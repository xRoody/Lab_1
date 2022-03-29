package com.example.lab_1.controllers;

import com.example.lab_1.DTOs.ContactDTO;
import com.example.lab_1.DTOs.TaskDTO;
import com.example.lab_1.jobs.TaskJob;
import com.example.lab_1.models.Person;
import com.example.lab_1.models.Task;
import com.example.lab_1.service.ContactService;
import com.example.lab_1.service.PersonService;
import com.example.lab_1.service.QuartzService;
import com.example.lab_1.service.TaskService;
import com.example.lab_1.validators.TaskValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    private final TaskService taskService;
    private final PersonService personService;
    private final TaskValidator taskValidator;
    private final QuartzService quartzService;
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @DeleteMapping("/tasks/{id}")
    public String deleteTask(@PathVariable("id") Long id){
        taskService.removeTaskById(id);
        quartzService.removeJob(id+":Task", "Tasks", id+":TaskTrigger", "TaskTriggers");
        return "redirect:/";
    }

    @GetMapping("/tasks/{id}")
    public String getTaskInfo(@PathVariable("id") Long id, Model model, Principal principal){
        TaskDTO taskDTO=taskService.getDTOByTaskById(id);
        Person person=personService.getById(taskDTO.getPersonId());
        if (!Objects.equals(personService.getPerson(principal.getName()).getId(), taskDTO.getPersonId())) return "redirect:/";
        addPersonDetails(person, model);
        model.addAttribute("task",taskDTO);
        return "CurTaskDemoPage";
    }

    @PatchMapping("/tasks")
    public String updateTaskInfo(@ModelAttribute("task") TaskDTO taskDTO, BindingResult result, Model model){
        taskValidator.validateTask(result, taskDTO);
        Person person=personService.getById(taskDTO.getPersonId());
        addPersonDetails(person, model);
        if (!result.hasErrors()){
            log.info(System.nanoTime()+" start");
            //if (!taskService.getDTOByTaskById(taskDTO.getId()).getEventTime().equals(taskDTO.getEventTime())) {
                quartzService.updateJob(
                        quartzService.createTrigger(taskDTO.getId()+":TaskTrigger", "TaskTriggers", LocalDateTime.parse(taskDTO.getEventTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME)),
                        taskDTO.getId()+":TaskTrigger",
                        "TaskTriggers",
                        taskDTO.getId()+":Task",
                        "Tasks"
                );
            log.info(System.nanoTime()+" end");
            //}
            taskService.updateTask(taskDTO);
            return "redirect:/";
        }
        else {
            return "CurTaskDemoPage";
        }
    }

    @PatchMapping("/tasks/addContact")
    public String updateTaskInfoContact(Model model, @ModelAttribute("task") TaskDTO taskDTO, BindingResult result){
        taskValidator.validateNewContact(result, taskDTO.getNewContact());
        Person person=personService.getById(taskDTO.getPersonId());
        addPersonDetails(person, model);
        if (!result.hasErrors()){
            ContactDTO contactDTO= ContactDTO.builder().email(taskDTO.getNewContact()).build();
            addContactToTaskDTOSet(contactDTO,taskDTO);
        }
        return "CurTaskDemoPage";
    }

    @PatchMapping("/tasks/deleteContact/{ind}")
    public String deleteContact(@PathVariable("ind") int ind, Model model, @ModelAttribute("task") TaskDTO taskDTO){
        if (taskDTO.getContactDTOSet()!=null){
            taskDTO.getContactDTOSet().remove(ind);
        }
        Person person=personService.getById(taskDTO.getPersonId());
        addPersonDetails(person, model);
        return "CurTaskDemoPage";
    }

    @GetMapping("/tasks/add/{id}")
    public String addNewTask(@PathVariable("id") Long personId, Model model, Principal principal){
        if (!Objects.equals(personService.getPerson(principal.getName()).getId(), personId)) return "redirect:/";
        Person person=personService.getById(personId);
        List<ContactDTO> contactDTOS=new ArrayList<>();
        contactDTOS.add(ContactDTO.builder().email(person.getEmail()).build());
        TaskDTO taskDTO=TaskDTO.builder().contactDTOSet(contactDTOS).personId(personId).build();
        model.addAttribute("task", taskDTO);
        addPersonDetails(person, model);
        return "TaskAddPage";
    }

    @PostMapping("/tasks/add")
    public String addTaskToPerson(@ModelAttribute("task") TaskDTO taskDTO, BindingResult result, Model model){
        taskValidator.validateTask(result, taskDTO);
        if (!result.hasErrors()){
            Task task=personService.addTaskToPerson(taskDTO, taskDTO.getPersonId());
            quartzService.addNewJob(
                    quartzService.createJobDetail(TaskJob.class, task.getId()+":Task", "Tasks"),
                    quartzService.createTrigger(task.getId()+":TaskTrigger", "TaskTriggers", task.getEventTime())
            );
            return "redirect:/";
        }else {
            Person person=personService.getById(taskDTO.getPersonId());
            addPersonDetails(person, model);
            System.out.println(taskDTO.getContactDTOSet());
            return "TaskAddPage";
        }
    }

    @PostMapping("/tasks/addContact")
    public String addContactToTask(@ModelAttribute("task") TaskDTO taskDTO, Model model, BindingResult result){
        Person person=personService.getById(taskDTO.getPersonId());
        String n=taskDTO.getNewContact();
        ContactDTO contactDTO=ContactDTO.builder().email(n).build();
        taskValidator.validateNewContact(result,taskDTO.getNewContact());
        if (!result.hasErrors()){
            addContactToTaskDTOSet(contactDTO,taskDTO);
        }
        addPersonDetails(person, model);
        return "TaskAddPage";
    }

    @PostMapping("/tasks/deleteContact/{ind}")
    public String deleteContactFromTask(@PathVariable("ind") int ind,@ModelAttribute("task") TaskDTO taskDTO, Model model){
        Person person=personService.getById(taskDTO.getPersonId());
        if (taskDTO.getContactDTOSet()!=null) taskDTO.getContactDTOSet().remove(ind);
        addPersonDetails(person, model);
        return "TaskAddPage";
    }

    private void addPersonDetails(Person person, Model model){
        model.addAttribute("nick", person.getNickName());
        model.addAttribute("persId", person.getId());
    }

    private void addContactToTaskDTOSet(ContactDTO contactDTO, TaskDTO taskDTO){
        if (taskDTO.getContactDTOSet()==null){
            List<ContactDTO> list=new ArrayList<>();
            list.add(contactDTO);
            taskDTO.setContactDTOSet(list);

        } else if (!taskDTO.getContactDTOSet().contains(contactDTO)){
            taskDTO.getContactDTOSet().add(contactDTO);
        }
    }
}
