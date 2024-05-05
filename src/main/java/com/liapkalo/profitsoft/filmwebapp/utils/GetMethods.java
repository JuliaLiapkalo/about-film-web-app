package com.liapkalo.profitsoft.filmwebapp.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class GetMethods {

    public static Map<String, Method> getSetterMethods(Class<?> clazz) {
        Map<String, Method> setters = new HashMap<>();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("set") && method.getParameterCount() == 1) {
                String fieldName = method.getName().substring(3);
                setters.put(fieldName.toLowerCase(), method);
            }
        }
        return setters;
    }
}
