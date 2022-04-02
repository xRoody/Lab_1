package com.example.lab_1.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/*
* This class is controller.
* It used to redirect to custom error page instead of standard WhitePage
* */


@Controller
@Slf4j
public class CustomErrorController implements ErrorController {
    /*
    * This method use to process web errors.
    * @param request use to log error_status.
    * */
    @RequestMapping ("/error")
    public String handleError(HttpServletRequest request){
        log.error("Server error "+request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        return "ErrorPage";
    }

}
