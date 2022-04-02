package com.example.lab_1.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/*
* This class is contact DTO.
* */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {
    private Long id;
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactDTO that = (ContactDTO) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
