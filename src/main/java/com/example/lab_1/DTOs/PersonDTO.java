package com.example.lab_1.DTOs;


import lombok.Builder;
import lombok.Data;
/*
* This class contain all person usefully information (DTO)
* */
@Data
@Builder
public class PersonDTO {
    private Long id;
    private String login;
    private String password;
    private String r_password;
    private String nickName;
    private String email;
}
