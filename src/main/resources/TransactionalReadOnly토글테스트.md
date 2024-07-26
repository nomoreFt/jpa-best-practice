~~~java
    public void fetchAuthor() {
        Author author = authorRepository.findFirstByGenre("Anthology");
    }

    @Transactional
    public void fetchAuthorReadWriteMode() {
        Author author = authorRepository.findFirstByGenre("Anthology");

        displayInformation("After Fetch", author);

        author.setAge(40);

        displayInformation("After Update Entity", author);

        // force flush - triggering manual flush is a code smell and should be avoided
        // in this case, by default, flush will take place before transaction commit
        authorRepository.flush();

        displayInformation("After Flush", author);
    }

    @Transactional(readOnly = true)
    public void fetchAuthorReadOnlyMode() {
        Author author = authorRepository.findFirstByGenre("Anthology");

        displayInformation("After Fetch", author);

        author.setAge(40);

        displayInformation("After Update Entity", author);

        // force flush - triggering manual flush is a code smell and should be avoided
        // be default, because we set readOnly=true, flush mode is MANUAL,
        // therefore no flush will take place
        authorRepository.flush();

        displayInformation("After Flush", author);
    }

    @Transactional
    public void fetchAuthorDtoReadWriteMode() {
        AuthorDto authorDtoV1 = authorRepository.findTopByGenre("Anthology");

        System.out.println("Author DTO: " + authorDtoV1.getName() + ", " + authorDtoV1.getAge());

        org.hibernate.engine.spi.PersistenceContext persistenceContext = getPersistenceContext();

        System.out.println("No of managed entities : " + persistenceContext.getNumberOfManagedEntities());
    }

    @Transactional(readOnly = true)
    public void fetchAuthorDtoReadOnlyMode() {
        AuthorDto authorDtoV1 = authorRepository.findTopByGenre("Anthology");

        System.out.println("Author DTO: " + authorDtoV1.getName() + ", " + authorDtoV1.getAge());

        org.hibernate.engine.spi.PersistenceContext persistenceContext = getPersistenceContext();

        System.out.println("No of managed entities : " + persistenceContext.getNumberOfManagedEntities());
    }

~~~


~~~java
    private org.hibernate.engine.spi.PersistenceContext getPersistenceContext() {
        SharedSessionContractImplementor sharedSession = entityManager.unwrap(
                SharedSessionContractImplementor.class
        );

        return sharedSession.getPersistenceContext();
    }

    private void displayInformation(String phase, Object entity){
        System.out.println("\n-------------------------------------");
        System.out.println("Phase:" + phase);
        System.out.println("Entity: " + entity);
        System.out.println("-------------------------------------");

        org.hibernate.engine.spi.PersistenceContext persistenceContext = getPersistenceContext();

        System.out.println("Has only read entities : " + persistenceContext.isReadOnly(entity));

        EntityEntry entityEntry = persistenceContext.getEntry(entity);
        Object[] loadedState = entityEntry.getLoadedState();
        Status status = entityEntry.getStatus();

        System.out.println("Entity entry : " + entityEntry);
        System.out.println("Status:" + status);
        System.out.println("Loaded state: " + Arrays.toString(loadedState));
    }

~~~

* 영속성 컨텍스트 현재 단계
* toString() 을 통한 로드된 엔티티
* 영속성 컨텍스트가 읽기-전용의 엔티티를 포함하는지
* 엔티티 상태
* 엔티티 하이드레이티드 로드 상태



~~~sql
READ-WRITE MODE:
[Hibernate] 
    select
        a1_0.id,
        a1_0.age,
        a1_0.genre,
        a1_0.name 
    from
        author a1_0 
    where
        a1_0.genre=? 
    fetch
        first ? rows only
2024-07-06T15:57:55.091+09:00 TRACE 17772 --- [           main] org.hibernate.orm.jdbc.bind              : binding parameter (1:VARCHAR) <- [Anthology]
2024-07-06T15:57:55.091+09:00 TRACE 17772 --- [           main] org.hibernate.orm.jdbc.bind              : binding parameter (2:INTEGER) <- [1]

