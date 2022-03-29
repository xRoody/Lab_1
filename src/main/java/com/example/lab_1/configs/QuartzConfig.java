package com.example.lab_1.configs;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;


import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@AllArgsConstructor
public class QuartzConfig {
    private final DataSource dataSource;
    private final ApplicationContext applicationContext;
    private final QuartzProperties quartzProperties;



    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerJobFactory schedulerJobFactory =new SchedulerJobFactory();
        schedulerJobFactory.setApplicationContext(applicationContext);
        Properties properties=new Properties();
        properties.putAll(quartzProperties.getProperties());
        SchedulerFactoryBean factoryBean=new SchedulerFactoryBean();
        factoryBean.setOverwriteExistingJobs(true);
        factoryBean.setDataSource(dataSource);
        factoryBean.setQuartzProperties(properties);
        factoryBean.setJobFactory(schedulerJobFactory);
        factoryBean.setAutoStartup(true);
        factoryBean.setWaitForJobsToCompleteOnShutdown(true);
        return factoryBean;
    }
}
