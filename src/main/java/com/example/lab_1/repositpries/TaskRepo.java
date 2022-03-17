package com.example.lab_1.repositpries;

import com.example.lab_1.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {
    @Query("select t from Task t left join fetch t.contacts where t.id=:id")
    Task findByIdWithContacts(@Param("id")Long id);
    Optional<Task> findById(Long id);
}
