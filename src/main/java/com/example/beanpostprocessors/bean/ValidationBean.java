package com.example.beanpostprocessors.bean;

import com.example.beanpostprocessors.annotation.Validate;
import org.springframework.stereotype.Component;

@Validate({"ageValidator", "nameValidator"})
@Component
public class ValidationBean {

    private String name = "vasia";

    private int age = 18;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
