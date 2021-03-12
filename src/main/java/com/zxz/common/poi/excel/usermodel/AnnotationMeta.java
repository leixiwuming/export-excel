package com.zxz.common.poi.excel.usermodel;

/**
 * @author 24447
 */
public class AnnotationMeta {
    private String value;

    private boolean status;

    private int sort;

    private int maxwidth;

    private int minWidth;

    private String[] dict;

    private int dictIndex;

    private int dictStep;

    private boolean textColumn;

    private Class type;

    private boolean notNull;

    private String fieldName;

    private String getMethodName;

    private String setMethodName;

    private String defaultValue;

    private boolean isSequence;

    public boolean isSequence() {
        return isSequence;
    }


    public void setSequence(boolean sequence) {
        isSequence = sequence;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getMaxwidth() {
        return maxwidth;
    }

    public void setMaxwidth(int maxwidth) {
        this.maxwidth = maxwidth;
    }

    public int getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }

    public String[] getDict() {
        return dict;
    }

    public void setDict(String[] dict) {
        this.dict = dict;
    }

    public int getDictIndex() {
        return dictIndex;
    }

    public void setDictIndex(int dictIndex) {
        this.dictIndex = dictIndex;
    }

    public int getDictStep() {
        return dictStep;
    }

    public void setDictStep(int dictStep) {
        this.dictStep = dictStep;
    }

    public boolean isTextColumn() {
        return textColumn;
    }

    public void setTextColumn(boolean textColumn) {
        this.textColumn = textColumn;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getGetMethodName() {
        return getMethodName;
    }

    public void setGetMethodName(String getMethodName) {
        this.getMethodName = getMethodName;
    }

    public String getSetMethodName() {
        return setMethodName;
    }

    public void setSetMethodName(String setMethodName) {
        this.setMethodName = setMethodName;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
