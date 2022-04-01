package com.example.lab_1.controllers;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;

/*
* This class is login page controller
* */
@Controller
public class LoginController {
    /*
     * This method is used to avoid empty strings (like "" or "    ") or meaningless whitespaces
     * */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
    /*
    * Return custom login page
    * */
    @GetMapping("/login")
    public String login(){
        return "LoginPage";
    }

    /*
    * Manually add custom wrong password error message
    * */
    @GetMapping("/login-error")
    public String showError(Model model){
        model.addAttribute("error", "Wrong login and password pair");
        return "LoginPage";
    }
}
