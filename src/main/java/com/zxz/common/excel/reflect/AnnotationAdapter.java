package com.zxz.common.excel.reflect;

import java.util.List;

public interface AnnotationAdapter {
    boolean supports(Class targetClass);

    /**
     * 获取目标类上支持的注解对应的映射关系
     * @param targetClass
     * @return
     */
    List getHeadAnnotation(Class targetClass);
}
