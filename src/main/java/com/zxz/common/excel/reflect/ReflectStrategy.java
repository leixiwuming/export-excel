package com.zxz.common.excel.reflect;

import java.lang.annotation.Annotation;
import java.util.List;

public interface ReflectStrategy {
    List getAllField(Class targetClass);

    <T extends Annotation> T getFieldAnnotation(Class targetClass, Class<T> annotationClass, String filedName);

    <T extends Annotation> List<T> getAnnotations(Class targetClass, Class<T> annotationClass);

    Object invoke(Object instance, MethodParameter methodParameter);

    <T> T newInstance(Class<T> targetClass);

    Class getFieldType(Class targetClass, String fieldName);
}
