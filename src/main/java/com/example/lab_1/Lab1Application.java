package com.example.lab_1;


import com.example.lab_1.models.Contact;
import com.example.lab_1.models.Person;
import com.example.lab_1.models.Role;
import com.example.lab_1.models.Task;
import com.example.lab_1.repositpries.ContactRepo;
import com.example.lab_1.repositpries.TaskRepo;
import com.example.lab_1.service.PersonService;
import com.example.lab_1.service.RoleService;
import com.example.lab_1.service.TaskService;
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
        TaskService taskService=context.getBean(TaskService.class);
        TaskRepo taskRepo=context.getBean(TaskRepo.class);
        ContactRepo contactRepo=context.getBean(ContactRepo.class);
        Person person=new Person();
        person.setPassword("123");
        person.setNickName("Nick4");
        person.setLogin("Login4");
        /*Role user=new Role();
        user.setName("Admin");*/
        //personService.addRoleToPerson("Login2","User");
        //personService.savePerson(person);
        //Person person=personService.getPerson("Login");
        //person.getTasks().add(task);
        //personService.savePerson(person);*/
        //System.out.println(personService.getAllPerson());
        Task task=new Task();
        task.setName("t1");
        Contact contact=new Contact();
        contact.setEmail("myMail");
        //System.out.println(taskRepo.findById(7l));
        //taskService.addContact(taskRepo.getById(7l),contact);
        System.out.println(taskRepo.findAll());
        System.out.println(contactRepo.findAll());
        //taskRepo.save(task);
        //System.out.println(taskService.getAllContacts(task));
        //personService.addTaskToPerson(task, "Login");
        /*Person person=personService.getPerson("Login");
        person.setNickName("John");
        personService.updatePerson(person);
        System.out.println(personService.getPerson("Login"));*/
    }
}
