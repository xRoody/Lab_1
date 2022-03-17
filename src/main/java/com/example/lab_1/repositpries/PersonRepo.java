package com.example.lab_1.repositpries;

import com.example.lab_1.models.Person;
import com.example.lab_1.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {
    Person findByLogin(String login);
    @Query("select p from Person p left join fetch p.tasks where p.login=:login")
    Person findByLoginWithTasks(@Param("login")String login);
}
