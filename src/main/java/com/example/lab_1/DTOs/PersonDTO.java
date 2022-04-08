package com.example.lab_1.DTOs;


import lombok.Builder;
import lombok.Data;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return Objects.equals(id, personDTO.id) && Objects.equals(login, personDTO.login) && Objects.equals(password, personDTO.password) && Objects.equals(r_password, personDTO.r_password) && Objects.equals(nickName, personDTO.nickName) && Objects.equals(email, personDTO.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, r_password, nickName, email);
    }
}
