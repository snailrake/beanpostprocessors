package com.example.beanpostprocessors.postprocessor;

import com.example.beanpostprocessors.annotation.ToString;
import com.example.beanpostprocessors.handler.ToStringProxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class ToStringAnnotationBeanPostProcessor implements BeanPostProcessor {

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (hasToStringAnnotation(bean)) {
            return ToStringProxy.create(bean);
        }
        return bean;
    }

    private boolean hasToStringAnnotation(Object bean) {
        if (bean.getClass().isAnnotationPresent(ToString.class)) {
            return true;
        }
        for (Field field : bean.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(ToString.class)) {
                return true;
            }
        }
        return false;
    }
}
