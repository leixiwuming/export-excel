package com.zxz.common.excel.annotation.adapter;

import com.zxz.common.excel.model.AnnotationMeta;

import java.lang.annotation.Annotation;
import java.util.List;

public interface AnnotationAdapter<T extends Annotation> {
    String GET="get";
    String SET="set";
    boolean supports(Object o);

    /**
     * 把注解转换成AnnotationMeta
     * @param filedName 属性名
     * @param t 注解对象
     * @return
     */
     AnnotationMeta getHeadAnnotation(String filedName,T t);
}
