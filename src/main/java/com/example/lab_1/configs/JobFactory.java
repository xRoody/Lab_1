package com.example.lab_1.configs;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JobFactory {

    public JobDetail createJob(Class<? extends Job> jobClass,  String jobName, String jobGroup){
        JobDetail jobDetail=JobBuilder
                .newJob()
                .withIdentity(jobName, jobGroup)
                .ofType(jobClass)
                .storeDurably(true)
                .build();
        return jobDetail;
    }

    public SimpleTrigger createSimpleTrigger(String triggerName,String triggerGroup, Date startTime) {
        SimpleTrigger trigger=TriggerBuilder
                .newTrigger()
                .withIdentity(triggerName, triggerGroup)
                .startAt(startTime)
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()
                        .withMisfireHandlingInstructionFireNow()
                )
                .build();
        return trigger;
    }
}
