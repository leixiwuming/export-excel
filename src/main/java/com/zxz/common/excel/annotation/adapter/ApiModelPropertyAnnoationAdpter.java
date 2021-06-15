package com.zxz.common.excel.annotation.adapter;

import com.zxz.common.excel.model.AnnotationMeta;
import com.zxz.common.excel.util.StringUtils;
import io.swagger.annotations.ApiModelProperty;

public class ApiModelPropertyAnnoationAdpter implements AnnotationAdapter<ApiModelProperty> {
    @Override
    public boolean supports(Object annotationClass) {
        return annotationClass != null && annotationClass.equals(ApiModelProperty.class);
    }

    @Override
    public AnnotationMeta getHeadAnnotation(String filedName, ApiModelProperty apiModelProperty) {
        AnnotationMeta annotationMeta = new AnnotationMeta();
        String firstToUp = StringUtils.firstToUp(filedName);
        annotationMeta.setValue(apiModelProperty.value());
        annotationMeta.setStatus(!apiModelProperty.hidden());
        annotationMeta.setFieldName(filedName);
        annotationMeta.setGetMethodName(GET + firstToUp);
        annotationMeta.setSetMethodName(SET + firstToUp);
        annotationMeta.setSequence(false);
        return annotationMeta;
    }
}
