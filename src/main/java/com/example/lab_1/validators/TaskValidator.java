package com.example.lab_1.validators;

import com.example.lab_1.DTOs.ContactDTO;
import com.example.lab_1.DTOs.TaskDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

/*
 * This class is custom validator to task
 * */

@Component
public class TaskValidator {
    private static final Pattern emailPattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])");

    /*
     * This method is used to validate task by task dto
     * @param taskDTO - task data by task dto
     * @param result - result set to add field errors
     * */
    public void validateTask(BindingResult result, TaskDTO taskDTO) {
        if (taskDTO.getName() == null) {
            result.addError(new FieldError("TaskDTO", "name", "Please enter the task title"));
        }
        if (taskDTO.getEventTime() == null) {
            result.addError(new FieldError("TaskDTO", "eventTime", "Please enter the event time of title"));
        } else if (LocalDateTime.parse(taskDTO.getEventTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME).isBefore(LocalDateTime.now())) {
            result.addError(new FieldError("TaskDTO", "eventTime", taskDTO.getEventTime(), true, null, null, "Invalid event time. This time is passed"));
        }
        if (taskDTO.getNewContact() != null) validateNewContact(result, taskDTO.getNewContact());

        if (taskDTO.getContactDTOSet() == null) {
            result.addError(new FieldError("TaskDTO", "contactDTOSet", "Please enter contacts"));
        } else validateContactDTOSet(result, taskDTO.getContactDTOSet());
    }

    /*
     * This method is used to validate new contact
     * */
    public void validateNewContact(BindingResult result, String mail) {
        validateMail(result, mail, "newContact");
    }

    /*
     * This method is used to validate email
     * @param email - validated email
     * @param result - result set to add field errors
     * */
    private void validateMail(BindingResult result, String mail, String fieldName) {
        if (mail == null || mail.isBlank()) {
            result.addError(new FieldError("TaskDTO", fieldName, "Please enter the new mail address"));
        } else if (!emailPattern.matcher(mail).matches()) {
            result.addError(new FieldError("TaskDTO", fieldName, mail, true, null, null, "Incorrect email address"));
        }
    }

    /*
     * This method is used to validate contact set (list)
     * @param list - validated list
     * @param result - result set to add field errors
     * */
    private void validateContactDTOSet(BindingResult result, List<ContactDTO> list) {
        for (int i = 0; i < list.size(); i++) {
            validateMail(result, list.get(i).getEmail(), "contactDTOSet[" + i + "].email");
        }
    }
}
