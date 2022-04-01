package com.example.lab_1.configs;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/*
* Here configure data source bean to Quartz
* */

@Configuration
@EnableAutoConfiguration
public class QuartzAutoConfigDataSource {
    @Bean(name = "myDS")
    @QuartzDataSource
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource myDS() {
        return DataSourceBuilder.create().build();
    }
}
