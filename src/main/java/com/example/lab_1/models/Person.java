package com.example.lab_1.models;

import lombok.*;


import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long id;
    @Column(unique = true)
    private String login;
    @Column
    private String password;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String nickName;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Collection<Role> roles = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "person")
    private Collection<Task> tasks = new HashSet<>();

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", nickName='" + nickName + '\'' +
                ", roles=" + roles +

                '}';
    }
}
