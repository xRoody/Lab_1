package com.example.lab_1.controllers;

import com.example.lab_1.DTOs.TaskDTO;
import com.example.lab_1.models.Person;
import com.example.lab_1.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * This class is main page controller.
 *
 * */

@Controller
@RequiredArgsConstructor
public class IndexController {
    /*
     * personService is used to interact with person model
     * */
    private final PersonService personService;
    /*
     * sorts is list of all sorts
     * */
    private static final String NAME_SORT="name";
    private static final String OLD_FIRST="old first";
    private static final String YOUNG_FIRST="young first";
    private List<String> sorts = Stream.of(NAME_SORT, OLD_FIRST, YOUNG_FIRST).collect(Collectors.toList());

    /*
     * This method is used to avoid empty strings (like "" or "    ") or meaningless whitespaces
     * */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/")
    public String home(Principal principal, Model model, @RequestParam(name = "query", required = false, defaultValue = "") String query, @RequestParam(name = "sort", required = false, defaultValue = "name") String sort, @RequestParam(name = "sortGetter", required = false) String sortGetter) {
        Person person = personService.getPersonWithTasks(principal.getName());
        model.addAttribute("personName", person.getNickName());
        model.addAttribute("personId", person.getId());
        if (sort == null || !sorts.contains(sort)) sort = NAME_SORT;
        if (sortGetter != null) sort = sortGetter;
        model.addAttribute("sort", sort);
        List<String> other = new ArrayList<>(sorts);
        other.remove(sort);
        model.addAttribute("otherSorts", other);
        List<TaskDTO> result = getTasksByParams(sort, query, person);
        model.addAttribute("tasks", result);
        return "Home";
    }

    /*
     * This method is used to get sorted task list by sort name, query and person
     * @param sort - sort name
     * @param query - name of task (or tasks)
     * @param person - the tasks owner (person)
     * */
    private List<TaskDTO> getTasksByParams(String sort, String query, Person person) {
        List<TaskDTO> result = new ArrayList<>();
        if (query == null) {
            switch (sort) {
                case NAME_SORT -> result = personService.getAllByPersonSortByName(person);
                case YOUNG_FIRST -> result = personService.getAllByPersonSortByDataDSC(person);
                case OLD_FIRST -> result = personService.getAllByPersonSortByDataASC(person);
            }
        } else {
            switch (sort) {
                case NAME_SORT -> result = personService.getAllByPersonSortNameAndByName(person, query);
                case YOUNG_FIRST -> result = personService.getAllByPersonSortByDataDscAndName(person, query);
                case OLD_FIRST -> result = personService.getAllByPersonSortByDataASCAndName(person, query);
            }
        }
        return result;
    }
}
