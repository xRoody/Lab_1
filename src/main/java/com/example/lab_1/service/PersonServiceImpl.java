package com.example.lab_1.service;

import com.example.lab_1.DTOs.ContactDTO;
import com.example.lab_1.DTOs.PersonDTO;
import com.example.lab_1.DTOs.TaskDTO;
import com.example.lab_1.models.Contact;
import com.example.lab_1.models.Person;
import com.example.lab_1.models.Task;
import com.example.lab_1.repositpries.PersonRepo;
import com.example.lab_1.repositpries.RoleRepo;
import com.example.lab_1.repositpries.TaskRepo;
import com.example.lab_1.validationExceptions.UniqueEmailException;
import com.example.lab_1.validationExceptions.UniqueLoginException;
import com.example.lab_1.validationExceptions.UniqueNickNameException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PersonServiceImpl implements PersonService {
    private final PersonRepo personRepo;
    private final RoleRepo roleRepo;
    private final TaskRepo taskRepo;
    private final TaskServiceImpl taskService;
    private final BCryptPasswordEncoder passwordEncoder;

    public Person savePerson(Person person) {
        log.info("save person name={}, email={}", person.getNickName(), person.getEmail());
        return personRepo.save(person);
    }

    public Person registerPerson(PersonDTO dto){
        Person person = Person.builder()
                .login(dto.getLogin())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .roles(new HashSet<>())
                .nickName(dto.getNickName())
                .build();
        addRoleToPerson(person,"USER");
        System.out.println(person);
        return savePerson(person);
    }

    public void validateLogin(String login) throws UniqueLoginException {
        if(getPerson(login)!=null){
            log.info("Login {} already exists", login);
            throw new UniqueLoginException("Login "+login+" already exists");
        }
    }

    public void validateUsername(String username) throws UniqueNickNameException {
        if(getByNickName(username)!=null){
            log.info("NickName {} already exists", username);
            throw new UniqueNickNameException("NickName "+username+" already exists");
        }
    }

    public void validateEmail(String email) throws UniqueEmailException {
        if(getByEmail(email)!=null){
            log.info("Email {} already exists", email);
            throw new UniqueEmailException("Email "+email+" already exists");
        }
    }

    private Person getByEmail(String email){
        return personRepo.findByEmail(email);
    }

    private Person getByNickName(String nick){
        return personRepo.findByNickName(nick);
    }

    @Override
    public Person getPersonWithTasks(String login) {
        return personRepo.findByLoginWithTasks(login);
    }

    @Override
    public Person getPerson(String login) {
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
    public void removePerson(Long personId) {
        log.info("remove person {}", personId);
        personRepo.deleteById(personId);
    }

    @Override
    public void updatePerson(PersonDTO personDTO) {
        Person person=getById(personDTO.getId());
        log.debug("update person {}", person.getLogin());
        person.setNickName(personDTO.getNickName());
        person.setEmail(personDTO.getEmail());
        if (personDTO.getR_password()!=null) person.setPassword(passwordEncoder.encode(personDTO.getR_password()));
        savePerson(person);
    }

    @Override
    public List<Person> getAllPerson() {
        return personRepo.findAll();
    }

    @Override
    public void removeRoleFromPerson(String login, String role) {
        log.info("remove role {} from person {}", role, login);
        Person person = personRepo.findByLogin(login);
        person.getRoles().remove(roleRepo.findByName(role.toUpperCase()));
    }

    @Override
    public Task addTaskToPerson(TaskDTO task, Long id) {
        log.info("add task to person id= {} by dto, with data: {}", id, task.toString());
        Task t=Task.builder()
                .name(task.getName())
                .description(task.getDescription())
                .eventTime(LocalDateTime.parse(task.getEventTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .contacts(new HashSet<>())
                .build();
        addTaskToPerson(t,id);
        if (task.getContactDTOSet()!=null){
            for(ContactDTO dto:task.getContactDTOSet()){
                taskService.addContact(t,dto);
            }
        }
        return t;
    }

    private void addTaskToPerson(Task task, Long id) {
        Person person = getById(id);
        task.setPerson(person);
        person.getTasks().add(task);
    }

    @Override
    public void removeTaskFromPerson(Long taskId, String login) {
        log.info("remove task id={} from person {}", taskId, login);
        taskRepo.deleteById(taskId);
    }

    public Person getById(Long id){
        return personRepo.getById(id);
    }

    public PersonDTO getDTObyId(Long id){
        Person person=getById(id);
        return PersonDTO.builder()
                .email(person.getEmail())
                .id(person.getId())
                .login(person.getLogin())
                .nickName(person.getNickName())
                .build();
    }

    public List<TaskDTO> getTasksDTOListByUserId(Long id){
        Person person=getById(id);
        List<TaskDTO> taskDTOS=new ArrayList<>();
        for(Task task:person.getTasks()){
            taskDTOS.add(taskService.getDTOByTaskById(task.getId()));
        }
        return taskDTOS;
    }

    public List<TaskDTO> searchTasksDTOListByUserIdAndTaskName(Long id, String name){
        List<TaskDTO> taskDTOS=new ArrayList<>();
        for(Task task:taskRepo.getAllByPersonSortByTaskName(name, id)){
            taskDTOS.add(taskService.getDTOByTaskById(task.getId()));
        }
        return taskDTOS;
    }

    public List<TaskDTO> getAllByPersonSortByDataDSC(Person person){
        List<TaskDTO> taskDTOS=new ArrayList<>();
        for(Task task:taskRepo.findAllByPersonOrderByEventTimeDesc(person)){
            taskDTOS.add(taskService.getDTOByTaskById(task.getId()));
        }
        return taskDTOS;
    }

    public List<TaskDTO> getAllByPersonSortByDataASC(Person person){
        List<TaskDTO> taskDTOS=new ArrayList<>();
        for(Task task:taskRepo.findAllByPersonOrderByEventTimeAsc(person)){
            taskDTOS.add(taskService.getDTOByTaskById(task.getId()));
        }
        return taskDTOS;
    }

    public List<TaskDTO> getAllByPersonSortByName(Person person){
        List<TaskDTO> taskDTOS=new ArrayList<>();
        for(Task task:taskRepo.findAllByPersonOrderByName(person)){
            taskDTOS.add(taskService.getDTOByTaskById(task.getId()));
        }
        return taskDTOS;
    }

    public  List<TaskDTO> getAllByPersonSortByDataASCAndName(Person person, String name){
        List<TaskDTO> taskDTOS=new ArrayList<>();
        for(Task task:taskRepo.findAllByPersonAndNameOrderByEventTimeAsc(person, name)){
            taskDTOS.add(taskService.getDTOByTaskById(task.getId()));
        }
        return taskDTOS;
    }

    public  List<TaskDTO> getAllByPersonSortByDataDscAndName(Person person, String name){
        List<TaskDTO> taskDTOS=new ArrayList<>();
        for(Task task:taskRepo.findAllByPersonAndNameOrderByEventTimeDesc(person, name)){
            taskDTOS.add(taskService.getDTOByTaskById(task.getId()));
        }
        return taskDTOS;
    }

    public  List<TaskDTO> getAllByPersonSortNameAndByName(Person person, String name){
        List<TaskDTO> taskDTOS=new ArrayList<>();
        for(Task task:taskRepo.findAllByPersonAndNameOrderByName(person, name)){
            taskDTOS.add(taskService.getDTOByTaskById(task.getId()));
        }
        return taskDTOS;
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
        return new User(person.getLogin(), person.getPassword(), authorities);
    }
}
