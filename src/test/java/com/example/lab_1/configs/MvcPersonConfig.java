package com.example.lab_1.configs;

import com.example.lab_1.repositpries.PersonRepo;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("PersonMVC")
public class MvcPersonConfig {
    @Bean
    @Primary
    public PersonRepo personRepo(){
        return Mockito.mock(PersonRepo.class);
    }
}


