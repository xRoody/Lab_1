package com.example.lab_1.service;

import com.example.lab_1.models.Contact;
import com.example.lab_1.models.Task;
import com.example.lab_1.repositpries.TaskRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService{
    private final TaskRepo taskRepo;

    @Override
    public Collection<Contact> getAllContacts(Task task) {
        return task.getContacts();
    }

    @Override
    public void addContact(Task task, Contact contact) {
        Task target=taskRepo.findById(task.getId()).orElse(null);
        if (target==null){
            throw new RuntimeException("Create custom ex!");
        }
        contact.setTask(target);
        target.getContacts().add(contact);
    }

    @Override
    public void removeContact(Task task, Contact contact) {
        task.getContacts().remove(contact);
    }

    @Override
    public void updateTask(Task task) {
        taskRepo.save(task);
    }

    @Override
    public Task getTaskWithContacts(Task task){
        return taskRepo.findByIdWithContacts(task.getId());
    }
}
