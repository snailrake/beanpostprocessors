package com.example.beanpostprocessors;

import com.example.beanpostprocessors.bean.BeanWithName;
import com.example.beanpostprocessors.bean.DefaultBean;
import com.example.beanpostprocessors.bean.DefaultSecondBean;
import com.example.beanpostprocessors.bean.ToStringBean;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class BeanpostprocessorsApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext("com.example.beanpostprocessors");
        BeanWithName testBean = ctx.getBean(BeanWithName.class);
        DefaultBean defaultBean = ctx.getBean(DefaultBean.class);
        DefaultSecondBean defaultSecondBean = ctx.getBean(DefaultSecondBean.class);
        ToStringBean toStringBean = ctx.getBean(ToStringBean.class);
        int random = ctx.getBean("random", Integer.class);
        int random2 = ctx.getBean("random", Integer.class);
        System.out.println(testBean.getName());
        System.out.println(defaultBean.getName());
        System.out.println(defaultSecondBean.getAge());
        System.out.println(toStringBean);
        System.out.println(random);
        System.out.println(random2);
    }
}
