package com.example.beanpostprocessors.postprocessor;

import com.example.beanpostprocessors.annotation.Default;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DefaultFieldValuesFillerBeanPostProcessor implements BeanPostProcessor {

    private final ApplicationContext context;

    public DefaultFieldValuesFillerBeanPostProcessor(ApplicationContext applicationContext) {
        this.context = applicationContext;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        String defaultBeanNameOnClass = bean.getClass().isAnnotationPresent(Default.class)
                ? bean.getClass().getAnnotation(Default.class).value()
                : null;
        List<Field> beanFields = getAllBeanFields(bean.getClass());

        beanFields.forEach(field -> {
            try {
                Object fieldValue = null;
                if (defaultBeanNameOnClass != null) {
                    field.setAccessible(true);
                    Object obj = context.getBean(defaultBeanNameOnClass);
                    String defaultFieldGetterName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                    Method defaultFieldGetter = obj.getClass().getDeclaredMethod(defaultFieldGetterName);
                    fieldValue = defaultFieldGetter.invoke(obj);
                } else if (field.isAnnotationPresent(Default.class)) {
                    field.setAccessible(true);
                    String defaultBeanNameOnField = field.getAnnotation(Default.class).value();
                    fieldValue = context.getBean(defaultBeanNameOnField);
                }
                if (fieldValue != null) {
                    field.set(bean, fieldValue);
                }
            } catch (Exception e) {
                throw new BeanCreationException("Ошибка создания бина" + beanName, e);
            }
        });
        return bean;
    }

    private List<Field> getAllBeanFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null && Object.class != clazz) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}
