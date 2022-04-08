package com.example.lab_1.configs;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.Date;

/*
* This class  used to create jobs and triggers to Quartz by builder API.
* */

@Component
@Slf4j
public class JobFactory {

    /*
    * Here create job detail
    *
    * @param jobClass define class of this job
    * @param jobName define the job name (unique in group)
    * @param jobGroup define the job group name (unique)
    *
    * @return JobDetail instance
    * */

    public JobDetail createJob(Class<? extends Job> jobClass,  String jobName, String jobGroup){
        JobDetail jobDetail=JobBuilder
                .newJob()
                .withIdentity(jobName, jobGroup)
                .ofType(jobClass)
                .storeDurably(true)
                .build();
        return jobDetail;
    }

    /*
    * Here create job detail
    *
    * @param triggerGroup define the trigger group name (unique)
    * @param triggerName define the trigger name (unique in group)
    * @param startTime define the trigger start time
    *
    * @return SimpleTrigger with entered param
    * */

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
