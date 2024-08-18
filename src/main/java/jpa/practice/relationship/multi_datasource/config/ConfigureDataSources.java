package jpa.practice.relationship.multi_datasource.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class ConfigureDataSources {

    @Primary
    @Bean(name = "configPrimaryDb")
    @ConfigurationProperties(prefix = "primary")
    public DataSourceProperties ds1DataSource() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "dataSourcePrimaryDb")
    @ConfigurationProperties(prefix = "primary.hikari")
    public HikariDataSource ds1(@Qualifier("configPrimaryDb") DataSourceProperties dataSourceProperties) {
        log.info("New data source with url: {}", dataSourceProperties.getUrl());
        HikariDataSource build = dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        return build;
    }


    /**
     * Secondary DataSource
     */

    @Bean(name = "configSecondaryDb")
    @ConfigurationProperties(prefix = "secondary")
    public DataSourceProperties secondaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "dataSourceSecondaryDb")
    @ConfigurationProperties(prefix = "secondary.hikari")
    public HikariDataSource secondaryDataSource(@Qualifier("configSecondaryDb") DataSourceProperties secondaryDataSourceProperties) {
        return secondaryDataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }



}
