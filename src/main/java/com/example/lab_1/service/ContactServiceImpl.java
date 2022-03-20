package com.example.lab_1.service;

import com.example.lab_1.models.Contact;
import com.example.lab_1.repositpries.ContactRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ContactServiceImpl implements ContactService{
    private final ContactRepo repo;

    @Override
    public void updateContact(Contact contact) {
        log.info("update contact data id={} in task id={}", contact.getId(), contact.getTask().getId());
        repo.save(contact);
    }
}
