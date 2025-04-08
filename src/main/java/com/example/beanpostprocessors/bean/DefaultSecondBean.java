package com.example.beanpostprocessors.bean;

import com.example.beanpostprocessors.annotation.Default;
import org.springframework.stereotype.Component;

@Component
public class DefaultSecondBean {

    @Default("age")
    private int age;

    public int getAge() {
        return age;
    }
}
