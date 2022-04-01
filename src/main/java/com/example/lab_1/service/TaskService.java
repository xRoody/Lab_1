package com.example.lab_1.service;

import com.example.lab_1.DTOs.ContactDTO;
import com.example.lab_1.DTOs.TaskDTO;
import com.example.lab_1.models.Contact;
import com.example.lab_1.models.Task;

import java.util.Collection;

public interface TaskService {
    void addContact(Task task, ContactDTO dto);
    void removeContact(Task task, Long contact);
    void updateTask(TaskDTO task);
    Task getTaskWithContacts(Long taskId);
    public void removeTaskById(Long id);
    TaskDTO getDTOByTaskById(Long taskId);
    public boolean makeComplete(Long id);
}
