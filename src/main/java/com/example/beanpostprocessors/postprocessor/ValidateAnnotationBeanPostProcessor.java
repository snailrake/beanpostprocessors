package com.example.beanpostprocessors.postprocessor;

import com.example.beanpostprocessors.annotation.Validate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@Component
public class ValidateAnnotationBeanPostProcessor implements BeanPostProcessor {

    private final ApplicationContext context;

    public ValidateAnnotationBeanPostProcessor(ApplicationContext context) {
        this.context = context;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        try {
            List<String> rules = getRules(bean);
            if (!rules.isEmpty()) {
                for (String rule : rules) {
                    Object ruleBean = context.getBean(rule);
                    for (Method method : ruleBean.getClass().getMethods()) {
                        if (method.getParameterCount() == 1 && bean.getClass().isAssignableFrom(method.getParameterTypes()[0])) {
                            method.invoke(ruleBean, bean);
                        }
                    }
                }
            }
            return bean;
        } catch (Exception e) {
            throw new BeanCreationException("Ошибка создания бина " + beanName, e);
        }
    }

    private List<String> getRules(Object bean) {
        if (bean.getClass().isAnnotationPresent(Validate.class)) {
            return Arrays.asList(bean.getClass().getAnnotation(Validate.class).value());
        }
        List<String> rules = new ArrayList<>();
        for (Annotation annotation : bean.getClass().getAnnotations()) {
            if (annotation.annotationType().isAnnotationPresent(Validate.class)) {
                rules.addAll(Arrays.asList(annotation.annotationType().getAnnotation(Validate.class).value()));
            }
        }
        return rules;
    }
}
