package com.example.lab_1.validators;

import com.example.lab_1.DTOs.PersonDTO;
import com.example.lab_1.service.PersonService;
import com.example.lab_1.validationExceptions.UniqueEmailException;
import com.example.lab_1.validationExceptions.UniqueLoginException;
import com.example.lab_1.validationExceptions.UniqueNickNameException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class RegistrationValidator {
    private final PersonService personService;
    private static final Pattern loginPattern = Pattern.compile("[a-zA-Z\\d]([A-Za-z\\d]|[!@#$%&])*[a-zA-Z\\d]");
    private static final Pattern passwordPattern = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#@$!%?&])[A-za-z\\d#@$!%?&]*");
    private static final Pattern usernamePattern = Pattern.compile("[a-zA-Z\\d]*([#@!$%^_]|[a-zA-Z0-9])[a-zA-Z\\d]");
    private static final Pattern emailPattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])");

    public void validate(BindingResult result, PersonDTO personDTO) {
        //login validation
        if (personDTO.getLogin() == null)
            result.addError(new FieldError("personDTO", "login", "Please enter your login"));
        else if (personDTO.getLogin().length() <= 3)
            result.addError(new FieldError("personDTO", "login", "Login must be over 3"));
        else if (personDTO.getLogin().length() >= 20)
            result.addError(new FieldError("personDTO", "login", "Login must be less then 20"));
        else {
            Matcher matcher = loginPattern.matcher(personDTO.getLogin());
            if (!matcher.matches()) {
                result.addError(new FieldError("personDTO", "login", "Login must contains latin characters, numbers and $@#()!_- only"));
            }
        }
        //password validation
        if (personDTO.getPassword() == null)
            result.addError(new FieldError("personDTO", "password", "Please enter your password"));
        else if (personDTO.getPassword().length() <= 5)
            result.addError(new FieldError("personDTO", "password", "Password must be over 5"));
        else if (personDTO.getPassword().length() >= 25)
            result.addError(new FieldError("personDTO", "password", "Password must be less then 25"));
        else {
            Matcher matcher = passwordPattern.matcher(personDTO.getPassword());
            if (!matcher.matches()) {
                result.addError(new FieldError("personDTO", "password", "Password must including one uppercase letter, one special character and alphanumeric characters"));
            }
        }
        //Check password block
        if (personDTO.getR_password() == null)
            result.addError(new FieldError("personDTO", "r_password", "Please confirm your password"));
        else if (personDTO.getPassword()!=null && !personDTO.getPassword().equals(personDTO.getR_password()))
            result.addError(new FieldError("personDTO", "r_password", "Password are not the same"));
        //Username validation
        if (personDTO.getUsername() == null)
            result.addError(new FieldError("personDTO", "username", "Please enter your username"));
        else if (personDTO.getUsername().length() <= 3)
            result.addError(new FieldError("personDTO", "username", "Username must be over 3"));
        else if (personDTO.getUsername().length() >= 25)
            result.addError(new FieldError("personDTO", "username", "Username must less then 25"));
        else {
            Matcher matcher = usernamePattern.matcher(personDTO.getUsername());
            if (!matcher.matches())
                result.addError(new FieldError("personDTO", "username", "Username can contain latin characters, numbers and #@!$%^_ symbols only"));
        }
        //Email validation
        if (personDTO.getEmail() == null)
            result.addError(new FieldError("personDTO", "email", "Please enter your email"));
        else {
            Matcher matcher = emailPattern.matcher(personDTO.getEmail());
            if (!matcher.matches()) result.addError(new FieldError("personDTO", "email", "Please enter correct email"));
        }
        //Try to register user
        try {
            personService.validateDto(personDTO);
        } catch (UniqueNickNameException e) {
            result.addError(new FieldError("personDTO","username", e.getMessage()));
        } catch (UniqueLoginException e) {
            result.addError(new FieldError("personDTO","login", e.getMessage()));
        } catch (UniqueEmailException e) {
            result.addError(new FieldError("personDTO","email", e.getMessage()));
        }
    }
}
