package com.example.lab_1.service;

import com.example.lab_1.DTOs.ContactDTO;
import com.example.lab_1.models.Contact;
import com.example.lab_1.models.Task;
import com.example.lab_1.repositpries.TaskRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TaskServiceImpl implements TaskService{
    private final TaskRepo taskRepo;

    @Override
    public Collection<Contact> getAllContacts(Task task) {
        log.info("user {} get all contacts by task id {}", task.getPerson().getLogin(), task.getId());
        return task.getContacts();
    }

    @Override
    public void addContact(Task task,ContactDTO dto){
        log.info("user {} add contact to task id={} by dto", task.getPerson().getLogin(),task.getId());
        Contact contact=Contact.builder()
                .email(dto.getEmail())
                .build();
        addContact(task,contact);
    }

    private void addContact(Task task, Contact contact) {
        log.info("user {} add contact id={} to task id={}", task.getPerson().getLogin(),contact.getId(),task.getId());
        contact.setTask(task);
        task.getContacts().add(contact);
    }

    @Override
    public void removeContact(Task task, Contact contact) {
        log.info("user {} remove contact id={} from task id={}", task.getPerson().getLogin(),contact.getId(), task.getId());
        task.getContacts().remove(contact);
    }

    @Override
    public void updateTask(Task task) {
        log.info("user {} update task id={}", task.getPerson().getLogin(), task.getId());
        taskRepo.save(task);
    }

    @Override
    public Task getTaskWithContacts(Task task){
        log.info("fetch task id={} with contacts", task.getId());
        return taskRepo.findByIdWithContacts(task.getId());
    }
}
