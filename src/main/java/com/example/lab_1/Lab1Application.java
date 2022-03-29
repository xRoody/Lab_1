package com.example.lab_1;


import com.example.lab_1.DTOs.ContactDTO;
import com.example.lab_1.DTOs.PersonDTO;
import com.example.lab_1.DTOs.TaskDTO;
import com.example.lab_1.configs.JobFactory;
import com.example.lab_1.jobs.TestJob;
import com.example.lab_1.models.Person;
import com.example.lab_1.repositpries.ContactRepo;
import com.example.lab_1.repositpries.PersonRepo;
import com.example.lab_1.repositpries.RoleRepo;
import com.example.lab_1.repositpries.TaskRepo;
import com.example.lab_1.service.PersonService;
import com.example.lab_1.service.QuartzService;
import com.example.lab_1.service.RoleService;
import com.example.lab_1.service.TaskService;
import org.quartz.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;



@SpringBootApplication
public class Lab1Application {
    public static void main(String[] args)  {
        ConfigurableApplicationContext context=SpringApplication.run(Lab1Application.class, args);
        PersonService personService=context.getBean(PersonService.class);
        RoleService roleService=context.getBean(RoleService.class);
        RoleRepo roleRepo =context.getBean(RoleRepo.class);
        TaskService taskService=context.getBean(TaskService.class);
        TaskRepo taskRepo=context.getBean(TaskRepo.class);
        ContactRepo contactRepo=context.getBean(ContactRepo.class);
        PersonRepo personRepo=context.getBean(PersonRepo.class);
        QuartzService quartzService=context.getBean(QuartzService.class);
        JobDetail jobDetail= quartzService.createJobDetail(TestJob.class, "TEstJob", "Task");
        //Trigger trigger= quartzService.createTrigger("TEstJob","TaskTrigger",LocalDateTime.parse("2022-03-27T19:03", DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        //Trigger trigger1= quartzService.createTrigger("UpdatedJobTrigger", "TaskTrigger", LocalDateTime.parse("2022-03-27T19:07", DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        //quartzService.addNewJob(jobDetail,trigger1);
        //quartzService.removeJob("TEstJob", "Task", "UpdatedJobTrigger","TaskTrigger");
        //quartzService.updateJob(trigger, "UpdatedJobTrigger", "TaskTrigger");
        //System.out.println(quartzService.isExists("TEstJob", "Task"));
        //quartzService.clearScheduler();
    }
}
