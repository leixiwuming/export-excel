package com.zxz.common.excel.reflect;

import com.zxz.common.excel.model.AnnotationMeta;

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
     * @param tagertClass
     * @return
     */
    List<Class> getInterfaceGeneric(Class tagertClass);


    /**
     * 获取目标类父类的泛型
     *
     * @param tagertClass
     * @return
     */
    List<Class> getSuperclassGeneric(Class tagertClass);

}
