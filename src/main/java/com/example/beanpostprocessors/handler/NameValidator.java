package com.example.beanpostprocessors.handler;

import com.example.beanpostprocessors.bean.ValidationBean;
import com.example.beanpostprocessors.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class NameValidator {

    public void isValidName(ValidationBean bean) {
        if (!bean.getName().equals("max")) {
            throw new ValidationException("name should be max");
        }
    }
}
