package com.zxz.common.excel.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationType {
    /**
     * 使用哪个注解
     * @return
     */
     Class value() default Mapping.class;
}
