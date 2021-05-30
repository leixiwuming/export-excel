package com.zxz.common.excel.model;

/**
 * @author 24447
 */
public class AnnotationMeta {
    /**
     * 属性对应的中文解释
     */
    private String value;

    /**
     *  是否导出
     */
    private boolean status;

    /**
     * 排序号
     */
    private int sort;

    /**
     * 最大宽度
     */
    private int maxwidth;


    /**
     * 最小宽度
     */
    private int minWidth;

    /**
     * 字典列表 导出时会生成下拉条，导入时会根据选择的下拉值、和dictIndex(),dictStep() 转成数值
     */
    private String[] dict;

    /**
     * 字典开始索引
     */
    private int dictIndex;

    /**
     * 字典步长
     */
    private int dictStep;

    /**
     * 是否导出为文本列
     */
    private boolean textColumn;

    /**
     * 导入时该列不能为空
     */
    private boolean notNull;

    /**
     * 属性名称
     */
    private String fieldName;

    /**
     * get方法名称
     */
    private String getMethodName;

    /**
     * set 方法名称
     */
    private String setMethodName;

    /**
     * 默认值
     */
    private String defaultValue;

    private boolean sequence;

    public boolean getSequence() {
        return sequence;
    }

    public void setSequence(boolean sequence) {
        this.sequence = sequence;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean getStatus() {
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

    public boolean getTextColumn() {
        return textColumn;
    }

    public void setTextColumn(boolean textColumn) {
        this.textColumn = textColumn;
    }


    public boolean getNotNull() {
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
