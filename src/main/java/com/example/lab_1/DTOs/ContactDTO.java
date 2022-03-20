package com.example.lab_1.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactDTO {
    private String email;
}
