package com.example.lab_1.controllers;

import com.example.lab_1.DTOs.PersonDTO;
import com.example.lab_1.service.PersonService;
import com.example.lab_1.validators.RegistrationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationValidator validator;
    private final PersonService personService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("personDto") PersonDTO personDTO) {
        return "RegPage";
    }

    @PostMapping("/registration")
    public String registrationMethod(@ModelAttribute("personDto") PersonDTO personDTO, BindingResult result, Model model) {
        validator.validate(result, personDTO);
        if (result.hasErrors()) return "RegPage";
        personService.registerPerson(personDTO);
        return "redirect:/login"; //to regConfirm page -> to login page
    }
}
