package com.example.lab_1.service;

import com.example.lab_1.models.Contact;
import com.example.lab_1.models.Task;

import java.util.Collection;

public interface TaskService {
    Collection<Contact> getAllContacts(Task task);
    void addContact(Task task,Contact contact);
    void removeContact(Task task, Contact contact);
    void updateTask(Task task);
    Task getTaskWithContacts(Task task);
}
