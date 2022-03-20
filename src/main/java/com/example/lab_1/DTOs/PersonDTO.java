package com.example.lab_1.DTOs;


import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Data
@Builder
public class PersonDTO {
    private String login;
    private String password;
    private String r_password;
    private String username;
    private String email;
}
