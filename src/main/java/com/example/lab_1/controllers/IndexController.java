package com.example.lab_1.controllers;

import com.example.lab_1.models.Person;
import com.example.lab_1.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final PersonService personService;

    @GetMapping("/")
    public String home(Principal principal, Model model) {
        Person person = personService.getPersonWithTasks(principal.getName());
        model.addAttribute("person", person);
        return "Home";
    }

}
