package com.example.lab_1;


import com.example.lab_1.DTOs.PersonDTO;
import com.example.lab_1.DTOs.RoleDTO;
import com.example.lab_1.models.Contact;
import com.example.lab_1.models.Person;
import com.example.lab_1.models.Role;
import com.example.lab_1.models.Task;
import com.example.lab_1.repositpries.ContactRepo;
import com.example.lab_1.repositpries.PersonRepo;
import com.example.lab_1.repositpries.RoleRepo;
import com.example.lab_1.repositpries.TaskRepo;
import com.example.lab_1.service.PersonService;
import com.example.lab_1.service.RoleService;
import com.example.lab_1.service.TaskService;
import com.example.lab_1.validationExceptions.UniqueEmailException;
import com.example.lab_1.validationExceptions.UniqueLoginException;
import com.example.lab_1.validationExceptions.UniqueNickNameException;
import lombok.extern.log4j.Log4j2;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        PersonDTO dto=PersonDTO.builder()
                .username("Alex")
                .login("N00st")
                .password("123456")
                .email("n00sty@gmail.com")
                .build();
        /*try {
            personService.registerPerson(dto);
        } catch (UniqueNickNameException e) {
            System.out.println(e.getMessage());
        } catch (UniqueLoginException e) {
            System.out.println(e.getMessage());
        } catch (UniqueEmailException e) {
            System.out.println(e.getMessage());
        }*/
        System.out.println(personService.getAllPerson());
        System.out.println(roleService.getAllRoles());
    }
}
