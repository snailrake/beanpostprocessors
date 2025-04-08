package com.example.beanpostprocessors.proxy;

import com.example.beanpostprocessors.annotation.ToString;
import com.example.beanpostprocessors.enums.ToStringValue;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ToStringProxy {

    public static <T> T create(T t) {
        return (T) Enhancer.create(
                t.getClass(),
                new ToStringProxyHandler(t));
    }

    private static class ToStringProxyHandler implements MethodInterceptor {

        private Object target;

        private ToStringProxyHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            if (method.getName().equals("toString")) {
                StringBuilder sb = new StringBuilder();
                for (Field field : target.getClass().getDeclaredFields()) {
                    if (isFieldToStringPositive(field) || isClassToStringNotNegative(target)) {
                        field.setAccessible(true);
                        sb.append(field.getName());
                        sb.append("=");
                        sb.append(field.get(target).toString());
                    }
                }
                return sb.toString();
            } else {
                return proxy.invokeSuper(obj, args);
            }
        }

        private boolean isFieldToStringPositive(Field field) {
            return field.isAnnotationPresent(ToString.class)
                    && field.getAnnotation(ToString.class).value().equals(ToStringValue.YES);
        }

        private boolean isClassToStringNotNegative(Object target) {
            return !target.getClass().isAnnotationPresent(ToString.class) || !target.getClass().getAnnotation(ToString.class).value().equals(ToStringValue.NO);
        }
    }
}
