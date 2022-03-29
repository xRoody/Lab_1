package com.example.lab_1.jobs;


import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TaskJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("Job ** {} starting in {}", jobExecutionContext.getJobDetail().getKey().getName(), jobExecutionContext.getFireTime());
    }
}
