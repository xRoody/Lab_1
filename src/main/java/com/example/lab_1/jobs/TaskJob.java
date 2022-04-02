package com.example.lab_1.jobs;


import com.example.lab_1.DTOs.ContactDTO;
import com.example.lab_1.DTOs.PersonDTO;
import com.example.lab_1.DTOs.TaskDTO;
import com.example.lab_1.models.Person;
import com.example.lab_1.service.MailService;
import com.example.lab_1.service.MailServiceImpl;
import com.example.lab_1.service.PersonService;
import com.example.lab_1.service.TaskService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
* Job component to Quartz. This class is task job
* */

@Component
@Slf4j
public class TaskJob implements Job {
    /*
     * taskService is used to interact with task model
     * */
    private  TaskService taskService;
    /*
     * personService is used to interact with person model
     * */
    private PersonService personService;
    /*
     * mailService is used to configure and send messages
     * */
    private MailService mailService;
    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }
    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }
    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    public TaskJob() {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext){
        String name=jobExecutionContext.getJobDetail().getKey().getName();
        log.info("Job ** {} starting in {}", name, jobExecutionContext.getFireTime());
        Long id=Long.parseLong(name.split(":")[0]);
        TaskDTO task=taskService.getDTOByTaskById(id);
        String person=personService.getDTObyId(task.getPersonId()).getEmail();
        taskService.makeComplete(id);
        for (ContactDTO curDTO: task.getContactDTOSet()){
            mailService.sendMessageWithHTML(curDTO.getEmail(), "Test", id, person.equals(curDTO.getEmail()));
        }
    }
}
