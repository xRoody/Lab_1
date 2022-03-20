package com.example.lab_1.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonDTO {
    private String login;
    private String password;
    private String username;
    private String email;
}
