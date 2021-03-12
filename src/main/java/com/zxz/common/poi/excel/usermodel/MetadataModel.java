package com.zxz.common.poi.excel.usermodel;

import java.util.List;

public class MetadataModel {
    /**
     * 类全路径
     */
    private String classFullPath;

    private List<AnnotationMeta> fieldMap;

    public String getClassFullPath() {
        return classFullPath;
    }

    public void setClassFullPath(String classFullPath) {
        this.classFullPath = classFullPath;
    }

    public List<AnnotationMeta> getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(List<AnnotationMeta> fieldMap) {
        this.fieldMap = fieldMap;
    }
}

