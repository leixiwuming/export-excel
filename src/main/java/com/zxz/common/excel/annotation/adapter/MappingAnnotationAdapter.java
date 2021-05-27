package com.zxz.common.excel.annotation.adapter;

import com.zxz.common.excel.annotation.Mapping;
import com.zxz.common.excel.model.AnnotationMeta;
import com.zxz.common.excel.util.StringUtils;

public class MappingAnnotationAdapter implements AnnotationAdapter<Mapping> {
    @Override
    public boolean supports(Object annotationClass) {
        return annotationClass instanceof Mapping;
    }

    @Override
    public AnnotationMeta getHeadAnnotation(String filedName, Mapping annotation) {
        AnnotationMeta annotationMeta = new AnnotationMeta();
        String firstToUp = StringUtils.firstToUp(filedName);
        annotationMeta.setValue(annotation.value());
        annotationMeta.setStatus(annotation.status());
        annotationMeta.setSort(annotation.sort());
        annotationMeta.setMaxwidth(annotation.maxwidth());
        annotationMeta.setMinWidth(annotation.minWidth());
        annotationMeta.setDict(annotation.dict());
        annotationMeta.setDictIndex(annotation.dictIndex());
        annotationMeta.setDictStep(annotation.dictStep());
        annotationMeta.setDefaultValue(annotation.defaultValue());
        annotationMeta.setTextColumn(annotation.textColumn());
        annotationMeta.setNotNull(annotation.notNull());
        annotationMeta.setFieldName(filedName);
        annotationMeta.setGetMethodName(GET + firstToUp);
        annotationMeta.setSetMethodName(SET + firstToUp);
        annotationMeta.setSequence(false);
        return annotationMeta;
    }
}
