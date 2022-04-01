package com.example.lab_1.controllers;

import com.example.lab_1.DTOs.PersonDTO;
import com.example.lab_1.models.Person;
import com.example.lab_1.service.PersonService;
import com.example.lab_1.validators.PersonValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

/*
* This class is Person page controller.
* */

@Controller
@RequiredArgsConstructor
public class PersonController {
    /*
     * personService is used to interact with person model
     * */
    private final PersonService personService;
    /*
    * personValidator is used to validate person data
    * */
    private final PersonValidator personValidator;


    /*
     * This method is used to avoid empty strings (like "" or "    ") or meaningless whitespaces
     * */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    /*
    * Return person personal area.
    * @param id - person id
    * @param principal - authorized user
    * @param model - instance of Model class (used to add model attributes)
    * */
    @GetMapping("/person/{id}")
    public String getPerson(@PathVariable("id") Long id, Principal principal, Model model){
        Person person=personService.getPerson(principal.getName());
        if (!Objects.equals(person.getId(), id)) return "redirect:/";
        PersonDTO dto=PersonDTO.builder().nickName(person.getNickName()).email(person.getEmail()).id(person.getId()).build();
        model.addAttribute("person", dto);
        return "PersonPage";
    }

    /*
     * Update person data
     * @param id - person id
     * @param personDTO - new person data from form
     * @param result - instance of BindingResult class (used to add fields validation errors)
     * */
    @PostMapping("/person/update/{id}")
    public String updatePerson(@PathVariable("id") Long id, @ModelAttribute("person") PersonDTO personDTO, BindingResult result){
        personValidator.validateUpdate(result,personDTO);
        if (result.hasErrors()){
            Person person=personService.getById(personDTO.getId());
            personDTO.setEmail(person.getEmail());
            personDTO.setNickName(person.getNickName());
            return "PersonPage";
        }
        personService.updatePerson(personDTO);
        return "redirect:/person/"+id;
    }

    /*
     * Delete person
     * @param id - person id
     * @param personDTO - person data from form
     * @param result - instance of BindingResult class (used to add fields validation errors)
     * */
    @PostMapping("/person/delete/{id}")
    public String deletePerson(@PathVariable("id") Long id, @ModelAttribute("person") PersonDTO personDTO, BindingResult result){
        personValidator.validateDelete(result,personDTO);
        if (result.hasErrors()) return "PersonPage";
        personService.removePerson(id);
        return "redirect:/login";
    }
}
