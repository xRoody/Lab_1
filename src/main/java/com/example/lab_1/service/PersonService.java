package com.example.lab_1.service;

import com.example.lab_1.models.Person;
import com.example.lab_1.models.Role;
import com.example.lab_1.models.Task;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;
import java.util.List;


public interface PersonService extends UserDetailsService {
    Person savePerson(Person person);
    Person getPerson(String login);
    void addRoleToPerson(String login, String roleName);
    void removePerson(Person person);
    void updatePerson(Person person);
    List<Person> getAllPerson();
    void removeRoleFromPerson(String login, Role role);
    void addTaskToPerson(Task task, String login);
    void removeTaskFromPerson(Task task, String login);
    Person getPersonWithTasks(String login);
}
