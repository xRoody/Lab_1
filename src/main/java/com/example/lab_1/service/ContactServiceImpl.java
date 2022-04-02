package com.example.lab_1.service;

import com.example.lab_1.models.Contact;
import com.example.lab_1.repositpries.ContactRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
* Contact service implementation class.
* */

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ContactServiceImpl implements ContactService{
    private final ContactRepo repo;


    /*
    * This method is used to update contact data
    * @param contact - exists contact, that must be updated
    * */
    @Override
    public void updateContact(Contact contact) {
        log.debug("update contact to task id={}", contact.getTask().getId());
        repo.save(contact);
    }
    /*
     * This method is used to delete contact bu id
     * @param id - contact id
     * */
    public void removeById(Long id){
        repo.deleteById(id);
    }
}
