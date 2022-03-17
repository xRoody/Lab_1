package com.example.lab_1.service;

import com.example.lab_1.models.Role;
import com.example.lab_1.repositpries.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService{
    private final RoleRepo roleRepo;

    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public void removeRole(Role role) {
        roleRepo.delete(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }
}
