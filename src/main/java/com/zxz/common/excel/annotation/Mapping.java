package com.zxz.common.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 24447
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
/**
 * {@link com.zxz.common.excel.model.AnnotationMeta}
 */
public @interface Mapping {
    /**
     * 属性对应的中文解释
     */
    String value() default "";

    /**
     * 是否导出
     */
    boolean status() default true;

    /**
     * 排序号
     */
    int sort() default 0;

    /**
     * 最大宽度
     */
    int maxwidth() default 0;

    /**
     * 最小宽度
     */
    int minWidth() default 0;

    /**
     * 字典列表 导出时会生成下拉条，导入时会根据选择的下拉值、和dictIndex(),dictStep() 转成数值
     */
    String[] dict() default {};

    /**
     * 字典开始索引
     */
    int dictIndex() default 0;

    /**
     * 字典步长
     */
    int dictStep() default 0;

    /**
     * 是否导出为文本列
     */
    boolean textColumn() default false;

    /**
     * 导入时该列不能为空
     */
    boolean notNull() default false;

    /**
     * 属性名称
     */
    String fieldName() default "";

    /**
     * get方法名称
     */
    String getMethodName() default "";

    /**
     * set 方法名称
     */
    String setMethodName() default "";

    /**
     * 默认值
     */
    String defaultValue() default "";

    boolean isSequence() default false;
}
