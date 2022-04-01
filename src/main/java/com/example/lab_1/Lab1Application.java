package com.example.lab_1;

import com.example.lab_1.repositpries.TaskRepo;
import com.example.lab_1.service.PersonService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class Lab1Application {
    public static void main(String[] args)  {
        ConfigurableApplicationContext context=SpringApplication.run(Lab1Application.class, args);
        TaskRepo taskRepo=context.getBean(TaskRepo.class);
        PersonService personService=context.getBean(PersonService.class);
    }
}
