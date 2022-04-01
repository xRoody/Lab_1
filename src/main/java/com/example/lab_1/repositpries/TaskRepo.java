package com.example.lab_1.repositpries;

import com.example.lab_1.models.Person;
import com.example.lab_1.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {
    @Query("select t from Task t left join fetch t.contacts where t.id=:id")
    Task findByIdWithContacts(@Param("id") Long id);

    Optional<Task> findById(Long id);

    @Query("select t from Task t where t.name=:name and t.person.id=:personId")
    List<Task> getAllByPersonSortByTaskName(@Param("name") String name, @Param("personId") Long personId);

    List<Task> findAllByPersonOrderByEventTimeAsc(Person person);

    List<Task> findAllByPersonOrderByEventTimeDesc(Person person);

    List<Task> findAllByPersonOrderByName(Person person);

    List<Task> findAllByPersonAndNameOrderByEventTimeAsc(Person person, String name);

    List<Task> findAllByPersonAndNameOrderByEventTimeDesc(Person person, String name);

    List<Task> findAllByPersonAndNameOrderByName(Person person, String name);
}
