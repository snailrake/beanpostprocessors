package com.example.beanpostprocessors.annotation;

import com.example.beanpostprocessors.enums.ToStringValue;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.example.beanpostprocessors.enums.ToStringValue.YES;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ToString {

    ToStringValue value() default YES;
}
