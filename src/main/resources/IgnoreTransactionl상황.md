
~~~java
    public void mainAuthor() {
        Author author = new Author();
        persistAuthor(author);
        //helperService.persistAuthor(author);
        notifyAuthor(author);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public persistAuthor(Author author) {
        authorRepository.save(author);
        return authorRepository.count();
    }

    public void notifyAuthor(Author author) {
        log.info(() -> "Saving author: " + author);
    }
~~~
* 호출된 동일한 클래스에 정의된 메서드에 추가된 경우
* save시와 count 제각기 트랜잭션이 추가됐다. (메서드 단위로 하나로 안묶임)


~~~sql
Creating new transaction with name [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
Opened new EntityManager [SessionImpl(591853434<open>)] for JPA transaction
On TransactionImpl creation, JpaCompliance#isJpaTransactionComplianceEnabled == false
begin
Exposing JPA transaction as JDBC [org.springframework.orm.jpa.vendor.HibernateJpaDialect$HibernateConnectionHandle@21780905]
    
[Hibernate] 
    insert 
    into
        author
        (age, genre, name, id) 
    values
        (?, ?, ?, default)

Initiating transaction commit
Committing JPA transaction on EntityManager [SessionImpl(591853434<open>)]
committing
Closing JPA EntityManager [SessionImpl(591853434<open>)] after transaction
Creating new transaction with name [org.springframework.data.jpa.repository.support.SimpleJpaRepository.count]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT,readOnly
Opened new EntityManager [SessionImpl(2088366799<open>)] for JPA transaction
On TransactionImpl creation, JpaCompliance#isJpaTransactionComplianceEnabled == false
begin
Exposing JPA transaction as JDBC [org.springframework.orm.jpa.vendor.HibernateJpaDialect$HibernateConnectionHandle@63551c66]
    
[Hibernate] 
    select
        count(*) 
    from
        author a1_0
Initiating transaction commit
Committing JPA transaction on EntityManager [SessionImpl(2088366799<open>)]
committing
Closing JPA EntityManager [SessionImpl(2088366799<open>)] after transaction
Saving author: Author{id=1, age=0, name=null, genre=null}
Closing JPA EntityManagerFactory for persistence unit 'default'
~~~



## 해결책 1. 단일 트랜잭션 사용



~~~java
    @Transactional
    public void mainAuthor() {
        Author author = new Author();
        authorRepository.save(author);
        authorRepository.count();
        //persistAuthor(author); 로 메서드를 호출해도 동일한 결과이다. (persistAuthor에 @Transactional(propagation = Propagation.REQUIRES_NEW)이 붙어도)
        notifyAuthor(author);
    }
~~~

* 정상적으로 1개의 트랜잭션 내에서 실행되는 모습이다.

~~~sql
Creating new transaction with name [com.example.practicepersistancelayer.chapter6.DelayConnection.service.IgnoreTransactionalTest.mainAuthor]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
Opened new EntityManager [SessionImpl(1936689207<open>)] for JPA transaction
On TransactionImpl creation, JpaCompliance#isJpaTransactionComplianceEnabled == false
begin
Exposing JPA transaction as JDBC [org.springframework.orm.jpa.vendor.HibernateJpaDialect$HibernateConnectionHandle@66a8ff6d]
Found thread-bound EntityManager [SessionImpl(1936689207<open>)] for JPA transaction
Participating in existing transaction
    
[Hibernate] 
    insert 
    into
        author
        (age, genre, name, id) 
    values
        (?, ?, ?, default)

Found thread-bound EntityManager [SessionImpl(1936689207<open>)] for JPA transaction
Participating in existing transaction
        
[Hibernate] 
    select
        count(*) 
    from
        author a1_0
        
Saving author: Author{id=1, age=0, name=null, genre=null}
Initiating transaction commit
Committing JPA transaction on EntityManager [SessionImpl(1936689207<open>)]
committing
Closing JPA EntityManager [SessionImpl(1936689207<open>)] after transaction
Closing JPA EntityManagerFactory for persistence unit 'default'
~~~



## 해결책 2. 타겟 트랜잭션 외부 클래스로 분리



~~~java
    @Transactional
    public void mainAuthor() {
        Author author = new Author();
        authorRepository.count();
        helperService.persistAuthor(author);
        notifyAuthor(author);
    }
~~~



~~~sql
Creating new transaction with name [com.example.practicepersistancelayer.chapter6.DelayConnection.service.IgnoreTransactionalTest.mainAuthor]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
Opened new EntityManager [SessionImpl(698263942<open>)] for JPA transaction
On TransactionImpl creation, JpaCompliance#isJpaTransactionComplianceEnabled == false
begin
Exposing JPA transaction as JDBC [org.springframework.orm.jpa.vendor.HibernateJpaDialect$HibernateConnectionHandle@3aed692d]
Found thread-bound EntityManager [SessionImpl(698263942<open>)] for JPA transaction
Participating in existing transaction
    
[Hibernate] 
    select
        count(*) 
    from
        author a1_0
        
Found thread-bound EntityManager [SessionImpl(698263942<open>)] for JPA transaction
Suspending current transaction, creating new transaction with name [com.example.practicepersistancelayer.chapter6.DelayConnection.service.IgnoreTransactionOuterService.persistAuthor]
Opened new EntityManager [SessionImpl(60426688<open>)] for JPA transaction
On TransactionImpl creation, JpaCompliance#isJpaTransactionComplianceEnabled == false

    begin
    Exposing JPA transaction as JDBC [org.springframework.orm.jpa.vendor.HibernateJpaDialect$HibernateConnectionHandle@3dbf3bc]
    Found thread-bound EntityManager [SessionImpl(60426688<open>)] for JPA transaction
    Participating in existing transaction
    
[Hibernate] 
    insert 
    into
        author
        (age, genre, name, id) 
    values
        (?, ?, ?, default)

    Found thread-bound EntityManager [SessionImpl(60426688<open>)] for JPA transaction
    Participating in existing transaction
        
[Hibernate] 
    select
        count(*) 
    from
        author a1_0
        
    Initiating transaction commit
    Committing JPA transaction on EntityManager [SessionImpl(60426688<open>)]
    committing
    Closing JPA EntityManager [SessionImpl(60426688<open>)] after transaction
Resuming suspended transaction after completion of inner transaction
Saving author: Author{id=1, age=0, name=null, genre=null}
Initiating transaction commit
Committing JPA transaction on EntityManager [SessionImpl(698263942<open>)]
committing
Closing JPA EntityManager [SessionImpl(698263942<open>)] after transaction
Closing JPA EntityManagerFactory for persistence unit 'default'
~~~


이제 무시되지 않고 의도대로 호출부 트랜잭션 (내부 트랜잭션 new )  으로 작동하는 모습이다.

또 한가지로 호출되는 method에는 @Transactional을 붙이지 않아도 호출부의 Transactional을 따라가는 모습이다.


~~~java
    @Transactional
    public void mainAuthor() {
        Author author = new Author();
        System.out.println(authorRepository.count());
        helperService.persistAuthor(author);
        notifyAuthor(author);
    }
~~~

~~~java
//외부 클래스의 메서드, No Transactional 설정
    public long persistAuthor(Author author) {
        authorRepository.save(author);
        long count = authorRepository.count();
        System.out.println(count);
        return count;
    }
~~~


그러나 어지간하면 외부 클래스의 메서드로 분리해도 @Transactional 붙여주는게 옳다.