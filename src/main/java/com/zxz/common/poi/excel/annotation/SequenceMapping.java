package com.zxz.common.poi.excel.annotation;

import com.zxz.common.poi.excel.usermodel.AnnotationMeta;

/**
 * @author 24447
 */
public class SequenceMapping extends AnnotationMeta {
    @Override
    public boolean isSequence() {
        return true;
    }

    @Override
    public String getValue() {
        return "序号";
    }
}
