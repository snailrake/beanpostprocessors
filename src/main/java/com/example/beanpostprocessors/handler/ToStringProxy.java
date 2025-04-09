package com.example.beanpostprocessors.handler;

import com.example.beanpostprocessors.annotation.ToString;
import com.example.beanpostprocessors.enums.ToStringValue;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ToStringProxy {

    public static <T> T create(T t) {
        return (T) Enhancer.create(
                t.getClass(),
                new ToStringProxyHandler(t));
    }

    private static class ToStringProxyHandler implements InvocationHandler {

        private Object target;

        private ToStringProxyHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("toString")) {
                StringBuilder sb = new StringBuilder();
                for (Field field : target.getClass().getDeclaredFields()) {
                    if (isFieldToStringPositive(field) || (isClassToStringNotNegative(target) && isFieldToStringNotNegative(field))) {
                        field.setAccessible(true);
                        sb.append(field.getName());
                        sb.append("=");
                        sb.append(field.get(target).toString());
                    }
                }
                return sb.toString();
            } else {
                return target.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes()).invoke(target, args);
            }
        }

        private boolean isFieldToStringPositive(Field field) {
            return field.isAnnotationPresent(ToString.class)
                    && field.getAnnotation(ToString.class).value().equals(ToStringValue.YES);
        }

        private boolean isClassToStringNotNegative(Object target) {
            return !target.getClass().isAnnotationPresent(ToString.class)
                    || !target.getClass().getAnnotation(ToString.class).value().equals(ToStringValue.NO);
        }

        private boolean isFieldToStringNotNegative(Field field) {
            return !field.isAnnotationPresent(ToString.class)
                    || !field.getAnnotation(ToString.class).value().equals(ToStringValue.NO);
        }
    }
}
