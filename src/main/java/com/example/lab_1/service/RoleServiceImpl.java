package com.example.lab_1.service;

import com.example.lab_1.DTOs.RoleDTO;
import com.example.lab_1.models.Role;
import com.example.lab_1.repositpries.RoleRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService{
    private final RoleRepo roleRepo;

    @Override
    public Role saveRole(RoleDTO role){
        return saveRole(Role.builder().name(role.getRoleName().toUpperCase()).build());
    }


    private Role saveRole(Role role) {
        log.info("save new role {}", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void removeRole(Role role) {
        log.info("remove role {}", role.getName());
        roleRepo.delete(role);
    }

    @Override
    public List<Role> getAllRoles() {
        log.info("fetch all roles");
        return roleRepo.findAll();
    }
}
