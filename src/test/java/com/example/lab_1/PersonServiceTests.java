package com.example.lab_1;

import com.example.lab_1.DTOs.PersonDTO;
import com.example.lab_1.models.Person;
import com.example.lab_1.models.Role;
import com.example.lab_1.repositpries.PersonRepo;
import com.example.lab_1.service.PersonServiceImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
class PersonServiceTests {
    @Mock
    public PersonRepo personRepo;

    @InjectMocks
    public PersonServiceImpl personService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeClass


    @Test
    void getPersonDTOByPersonId() {
        Person person=Person.builder()
                .id(1l)
                .login("xRoody")
                .nickName("Wolf")
                .email("sasha_ermak_2002@mail.ru")
                .build();
        PersonDTO personDTO=PersonDTO.builder()
                .id(1l)
                .login("xRoody")
                .nickName("Wolf")
                .email("sasha_ermak_2002@mail.ru")
                .build();
        Mockito.when(personRepo.getById(1l)).thenReturn(person);
        PersonDTO result=personService.getDTObyId(1l);
        Assertions.assertEquals(personDTO, result);
    }

    @Test
    void personTryToLoginTest(){
        Person person=Person.builder()
                .id(1l)
                .login("xRoody")
                .nickName("Wolf")
                .email("sasha_ermak_2002@mail.ru")
                .password("12345")
                .roles(List.of(Role.builder().id(1l).name("USER").build()))
                .build();
        Mockito.when(personRepo.findByLogin("xRoody")).thenReturn(person);
        User user=new User("xRoody", "12345", List.of(new SimpleGrantedAuthority("USER")));
        Assertions.assertEquals(user, personService.loadUserByUsername("xRoody"));
    }
}
