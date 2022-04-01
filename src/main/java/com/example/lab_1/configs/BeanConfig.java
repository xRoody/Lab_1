package com.example.lab_1.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/*
* Here we set some usefully beans
* */

@Configuration
public class BeanConfig {
    /*
    * BCryptPasswordEncoder bean to encode passwords
    * */
    @Bean
    BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}
