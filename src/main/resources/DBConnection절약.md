# disable auto-commit
spring.datasource.hikari.auto-commit: false
spring.jpa.properties.hibernate.connection.provider_disables_autocommit=true


# Enable logging for HikariCP to verify that it is used

logging:
level:
org.hibernate.orm.jdbc.bind: TRACE
com.zaxxer.hikari.HikariConfig: DEBUG
com.zaxxer.hikari: TRACE


~~~sql
Waiting for a time-consuming task that doesn't need a database connection ...
HikariPool-1 - Pool stats (total=10, active=0, idle=10, waiting=0)
HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
Done, now query the database ...
The database connection should be acquired now ...
[Hibernate] 
    select
        a1_0.id,
        a1_0.age,
        a1_0.genre,
        a1_0.name 
    from
        author a1_0 
    where
        a1_0.id=?

HikariPool-1 - Pool stats (total=10, active=1, idle=9, waiting=0)
HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).

[Hibernate] 
    update
        author 
    set
        age=?,
        genre=?,
        name=? 
    where
        id=?


Closing JPA EntityManagerFactory for persistence unit 'default'
HikariPool-1 - Shutdown initiated...
HikariPool-1 - Before shutdown stats (total=10, active=0, idle=10, waiting=0)
~~~