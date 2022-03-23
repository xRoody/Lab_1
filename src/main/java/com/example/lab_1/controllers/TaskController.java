package com.example.lab_1.controllers;

import com.example.lab_1.DTOs.ContactDTO;
import com.example.lab_1.DTOs.TaskDTO;
import com.example.lab_1.models.Person;
import com.example.lab_1.models.Task;
import com.example.lab_1.service.ContactService;
import com.example.lab_1.service.PersonService;
import com.example.lab_1.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    private final TaskService taskService;
    private final PersonService personService;
    private final ContactService contactService;

    @DeleteMapping("/tasks/{id}")
    public String deleteTask(@PathVariable("id") Long id){
        taskService.removeTaskById(id);
        return "redirect:/";
    }

    @GetMapping("/tasks/{id}")
    public String getTaskInfo(@PathVariable("id") Long id, Model model){
        TaskDTO taskDTO=taskService.getDTOByTaskById(id);
        Person person=personService.getById(taskDTO.getPersonId());
        model.addAttribute("nick", person.getNickName());
        model.addAttribute("persId", taskDTO.getPersonId());
        model.addAttribute("task",taskDTO);
        return "CurTaskDemoPage";
    }

    @PatchMapping("/tasks")
    public String updateTaskInfo(@ModelAttribute("task") TaskDTO taskDTO){
        taskService.updateTask(taskDTO);
        return "redirect:/";
    }

    @PatchMapping("/tasks/addContact")
    public String updateTaskInfoContact(Model model, @ModelAttribute("task") TaskDTO taskDTO){
        ContactDTO contactDTO= ContactDTO.builder().email(taskDTO.getNewContact()).build();
        if (taskDTO.getContactDTOSet()==null){
            List<ContactDTO> list=new ArrayList<>();
            list.add(contactDTO);
            taskDTO.setContactDTOSet(list);
        } else if(!taskDTO.getContactDTOSet().contains(contactDTO)){
            taskDTO.getContactDTOSet().add(contactDTO);
        }
        Person person=personService.getById(taskDTO.getPersonId());
        model.addAttribute("nick", person.getNickName());
        model.addAttribute("persId", taskDTO.getPersonId());
        model.addAttribute("task",taskDTO);
        return "CurTaskDemoPage";
    }

    @PatchMapping("/tasks/deleteContact/{ind}")
    public String deleteContact(@PathVariable("ind") int ind, Model model, @ModelAttribute("task") TaskDTO taskDTO){
        if (taskDTO.getContactDTOSet()!=null){
            taskDTO.getContactDTOSet().remove(ind);
        }
        Person person=personService.getById(taskDTO.getPersonId());
        model.addAttribute("nick", person.getNickName());
        model.addAttribute("persId", taskDTO.getPersonId());
        model.addAttribute("task",taskDTO);
        return "CurTaskDemoPage";
    }

    @GetMapping("/tasks/add/{id}")
    public String addNewTask(@PathVariable("id") Long personId, Model model){
        Person person=personService.getById(personId);
        List<ContactDTO> contactDTOS=new ArrayList<>();
        contactDTOS.add(ContactDTO.builder().email(person.getEmail()).build());
        TaskDTO taskDTO=TaskDTO.builder().contactDTOSet(contactDTOS).personId(personId).build();
        model.addAttribute("task", taskDTO);
        model.addAttribute("nick", person.getNickName());
        model.addAttribute("persId", personId);
        return "TaskAddPage";
    }

    @PostMapping("/tasks/add")
    public String addTaskToPerson(@ModelAttribute("task") TaskDTO taskDTO){
        personService.addTaskToPerson(taskDTO, taskDTO.getPersonId());
        return "redirect:/";
    }

    @PostMapping("/tasks/addContact")
    public String addContactToTask(@ModelAttribute("task") TaskDTO taskDTO, Model model){
        Person person=personService.getById(taskDTO.getPersonId());
        String n=taskDTO.getNewContact();
        ContactDTO contactDTO=ContactDTO.builder().email(n).build();
        if (taskDTO.getContactDTOSet()==null){
            List<ContactDTO> list=new ArrayList<>();
            list.add(contactDTO);
            taskDTO.setContactDTOSet(list);

        } else if (!taskDTO.getContactDTOSet().contains(contactDTO)){
            taskDTO.getContactDTOSet().add(contactDTO);
        }
        model.addAttribute("task", taskDTO);
        model.addAttribute("nick", person.getNickName());
        model.addAttribute("persId", person.getId());
        return "TaskAddPage";
    }

    @PostMapping("/tasks/deleteContact/{ind}")
    public String deleteContactFromTask(@PathVariable("ind") int ind,@ModelAttribute("task") TaskDTO taskDTO, Model model){
        Person person=personService.getById(taskDTO.getPersonId());
        if (taskDTO.getContactDTOSet()!=null) taskDTO.getContactDTOSet().remove(ind);
        model.addAttribute("task", taskDTO);
        model.addAttribute("nick", person.getNickName());
        model.addAttribute("persId", person.getId());
        return "TaskAddPage";
    }
}
