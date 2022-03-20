package com.example.lab_1.service;

import com.example.lab_1.DTOs.PersonDTO;
import com.example.lab_1.DTOs.TaskDTO;
import com.example.lab_1.models.Person;
import com.example.lab_1.models.Role;
import com.example.lab_1.models.Task;
import com.example.lab_1.validationExceptions.UniqueEmailException;
import com.example.lab_1.validationExceptions.UniqueLoginException;
import com.example.lab_1.validationExceptions.UniqueNickNameException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;
import java.util.List;


public interface PersonService extends UserDetailsService {
    //Person savePerson(Person person);
    Person registerPerson(PersonDTO dto) throws UniqueLoginException, UniqueEmailException, UniqueNickNameException;
    Person getPerson(String login);
    void addRoleToPerson(String login, String roleName);
    void removePerson(Person person);
    void updatePerson(Person person);
    List<Person> getAllPerson();
    void removeRoleFromPerson(String login, String role);
    //void addTaskToPerson(Task task, String login);
    void addTaskToPerson(TaskDTO task, String login);
    void removeTaskFromPerson(Task task, String login);
    Person getPersonWithTasks(String login);
}
