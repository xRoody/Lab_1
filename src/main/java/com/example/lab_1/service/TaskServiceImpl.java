package com.example.lab_1.service;

import com.example.lab_1.DTOs.ContactDTO;
import com.example.lab_1.DTOs.TaskDTO;
import com.example.lab_1.models.Contact;
import com.example.lab_1.models.Task;
import com.example.lab_1.repositpries.ContactRepo;
import com.example.lab_1.repositpries.PersonRepo;
import com.example.lab_1.repositpries.TaskRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TaskServiceImpl implements TaskService{
    private final TaskRepo taskRepo;
    private final ContactRepo contactRepo;
    private final PersonRepo personRepo;

    /*
    * This method is used to add contact by contact dto to task
    * */
    @Override
    public void addContact(Task task,ContactDTO dto){
        log.info("user {} add contact to task id={} by dto contactInfo: {}", task.getPerson().getLogin(),task.getId(), dto.toString());
        Contact contact=Contact.builder()
                .email(dto.getEmail())
                .build();
        addContact(task,contact);
    }

    private void addContact(Task task, Contact contact) {
        contact.setTask(task);
        task.getContacts().add(contact);
    }

    /*
     * This method is used to add remove by id (actually task is used to log info)
     * */
    @Override
    public void removeContact(Task task, Long contactId) {
        log.info("user {} remove contact id={} from task id={}", task.getPerson().getLogin(),contactId, task.getId());
        contactRepo.deleteById(contactId);
    }
    /*
    * This method is used to update task data by task dto (dto in this case must have task id)
    * */
    @Override
    public void updateTask(TaskDTO task) {
        log.info("user id={} update task id={}", task.getPersonId(), task.getId());
        Task t=taskRepo.getById(task.getId());
        t.setName(task.getName());
        t.setDescription(task.getDescription());
        t.setEventTime(LocalDateTime.parse(task.getEventTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        t.setComplete(false);
        removeAllContacts(t);
        t.setContacts(new HashSet<>());
        if (task.getContactDTOSet()!=null) {
            for (ContactDTO dto : task.getContactDTOSet()) {
                addContact(t, dto);
            }
        }
        taskRepo.save(t);
    }

    private void removeAllContacts(Task t){
        for (Contact x : t.getContacts()) {
            removeContact(t, x.getId());
        }
    }

    /*
    * This method is used to get task with all contacts (eager, lazy by default)
    * */
    @Override
    public Task getTaskWithContacts(Long id){
        log.info("fetch task id={} with contacts", id);
        return taskRepo.findByIdWithContacts(id);
    }

    public void removeTaskById(Long id){
        taskRepo.deleteById(id);
    }
    /*
    * This method is used to et task dto by task id
    * */
    @Override
    public TaskDTO getDTOByTaskById(Long taskId) {
        Collection<ContactDTO> contactDTOS=new ArrayList<>();
        Task task=taskRepo.findByIdWithContacts(taskId);
        List<ContactDTO> list=task.getContacts().stream().map(x->ContactDTO.builder().email(x.getEmail()).id(x.getId()).build()).collect(Collectors.toList());
        TaskDTO t=TaskDTO.builder()
                .id(task.getId())
                .personId(task.getPerson().getId())
                .name(task.getName())
                .description(task.getDescription())
                .eventTime(task.getEventTime().toString())
                .isComplete(task.isComplete())
                .contactDTOSet(list)
                .build();
        return t;
    }
    /*
    * This method is used to make task complete (by quartz scheduler)
    * */
    public boolean makeComplete(Long id){
        Task task=taskRepo.getById(id);
        task.setComplete(true);
        taskRepo.save(task);
        return task.isComplete();
    }
}
