package com.example.lab_1.service;

import com.example.lab_1.DTOs.RoleDTO;
import com.example.lab_1.models.Role;

import java.util.List;
import java.util.Set;

public interface RoleService{
    Role saveRole(RoleDTO role);
    void removeRole(String roleName);
    List<Role> getAllRoles();
}
