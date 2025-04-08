package com.example.beanpostprocessors.bean;

import com.example.beanpostprocessors.annotation.Default;
import org.springframework.stereotype.Component;

@Default("appConfig")
@Component
public class DefaultBean {

    private String name;

    public String getName() {
        return name;
    }
}
