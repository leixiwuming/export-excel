package com.zxz.common.excel.reflect;

import com.zxz.common.excel.ExcelConfig;
import com.zxz.common.excel.annotation.adapter.AnnotationAdapter;
import com.zxz.common.excel.model.AnnotationMeta;
import com.zxz.common.exception.BaseException;

import java.lang.annotation.Annotation;
import java.util.List;

public interface ReflectStrategy {
    /**
     * 获取所有属性
     *
     * @param targetClass
     * @return
     */
    List getAllField(Class targetClass);

    /**
     * 获取映射关系
     *
     * @param targetClass
     * @return
     */
    List<AnnotationMeta> getAnnotationMetas(Class targetClass);

    /**
     *
     */
    <T extends Annotation>T getClassAnnotation(Class targetClass, Class<T> annotationClass);

    /**
     * 调用方法
     *
     * @param instance
     * @param methodParameter
     * @return
     */
    Object invoke(Object instance, MethodParameter methodParameter);

    /**
     * 实例化
     *
     * @param targetClass
     * @param <T>
     * @return
     */
    <T> T newInstance(Class<T> targetClass);

    /**
     * 获取属性类型
     *
     * @param targetClass
     * @param fieldName
     * @return
     */
    Class getFieldType(Class targetClass, String fieldName);

    /**
     * 获取目标类超类接口的泛型
     *
     * @param targetClass
     * @return
     */
    List<Class> getInterfaceGeneric(Class targetClass);


    /**
     * 获取目标类父类的泛型
     *
     * @param targetClass
     * @return
     */
    List<Class> getSuperclassGeneric(Class targetClass);

    default AnnotationAdapter getAnnotationAdapter(Class<? extends Annotation> annotationClass) {
        for (AnnotationAdapter annotationAdapter : ExcelConfig.annotationAdapters) {
            if (annotationAdapter.supports(annotationClass)) {
                return annotationAdapter;
            }
        }
        throw new BaseException("未找到处理该注解的处理器");
    }
}
