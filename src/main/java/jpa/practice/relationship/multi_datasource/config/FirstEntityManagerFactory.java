package jpa.practice.relationship.multi_datasource.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "jpa.practice.relationship.multi_datasource.repository.primary",
        entityManagerFactoryRef = "primaryEntityManagerFactory",
        transactionManagerRef = "primaryTransactionManager"
)
@EnableTransactionManagement
public class FirstEntityManagerFactory {
    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("dataSourcePrimaryDb") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages(packagesToScan())
                .persistenceUnit("primary")
                .properties(hibernateProperties())
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager primaryTransactionManager(
            @Qualifier("primaryEntityManagerFactory") EntityManagerFactory primaryEntityManagerFactory) {
        return new org.springframework.orm.jpa.JpaTransactionManager(primaryEntityManagerFactory);
    }

    /**
     * entities to scan
     */
    protected String[] packagesToScan() {
        return new String[] {
                "jpa.practice.relationship.multi_datasource.entity.primary"
        };
    }


    /**
     * hibernate properties
     */
    protected java.util.Map<String, String> hibernateProperties() {
        return new java.util.HashMap<>() {
            {
                put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
                put("hibernate.hbm2ddl.auto", "create");
            }
        };
    }
}


/**
 * ===== 프라이머리로 생성
 *
 * j.LocalContainerEntityManagerFactoryBean : Building JPA container EntityManagerFactory for persistence unit 'primary'
 * o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: primary]
 * org.hibernate.Version                    : HHH000412: Hibernate ORM core version 6.4.4.Final
 * o.h.c.internal.RegionFactoryInitiator    : HHH000026: Second-level cache disabled
 * o.s.o.j.p.SpringPersistenceUnitInfo      : No LoadTimeWeaver setup: ignoring JPA class transformer
 *
 * ===== 생성한 히카리풀 정보
 *
 * HikariPool-1 - configuration:
 * allowPoolSuspension.............false
 * autoCommit......................true
 * catalog.........................none
 * connectionInitSql...............none
 * connectionTestQuery.............none
 * connectionTimeout...............30000
 * dataSource......................none
 * dataSourceClassName.............none
 * dataSourceJNDI..................none
 * dataSourceProperties............{password=<masked>}
 * driverClassName................."org.h2.Driver"
 * exceptionOverrideClassName......none
 * healthCheckProperties...........{}
 * healthCheckRegistry.............none
 * idleTimeout.....................600000
 * initializationFailTimeout.......1
 * isolateInternalQueries..........false
 * jdbcUrl.........................jdbc:h2:mem:5557f4e8-42d3-46f0-ba2c-d68c5df1dcbe;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
 * keepaliveTime...................0
 * leakDetectionThreshold..........0
 * maxLifetime.....................1800000
 * maximumPoolSize.................10
 * metricRegistry..................none
 * metricsTrackerFactory...........none
 * minimumIdle.....................10
 * password........................<masked>
 * poolName........................"HikariPool-1"
 * readOnly........................false
 * registerMbeans..................false
 * scheduledExecutor...............none
 * schema..........................none
 * threadFactory...................internal
 * transactionIsolation............default
 * username........................"sa"
 * validationTimeout...............5000
 *
 * com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
 * com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection conn0: url=jdbc:h2:mem:5557f4e8-42d3-46f0-ba2c-d68c5df1dcbe user=SA
 * com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
 * org.hibernate.orm.deprecation            : HHH90000025: H2Dialect does not need to be specified explicitly using 'hibernate.dialect' (remove the property setting and it will be selected by default)
 *
 * ===== 초기 커넥션 idle(유휴) 상태 8개 유지 모습
 *
 * com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=1, active=0, idle=1, waiting=0)
 * com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection conn1: url=jdbc:h2:mem:5557f4e8-42d3-46f0-ba2c-d68c5df1dcbe user=SA
 * com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - After adding stats (total=2, active=0, idle=2, waiting=0)
 * com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection conn2: url=jdbc:h2:mem:5557f4e8-42d3-46f0-ba2c-d68c5df1dcbe user=SA
 * com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - After adding stats (total=3, active=0, idle=3, waiting=0)
 * com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection conn3: url=jdbc:h2:mem:5557f4e8-42d3-46f0-ba2c-d68c5df1dcbe user=SA
 * com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - After adding stats (total=4, active=0, idle=4, waiting=0)
 * com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection conn4: url=jdbc:h2:mem:5557f4e8-42d3-46f0-ba2c-d68c5df1dcbe user=SA
 * com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - After adding stats (total=5, active=0, idle=5, waiting=0)
 * com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection conn5: url=jdbc:h2:mem:5557f4e8-42d3-46f0-ba2c-d68c5df1dcbe user=SA
 * com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - After adding stats (total=6, active=0, idle=6, waiting=0)
 * com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection conn6: url=jdbc:h2:mem:5557f4e8-42d3-46f0-ba2c-d68c5df1dcbe user=SA
 * com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - After adding stats (total=7, active=0, idle=7, waiting=0)
 * com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection conn7: url=jdbc:h2:mem:5557f4e8-42d3-46f0-ba2c-d68c5df1dcbe user=SA
 * com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - After adding stats (total=8, active=0, idle=8, waiting=0)
 * com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection conn8: url=jdbc:h2:mem:5557f4e8-42d3-46f0-ba2c-d68c5df1dcbe user=SA
 * o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000489: No JTA platform available (set 'hibernate.transaction.jta.platform' to enable JTA platform integration)
 *
 * packageScan에서 포함된 엔티티만 생성
 *
 * [Hibernate]
 *     drop table if exists Author cascade
 * [Hibernate]
 *     create table Author (
 *         age integer not null,
 *         id bigint generated by default as identity,
 *         books varchar(255),
 *         genre varchar(255),
 *         name varchar(255),
 *         primary key (id)
 *     )
 * Initialized JPA EntityManagerFactory for persistence unit 'primary'
 * Driver class org.h2.Driver found in Thread context class loader jdk.internal.loader.ClassLoaders$AppClassLoader@1dbd16a6
 */