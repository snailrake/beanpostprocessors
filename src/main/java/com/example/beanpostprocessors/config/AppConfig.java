package com.example.beanpostprocessors.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public String getName() {
        return "Default value";
    }

    @Bean
    public int age() {
        return 10;
    }
}