-------------------------------------
Phase:After Fetch
Entity: Author{id=1, age=23, name=Mark Janel, genre=Anthology}
-------------------------------------
Has only read entities : false
Entity entry : EntityEntry[com.example.practicepersistancelayer.chapter6.DelayConnection.entity.Author#1](MANAGED)
Status:MANAGED
Loaded state: [23, Anthology, Mark Janel]

-------------------------------------
Phase:After Update Entity
Entity: Author{id=1, age=40, name=Mark Janel, genre=Anthology}
-------------------------------------
Has only read entities : false
Entity entry : EntityEntry[com.example.practicepersistancelayer.chapter6.DelayConnection.entity.Author#1](MANAGED)
Status:MANAGED
Loaded state: [23, Anthology, Mark Janel]
[Hibernate] 
    update
        author 
    set
        age=?,
        genre=?,
        name=? 
    where
        id=?
2024-07-06T15:57:55.119+09:00 TRACE 17772 --- [           main] org.hibernate.orm.jdbc.bind              : binding parameter (1:INTEGER) <- [40]
2024-07-06T15:57:55.119+09:00 TRACE 17772 --- [           main] org.hibernate.orm.jdbc.bind              : binding parameter (2:VARCHAR) <- [Anthology]
2024-07-06T15:57:55.119+09:00 TRACE 17772 --- [           main] org.hibernate.orm.jdbc.bind              : binding parameter (3:VARCHAR) <- [Mark Janel]
2024-07-06T15:57:55.119+09:00 TRACE 17772 --- [           main] org.hibernate.orm.jdbc.bind              : binding parameter (4:BIGINT) <- [1]

-------------------------------------
Phase:After Flush
Entity: Author{id=1, age=40, name=Mark Janel, genre=Anthology}
-------------------------------------
Has only read entities : false
Entity entry : EntityEntry[com.example.practicepersistancelayer.chapter6.DelayConnection.entity.Author#1](MANAGED)
Status:MANAGED
Loaded state: [40, Anthology, Mark Janel]


READ-ONLY MODE:
[Hibernate] 
    select
        a1_0.id,
        a1_0.age,
        a1_0.genre,
        a1_0.name 
    from
        author a1_0 
    where
        a1_0.genre=? 
    fetch
        first ? rows only
2024-07-06T15:57:55.121+09:00 TRACE 17772 --- [           main] org.hibernate.orm.jdbc.bind              : binding parameter (1:VARCHAR) <- [Anthology]
2024-07-06T15:57:55.122+09:00 TRACE 17772 --- [           main] org.hibernate.orm.jdbc.bind              : binding parameter (2:INTEGER) <- [1]

-------------------------------------
Phase:After Fetch
Entity: Author{id=1, age=40, name=Mark Janel, genre=Anthology}
-------------------------------------
Has only read entities : true
Entity entry : EntityEntry[com.example.practicepersistancelayer.chapter6.DelayConnection.entity.Author#1](READ_ONLY)
Status:READ_ONLY
Loaded state: null

-------------------------------------
Phase:After Update Entity
Entity: Author{id=1, age=40, name=Mark Janel, genre=Anthology}
-------------------------------------
Has only read entities : true
Entity entry : EntityEntry[com.example.practicepersistancelayer.chapter6.DelayConnection.entity.Author#1](READ_ONLY)
Status:READ_ONLY
Loaded state: null

-------------------------------------
Phase:After Flush
Entity: Author{id=1, age=40, name=Mark Janel, genre=Anthology}
-------------------------------------
Has only read entities : true
Entity entry : EntityEntry[com.example.practicepersistancelayer.chapter6.DelayConnection.entity.Author#1](READ_ONLY)
Status:READ_ONLY
Loaded state: null


READ-WRITE MODE (DTO):
[Hibernate] 
    select
        a1_0.name,
        a1_0.age 
    from
        author a1_0 
    where
        a1_0.genre=? 
    fetch
        first ? rows only
2024-07-06T15:57:55.123+09:00 TRACE 17772 --- [           main] org.hibernate.orm.jdbc.bind              : binding parameter (1:VARCHAR) <- [Anthology]
2024-07-06T15:57:55.124+09:00 TRACE 17772 --- [           main] org.hibernate.orm.jdbc.bind              : binding parameter (2:INTEGER) <- [1]
Author DTO: Mark Janel, 40
No of managed entities : 0


READ-ONLY MODE (DTO):
[Hibernate] 
    select
        a1_0.name,
        a1_0.age 
    from
        author a1_0 
    where
        a1_0.genre=? 
    fetch
        first ? rows only
2024-07-06T15:57:55.127+09:00 TRACE 17772 --- [           main] org.hibernate.orm.jdbc.bind              : binding parameter (1:VARCHAR) <- [Anthology]
2024-07-06T15:57:55.127+09:00 TRACE 17772 --- [           main] org.hibernate.orm.jdbc.bind              : binding parameter (2:INTEGER) <- [1]
Author DTO: Mark Janel, 40
No of managed entities : 0
~~~