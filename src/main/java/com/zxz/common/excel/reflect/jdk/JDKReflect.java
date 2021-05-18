package com.zxz.common.excel.reflect.jdk;

import com.zxz.common.excel.reflect.MethodParameter;
import com.zxz.common.excel.reflect.ReflectStrategy;
import com.zxz.common.exception.BaseException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class JDKReflect implements ReflectStrategy {
    private Map<Class, Map<String, Field>> fieldCache;

    public JDKReflect() {
        fieldCache = new HashMap<>();
    }

    @Override
    public List<Field> getAllField(Class targetClass) {
        List<Field> fields = new ArrayList<>();
        while (!targetClass.equals(Object.class)) {
            fields.addAll(Arrays.asList(targetClass.getDeclaredFields()));
            targetClass = targetClass.getSuperclass();
        }
        return fields;
    }

    @Override
    public <T extends Annotation> T getFieldAnnotation(Class targetClass, Class<T> annotationClass, String filedName) {
        Field filed = getField(targetClass, filedName);
        return filed.getAnnotation(annotationClass);
    }

    @Override
    public <T extends Annotation> List<T> getAnnotations(Class targetClass, Class<T> annotationClass) {
        List<Field> allField = getAllField(targetClass);
        List<T> annotations = new ArrayList<>();
        if (allField == null || allField.isEmpty()) {
            return annotations;
        }
        for (Field field : allField) {
            T annotation = field.getAnnotation(annotationClass);
            if (annotation == null) {
                continue;
            }
            annotations.add(annotation);
        }
        return annotations;
    }

    @Override
    public Object invoke(Object instance, MethodParameter methodParameter) {
        Class<?> aClass = instance.getClass();
        Method method;
        try {
            method = aClass.getMethod(methodParameter.getMethodName(), methodParameter.getMethodParamType());
        } catch (NoSuchMethodException e) {
            throw new BaseException("未找到" + methodParameter.getMethodName() + "方法");
        }
        try {
            return method.invoke(instance, methodParameter.getMethodParamValue());
        } catch (IllegalAccessException e) {
            throw new BaseException("执行方法出错");
        } catch (InvocationTargetException e) {
            throw new BaseException("执行方法出错");
        }
    }

    @Override
    public <T> T newInstance(Class<T> targetClass) {
        try {
            return targetClass.newInstance();
        } catch (Exception e) {
            throw new BaseException("实例化失败");
        }
    }

    @Override
    public Class getFieldType(Class targetClass, String fieldName) {
        return getField(targetClass, fieldName).getType();
    }

    private Field getField(Class targetClass, String filedName) {
        //缓存处理
        Map<String, Field> fieldMap = fieldCache.get(targetClass);
        if (fieldMap == null) {
            fieldMap = new HashMap<>();
            fieldCache.put(targetClass, fieldMap);
        }
        Field field = null;
        //如果缓存存在属性，直接返回
        if ((field = fieldMap.get(filedName)) != null) {
            return field;
        }
        try {
            //反射获取属性
            field = targetClass.getDeclaredField(filedName);
        } catch (NoSuchFieldException e) {
            throw new BaseException("未找到该属性： " + filedName);
        }
        fieldMap.put(filedName, field);
        return field;
    }
}
