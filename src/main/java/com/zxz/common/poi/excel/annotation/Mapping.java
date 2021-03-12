package com.zxz.common.poi.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 24447
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mapping {
    String value() default "";

    boolean status() default true;

    int sort() default 0;


    int maxwidth() default 0;

    int minWidth() default 0;

    String[] dict() default {};

    int dictIndex() default 0;

    int dictStep() default 0;

    boolean textColumn() default false;

    Class type() default String.class;

    boolean notNull() default false;

    String fieldName() default "";

    String getMethodName() default "";

    String setMethodName() default "";

    String defaultValue() default "";

    boolean isSequence() default false;
}
