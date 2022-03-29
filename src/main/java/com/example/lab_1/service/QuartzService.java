package com.example.lab_1.service;

import com.example.lab_1.configs.JobFactory;
import com.example.lab_1.jobs.TaskJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class QuartzService {
    private final Scheduler scheduler;
    private final JobFactory jobFactory;

    @PostConstruct
    public void init(){
        log.info("Service initialization complete");
    }

    public JobDetail createJobDetail(Class<? extends Job> jobClass, String jobName, String jobGroupName){
        return jobFactory.createJob(jobClass,jobName,jobGroupName);
    }

    public Trigger createTrigger(String triggerName,String triggerGroup, LocalDateTime startDate){
        Instant instant = startDate.atZone(ZoneId.systemDefault()).toInstant();
        Date date=Date.from(instant);
        return jobFactory.createSimpleTrigger(triggerName,triggerGroup, date);
    }

    public void addNewJob(JobDetail jobDetail, Trigger trigger){
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            log.info("Job has been added {}", isExists(jobDetail.getKey().getName(), jobDetail.getKey().getGroup()));
        } catch (SchedulerException e) {
            log.warn("Job creating error"+e.getMessage());
        }
    }

    public void removeJob(String jobName, String jobGroup, String triggerKey, String triggerGroup){
        try {
            scheduler.unscheduleJob(TriggerKey.triggerKey(triggerKey, triggerGroup));
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroup));
            log.info("Job has been removed {}", isExists(jobName, jobGroup));
        } catch (SchedulerException e) {
            log.warn("Job creating error"+e.getMessage());
        }
    }

    public boolean isExists(String jobName, String jobGroup){
        try {
            return scheduler.checkExists(JobKey.jobKey(jobName,jobGroup));
        } catch (SchedulerException e) {
            log.warn("Job creating error"+e.getMessage());
        }
        return false;
    }

    public void updateJob(Trigger newTrigger, String triggerName, String triggerGroup, String oldJobName, String oldJobGroup){
        try {
            log.info("Job has been updated {}", isExists(oldJobName, oldJobGroup));
            if (scheduler.getTriggerState(TriggerKey.triggerKey(triggerName, triggerGroup)).equals(Trigger.TriggerState.NONE)){
                log.info("Complete job has been updated {}", isExists(oldJobName, oldJobGroup));
                updateCompletedJob(oldJobName,oldJobGroup,triggerName,triggerGroup,newTrigger);
            }
            scheduler.rescheduleJob(TriggerKey.triggerKey(triggerName, triggerGroup),newTrigger);
        } catch (SchedulerException e) {
            log.warn("Job creating error"+e.getMessage());
        }
    }

    public void updateCompletedJob(String jobName, String jobGroup, String oldTriggerKey, String oldTriggerGroup, Trigger newTrigger){
        removeJob(jobName, jobGroup, oldTriggerKey, oldTriggerGroup);
        addNewJob(
                createJobDetail(TaskJob.class, jobName, jobGroup),
                newTrigger
        );
    }

    public void clearScheduler(){
        try {
            scheduler.clear();
        } catch (SchedulerException e) {
            log.warn("Scheduler cleaning error"+e.getMessage());
        }
    }

    @PreDestroy
    public void destroy(){
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            log.warn("Job creating error"+e.getMessage());
        }
        log.info("Service destroying complete");
    }
}
