package com.example.lab_1.configs;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/*
* This class is used to configure run time job creating opportunity
* */

public class SchedulerJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {
    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        autowireCapableBeanFactory=applicationContext.getAutowireCapableBeanFactory();
    }

    /*
    * Add job autowiring to create jobs in runtime
    * */
    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Object job= super.createJobInstance(bundle);
        autowireCapableBeanFactory.autowireBean(job);
        return job;
    }
}
