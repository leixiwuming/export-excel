package com.zxz.common.excel.model;

import java.util.List;

public class MetadataModel {

    private Class targetClass;

    private List<AnnotationMeta> fieldMap;

    public Class getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class targetClass) {
        this.targetClass = targetClass;
    }

    public List<AnnotationMeta> getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(List<AnnotationMeta> fieldMap) {
        this.fieldMap = fieldMap;
    }
}

