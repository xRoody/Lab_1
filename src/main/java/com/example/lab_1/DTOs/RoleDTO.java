package com.example.lab_1.DTOs;

import lombok.Builder;
import lombok.Data;
/*
 * This class contain all role usefully information (DTO)
 * */
@Data
@Builder
public class RoleDTO {
    private String roleName;
}
