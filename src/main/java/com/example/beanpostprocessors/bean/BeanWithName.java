package com.example.beanpostprocessors.bean;

import org.springframework.stereotype.Component;

@Component
public class BeanWithName {

    private String name;

    public String getName() {
        return name;
    }
}
