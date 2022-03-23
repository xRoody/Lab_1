package com.example.lab_1;


import com.example.lab_1.DTOs.ContactDTO;
import com.example.lab_1.DTOs.PersonDTO;
import com.example.lab_1.DTOs.TaskDTO;
import com.example.lab_1.models.Person;
import com.example.lab_1.repositpries.ContactRepo;
import com.example.lab_1.repositpries.PersonRepo;
import com.example.lab_1.repositpries.RoleRepo;
import com.example.lab_1.repositpries.TaskRepo;
import com.example.lab_1.service.PersonService;
import com.example.lab_1.service.RoleService;
import com.example.lab_1.service.TaskService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class Lab1Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context=SpringApplication.run(Lab1Application.class, args);
        PersonService personService=context.getBean(PersonService.class);
        RoleService roleService=context.getBean(RoleService.class);
        RoleRepo roleRepo =context.getBean(RoleRepo.class);
        TaskService taskService=context.getBean(TaskService.class);
        TaskRepo taskRepo=context.getBean(TaskRepo.class);
        ContactRepo contactRepo=context.getBean(ContactRepo.class);
        PersonRepo personRepo=context.getBean(PersonRepo.class);
        System.out.println(taskRepo.findAll());
        System.out.println(personService.getAllPerson());
        System.out.println(roleService.getAllRoles());
    }
}
