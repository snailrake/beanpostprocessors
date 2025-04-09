package com.example.beanpostprocessors.bean;

import com.example.beanpostprocessors.annotation.ToString;
import org.springframework.stereotype.Component;

import static com.example.beanpostprocessors.enums.ToStringValue.NO;
import static com.example.beanpostprocessors.enums.ToStringValue.YES;

@Component
@ToString(YES)
public class ToStringBean {

    private String value = "value";

    @ToString(NO)
    private int secondValue = 1;
}
