package com.example.beanpostprocessors.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class StringNameFillerBeanPostProcessor implements BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        try {
            Field[] fields = bean.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("name".equals(field.getName()) && field.getType() == String.class) {
                    field.setAccessible(true);
                    if (field.get(bean) == null) {
                        field.set(bean, "vasia");
                    }
                }
            }
        } catch (Exception e) {
            throw new BeanCreationException("Ошибка при создании бина" + beanName, e);
        }
        return bean;
    }
}
