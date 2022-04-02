package com.example.lab_1.service;

import com.example.lab_1.schedulerExceptions.JobCreateException;
import com.example.lab_1.schedulerExceptions.JobRemoveException;
import com.example.lab_1.schedulerExceptions.JobUpdateException;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Trigger;

import java.time.LocalDateTime;

public interface QuartzService {
    JobDetail createJobDetail(Class<? extends Job> jobClass, String jobName, String jobGroupName);
    Trigger createTrigger(String triggerName, String triggerGroup, LocalDateTime startDate);
    void addNewJob(JobDetail jobDetail, Trigger trigger) throws JobCreateException;
    void removeJob(String jobName, String jobGroup, String triggerKey, String triggerGroup) throws JobRemoveException;
    boolean isExists(String jobName, String jobGroup) ;
    void updateJob(Trigger newTrigger, String triggerName, String triggerGroup, String oldJobName, String oldJobGroup) throws JobUpdateException;
    void clearScheduler();
}
