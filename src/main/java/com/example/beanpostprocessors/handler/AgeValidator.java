package com.example.beanpostprocessors.handler;

import com.example.beanpostprocessors.bean.ValidationBean;
import com.example.beanpostprocessors.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class AgeValidator {

    public void isValidAge(ValidationBean validationBean) {
        if (validationBean.getAge() < 21) {
            throw new ValidationException("Age must be 21+ years");
        }
    }
}
