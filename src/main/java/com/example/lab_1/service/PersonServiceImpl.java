package com.example.lab_1.service;

import com.example.lab_1.DTOs.PersonDTO;
import com.example.lab_1.DTOs.TaskDTO;
import com.example.lab_1.models.Person;
import com.example.lab_1.models.Role;
import com.example.lab_1.models.Task;
import com.example.lab_1.repositpries.PersonRepo;
import com.example.lab_1.repositpries.RoleRepo;
import com.example.lab_1.repositpries.TaskRepo;
import com.example.lab_1.validationExceptions.UniqueEmailException;
import com.example.lab_1.validationExceptions.UniqueLoginException;
import com.example.lab_1.validationExceptions.UniqueNickNameException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PersonServiceImpl implements PersonService {
    private final PersonRepo personRepo;
    private final RoleRepo roleRepo;
    private final TaskServiceImpl taskRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    public Person savePerson(Person person) {
        log.info("save new person");
        return personRepo.save(person);
    }

    public Person registerPerson(PersonDTO dto) throws UniqueLoginException, UniqueEmailException, UniqueNickNameException {
        log.info("received person dto,try to register new person");
        validateDto(dto);
        Person person = Person.builder()
                .login(dto.getLogin())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .roles(new HashSet<>())
                .nickName(dto.getUsername())
                .build();
        addRoleToPerson(person,"USER");
        return savePerson(person);
    }

    private void validateDto(PersonDTO dto) throws UniqueLoginException, UniqueEmailException, UniqueNickNameException{
        log.info("try to validate user data");
        if(getPerson(dto.getLogin())!=null){
            log.info("Login {} already exists", dto.getLogin());
            throw new UniqueLoginException("Login "+dto.getLogin()+" already exists");
        }
        if(getByEmail(dto.getEmail())!=null){
            log.info("Email {} already exists", dto.getEmail());
            throw new UniqueEmailException("Email "+dto.getEmail()+" already exists");
        }
        if(getByNickName(dto.getUsername())!=null){
            log.info("NickName {} already exists", dto.getUsername());
            throw new UniqueNickNameException("NickName "+dto.getUsername()+" already exists");
        }
        log.info("validation complete");
    }

    private Person getByEmail(String email){
        return personRepo.findByEmail(email);
    }

    private Person getByNickName(String nick){
        return personRepo.findByNickName(nick);
    }

    @Override
    public Person getPersonWithTasks(String login) {
        log.info("fetch person {} with tasks", login);
        return personRepo.findByLoginWithTasks(login);
    }

    @Override
    public Person getPerson(String login) {
        log.info("fetch person {} by login", login);
        return personRepo.findByLogin(login);
    }

    @Override
    public void addRoleToPerson(String login, String roleName) {
        log.info("add role {} to person {}", roleName, login);
        addRoleToPerson(personRepo.findByLogin(login),roleName);
    }

    private void addRoleToPerson(Person person, String roleName) {
        person.getRoles().add(roleRepo.findByName(roleName.toUpperCase()));
    }

    @Override
    public void removePerson(Person person) {
        log.info("remove person {}", person.getLogin());
        personRepo.delete(person);
    }

    @Override
    public void updatePerson(Person person) {
        log.info("update person {}", person.getLogin());
        savePerson(person);
    }

    @Override
    public List<Person> getAllPerson() {
        log.info("fetch all persons");
        return personRepo.findAll();
    }

    @Override
    public void removeRoleFromPerson(String login, String role) {
        log.info("remove role {} from person {}", role, login);
        Person person = personRepo.findByLogin(login);
        person.getRoles().remove(roleRepo.findByName(role.toUpperCase()));
    }

    @Override
    public void addTaskToPerson(TaskDTO task, String login) {
        log.info("add task to person {} by dto", login);
        Task t=Task.builder()
                .name(task.getName())
                .description(task.getDescription())
                .build();
        task.getContactDTOSet().forEach(x->taskRepo.addContact(t,x));
        addTaskToPerson(t,login);
    }

    private void addTaskToPerson(Task task, String login) {
        log.info("add task id={} to person {}", task.getId(), login);
        Person person = getPerson(login);
        task.setPerson(person);
        person.getTasks().add(task);
    }

    @Override
    public void removeTaskFromPerson(Task task, String login) {
        log.info("remove task id={} from person {}", task.getId(), login);
        getPerson(login).getTasks().remove(task);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("try to find user {} by security system", username);
        Person person = personRepo.findByLogin(username);
        if (person == null) {
            log.warn("user {} is not exist", username);
            throw new UsernameNotFoundException("");
        }
        Collection<SimpleGrantedAuthority> authorities = person.getRoles().stream().map(x -> new SimpleGrantedAuthority(x.getName())).collect(Collectors.toList());///
        return new User(person.getLogin(), person.getPassword(), authorities); // !!!password encode in registration method!!!
    }
}
