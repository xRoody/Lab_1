package com.example.lab_1.validators;

import com.example.lab_1.DTOs.PersonDTO;
import com.example.lab_1.models.Person;
import com.example.lab_1.service.PersonService;
import com.example.lab_1.validationExceptions.UniqueEmailException;
import com.example.lab_1.validationExceptions.UniqueLoginException;
import com.example.lab_1.validationExceptions.UniqueNickNameException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class PersonValidator {
    private final PersonService personService;
    private final BCryptPasswordEncoder encoder;
    private static final Pattern loginPattern = Pattern.compile("[a-zA-Z\\d]([A-Za-z\\d]|[#@$!%?&])*[a-zA-Z\\d]");
    private static final Pattern passwordPattern = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#@$!%?&])[A-za-z\\d#@$!%?&]*");
    private static final Pattern usernamePattern = Pattern.compile("[a-zA-Z\\d]*([#@!$%^_]|[a-zA-Z0-9])[a-zA-Z\\d]");
    private static final Pattern emailPattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])");

    public void validateRegistration(BindingResult result, PersonDTO personDTO) {
        //login validation
        if (validateString(personDTO.getLogin(), "login", 20, 3, loginPattern, result))
            validateUniqueLogin(personDTO.getLogin(), result);
        //password validation
        validateString(personDTO.getPassword(), "password", 25, 5, passwordPattern, result);
        //Check password block
        checkPasswords(personDTO.getPassword(), personDTO.getR_password(), result);
        //Username validation
        if (validateString(personDTO.getNickName(), "username", 25, 3, usernamePattern, result))
            validateUniqueUsername(personDTO.getNickName(), result);
        //Email validation
        if (emailValidation(personDTO.getEmail(), result)) validateUniqueEmail(personDTO.getEmail(), result);
        //Try to register user
    }

    public void validateUpdate(BindingResult result, PersonDTO personDTO) {
        Person person = personService.getById(personDTO.getId());
        if (!person.getNickName().equals(personDTO.getNickName()) && validateString(personDTO.getNickName(), "nickName", 25, 3, usernamePattern, result))
            validateUniqueUsername(personDTO.getNickName(), result);
        if (!person.getEmail().equals(personDTO.getEmail()) && emailValidation(personDTO.getEmail(), result))
            validateUniqueEmail(personDTO.getEmail(), result);
        if (personDTO.getR_password() != null)
            validateString(personDTO.getR_password(), "r_password", 25, 5, passwordPattern, result);
        validateOldPasswordToUDOperations(person.getPassword(), personDTO.getPassword(), result);
    }

    public void validateDelete(BindingResult result, PersonDTO personDTO) {
        Person person = personService.getById(personDTO.getId());
        validateOldPasswordToUDOperations(person.getPassword(), personDTO.getPassword(), result);
    }

    private void validateOldPasswordToUDOperations(String encodedOldPassword, String oldPassword, BindingResult result) {
        if (oldPassword == null) result.addError(new FieldError("personDTO", "password", "Please enter your password"));
        else if (!encoder.matches(oldPassword, encodedOldPassword)) {
            result.addError(new FieldError("personDTO", "password", "Wrong password"));
        }
    }

    private boolean validateString(String str, String name, int max, int min, Pattern pattern, BindingResult result) {
        boolean f = true;
        if (str == null) {
            result.addError(new FieldError("personDTO", name, "Please enter your " + name));
            f = false;
        } else if (str.length() <= min) {
            result.addError(new FieldError("personDTO", name, str, true, null, null,  name + " must be over 3"));
            f = false;
        } else if (str.length() >= max) {
            result.addError(new FieldError("personDTO", name, str, true, null, null, name + " must be less then 20"));
            f = false;
        } else {
            Matcher matcher = pattern.matcher(str);
            if (!matcher.matches()) {
                result.addError(new FieldError("personDTO", name, str, true, null, null, name + " must contains latin characters, numbers and #@$!%?& only"));
                f = false;
            }
        }
        return f;
    }

    private void checkPasswords(String password, String r_password, BindingResult result) {
        if (r_password == null)
            result.addError(new FieldError("personDTO", "r_password", "Please confirm your password"));
        else if (password != null && !password.equals(r_password))
            result.addError(new FieldError("personDTO", "r_password", "Password are not the same"));
    }

    private boolean emailValidation(String email, BindingResult result) {
        boolean f = true;
        if (email == null) {
            result.addError(new FieldError("personDTO", "email", "Please enter your email"));
            f = false;
        } else {
            Matcher matcher = emailPattern.matcher(email);
            if (!matcher.matches()) {
                result.addError(new FieldError("personDTO", "email",email, true, null, null, "Please enter correct email"));
                f = false;
            }
        }
        return f;
    }

    private void validateUniqueEmail(String email, BindingResult result) {
        try {
            personService.validateEmail(email);
        } catch (UniqueEmailException e) {
            result.addError(new FieldError("personDTO", "email",email, true, null, null, e.getMessage()));
        }
    }

    private void validateUniqueLogin(String login, BindingResult result) {
        try {
            personService.validateLogin(login);
        } catch (UniqueLoginException e) {
            result.addError(new FieldError("personDTO", "login",login, true, null, null, e.getMessage()));
        }
    }

    private void validateUniqueUsername(String username, BindingResult result) {
        try {
            personService.validateUsername(username);
        } catch (UniqueNickNameException e) {
            result.addError(new FieldError("personDTO", "nickName",username, true, null, null, e.getMessage()));
        }
    }
}
