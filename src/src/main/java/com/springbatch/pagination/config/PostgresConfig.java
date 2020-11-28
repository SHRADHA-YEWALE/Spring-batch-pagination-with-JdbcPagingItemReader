package com.springbatch.pagination.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import javax.sql.DataSource;

@Configuration
@Qualifier("postgres")
@Slf4j
@PropertySource(value = "classpath:application.yml")
public class PostgresConfig {


    /**
     * Provides postgres bean.
     * @return datasource.
     */
    @Bean("postgresDatasource")
    @ConfigurationProperties(prefix = "postgres.datasource")
    @Primary
    public DataSource dataSource() {
        log.info("Inside postgres data source");
        return DataSourceBuilder.create().build();
    }

    /**
     * Provides NamedParameter configured with postgres datasource
     * @return datasource.
     */
    @Bean("postgresJdbc")
    @Primary
    public NamedParameterJdbcTemplate getJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

}