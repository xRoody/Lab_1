package com.example.lab_1.service;

import com.example.lab_1.models.Role;

import java.util.List;
import java.util.Set;

public interface RoleService{
    Role saveRole(Role role);
    void removeRole(Role role);
    List<Role> getAllRoles();
}
