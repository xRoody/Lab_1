package com.example.lab_1.repositpries;


import com.example.lab_1.models.Person;
import com.example.lab_1.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
