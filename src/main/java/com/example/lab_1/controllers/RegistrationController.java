package com.example.lab_1.controllers;

import com.example.lab_1.DTOs.PersonDTO;
import com.example.lab_1.service.PersonService;
import com.example.lab_1.validators.PersonValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
/*
 * Registration page controller
 * */

@Controller
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {
    /*
     * personValidator is used to validate person data
     * */
    private final PersonValidator validator;
    /*
     * personService is used to interact with person model
     * */
    private final PersonService personService;

    /*
     * This method is used to avoid empty strings (like "" or "    ") or meaningless whitespaces
     * */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    /*
     * This method  is used get registration page view
     * */
    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("personDto") PersonDTO personDTO) {
        return "RegPage";
    }

    /*
     * Register new person
     * @param personDTO - person registration data from form
     * @param result - instance of BindingResult class (used to add fields validation errors)
     * */
    @PostMapping("/registration")
    public String registrationMethod(@ModelAttribute("personDto") PersonDTO personDTO, BindingResult result) {
        log.debug("received person dto,try to register new person");
        validator.validateRegistration(result, personDTO);
        if (result.hasErrors()) return "RegPage";
        personService.registerPerson(personDTO);
        return "redirect:/login";
    }
}
