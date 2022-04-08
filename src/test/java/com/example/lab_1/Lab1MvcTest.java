package com.example.lab_1;

import com.example.lab_1.DTOs.PersonDTO;
import com.example.lab_1.controllers.LoginController;
import com.example.lab_1.controllers.PersonController;
import com.example.lab_1.models.Person;
import com.example.lab_1.repositpries.PersonRepo;
import com.example.lab_1.service.PersonServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Array;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(PersonController.class)
@ActiveProfiles("PersonMVC")
public class Lab1MvcTest {

    private MockMvc mvc;

    @Mock
    private PersonRepo personRepo;

    @Mock
    private PersonServiceImpl personService;

    @InjectMocks
    private PersonController personController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.mvc= MockMvcBuilders.standaloneSetup(personController).build();
    }

    @Test
    public void getPerson() throws Exception {
        Person person=Person.builder()
                .id(1l)
                .login("xRoody")
                .nickName("Wolf")
                .email("sasha_ermak_2002@mail.ru")
                .build();
        Mockito.when(personService.getPerson("xRoody")).thenReturn(person);
        mvc
                .perform(get("/person/1").principal(new Principal() {
                    @Override
                    public String getName() {
                        return "xRoody";
                    }
                }))
                .andExpect(status().isOk()); //302 - redirect status
    }
}
