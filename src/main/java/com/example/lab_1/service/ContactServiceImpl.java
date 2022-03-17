package com.example.lab_1.service;

import com.example.lab_1.models.Contact;
import com.example.lab_1.repositpries.ContactRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ContactServiceImpl implements ContactService{
    private final ContactRepo repo;

    @Override
    public void updateContact(Contact contact) {
        repo.save(contact);
    }
}
