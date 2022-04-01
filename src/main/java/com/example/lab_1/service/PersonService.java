package com.example.lab_1.service;

import com.example.lab_1.DTOs.PersonDTO;
import com.example.lab_1.DTOs.TaskDTO;
import com.example.lab_1.models.Person;
import com.example.lab_1.models.Task;
import com.example.lab_1.validationExceptions.UniqueEmailException;
import com.example.lab_1.validationExceptions.UniqueLoginException;
import com.example.lab_1.validationExceptions.UniqueNickNameException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface PersonService extends UserDetailsService {

    Person registerPerson(PersonDTO dto);

    Person getPerson(String login);

    void addRoleToPerson(String login, String roleName);

    void removePerson(Long personId);

    void updatePerson(PersonDTO person);

    List<Person> getAllPerson();

    void removeRoleFromPerson(String login, String role);

    void validateLogin(String login) throws UniqueLoginException;

    void validateUsername(String username) throws UniqueNickNameException;

    void validateEmail(String email) throws UniqueEmailException;

    Task addTaskToPerson(TaskDTO task, Long persId);

    void removeTaskFromPerson(Long taskId, String login);

    Person getPersonWithTasks(String login);

    Person getById(Long id);

    PersonDTO getDTObyId(Long id);

    List<TaskDTO> getTasksDTOListByUserId(Long id);

    List<TaskDTO> searchTasksDTOListByUserIdAndTaskName(Long id, String name);

    List<TaskDTO> getAllByPersonSortByDataDSC(Person person);

    List<TaskDTO> getAllByPersonSortByDataASC(Person person);

    List<TaskDTO> getAllByPersonSortByName(Person person);

    List<TaskDTO> getAllByPersonSortByDataASCAndName(Person person, String name);

    List<TaskDTO> getAllByPersonSortByDataDscAndName(Person person, String name);

    List<TaskDTO> getAllByPersonSortNameAndByName(Person person, String name);
}
