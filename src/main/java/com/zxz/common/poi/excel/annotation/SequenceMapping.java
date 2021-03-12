package com.zxz.common.poi.excel.annotation;

import java.lang.annotation.Annotation;

/**
 * @author 24447
 */
public class SequenceMapping implements Mapping {
    @Override
    public String value() {
        return "序号";
    }

    @Override
    public boolean status() {
        return true;
    }

    @Override
    public int sort() {
        return -1;
    }

    @Override
    public int maxwidth() {
        return 0;
    }

    @Override
    public int minWidth() {
        return 0;
    }

    @Override
    public String[] dict() {
        return new String[0];
    }

    @Override
    public int dictIndex() {
        return 0;
    }

    @Override
    public int dictStep() {
        return 0;
    }

    @Override
    public boolean textColumn() {
        return false;
    }

    @Override
    public Class type() {
        return String.class;
    }

    @Override
    public boolean notNull() {
        return false;
    }

    @Override
    public String fieldName() {
        return null;
    }

    @Override
    public String getMethodName() {
        return "";
    }

    @Override
    public String setMethodName() {
        return null;
    }

    @Override
    public String defaultValue() {
        return null;
    }

    @Override
    public boolean isSequence() {
        return true;
    }


    /**
     * Returns the annotation type of this annotation.
     *
     * @return the annotation type of this annotation
     */
    @Override
    public Class<? extends Annotation> annotationType() {
        return Mapping.class;
    }
}
