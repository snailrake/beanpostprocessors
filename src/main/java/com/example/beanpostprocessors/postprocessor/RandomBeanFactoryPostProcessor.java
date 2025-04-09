package com.example.beanpostprocessors.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (!beanFactory.containsBeanDefinition("random")) {
            if (beanFactory instanceof BeanDefinitionRegistry registry) {
                GenericBeanDefinition random = new GenericBeanDefinition();
                random.setBeanClass(Integer.class);
                random.setInstanceSupplier(() -> new Random().nextInt(101));
                random.setScope(BeanDefinition.SCOPE_PROTOTYPE);
                registry.registerBeanDefinition("random", random);
            }
        }
    }
}
