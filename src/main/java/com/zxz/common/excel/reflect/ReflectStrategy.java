package com.zxz.common.excel.reflect;

import com.zxz.common.excel.model.AnnotationMeta;

import java.lang.annotation.Annotation;
import java.util.List;

public interface ReflectStrategy {
    List getAllField(Class targetClass);

    List<AnnotationMeta> getAnnotations(Class targetClass);

    Object invoke(Object instance, MethodParameter methodParameter);

    <T> T newInstance(Class<T> targetClass);

    Class getFieldType(Class targetClass, String fieldName);
}
