package com.example.lab_1.service;

import com.example.lab_1.models.Person;
import com.example.lab_1.models.Role;
import com.example.lab_1.models.Task;
import com.example.lab_1.repositpries.PersonRepo;
import com.example.lab_1.repositpries.RoleRepo;
import com.example.lab_1.repositpries.TaskRepo;
import lombok.RequiredArgsConstructor;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonServiceImpl implements PersonService{
    private final PersonRepo personRepo;
    private final RoleRepo roleRepo;
    private final TaskRepo taskRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Person savePerson(Person person) {
        return personRepo.save(person);
    }

    /*public Person registerPerson(PersonDTO dto){

    }*/

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
        Person person=personRepo.findByLogin(login);
        Role role=roleRepo.findByName(roleName);
        person.getRoles().add(role);
    }

    @Override
    public void removePerson(Person person) {
        personRepo.delete(person);
    }

    @Override
    public void updatePerson(Person person) {
        savePerson(person);
    }

    @Override
    public List<Person> getAllPerson() {
        return personRepo.findAll();
    }

    @Override
    public void removeRoleFromPerson(String login, Role role) {
        Person person=personRepo.findByLogin(login);
        person.getRoles().remove(role);
    }

    @Override
    public void addTaskToPerson(Task task, String login) {
        Person person=getPerson(login);
        task.setPerson(person);
        person.getTasks().add(task);
    }

    @Override
    public void removeTaskFromPerson(Task task, String login) {
        getPerson(login).getTasks().remove(task);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person=personRepo.findByLogin(username);
        if (person==null) throw new UsernameNotFoundException("");
        Collection<SimpleGrantedAuthority> authorities=person.getRoles().stream().map(x->new SimpleGrantedAuthority(x.getName())).collect(Collectors.toList());///
        System.out.println(person);
        return new User(person.getLogin(),passwordEncoder.encode(person.getPassword()),authorities); // !!!password gonna encode in register method!!!
    }
}
