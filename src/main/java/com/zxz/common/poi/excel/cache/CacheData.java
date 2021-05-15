package com.zxz.common.poi.excel.cache;

import com.zxz.common.poi.excel.usermodel.AnnotationMeta;
import com.zxz.common.poi.excel.usermodel.MetadataModel;
import com.zxz.common.poi.excel.util.AnnotationUtil;
import com.zxz.common.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheData {
    public static Map<String, List<AnnotationMeta>> classMap = new HashMap<>();

    public static List<AnnotationMeta> getOrGenClassMap(Class headClass) {
        String typeName = headClass.getTypeName();
        List<AnnotationMeta> annotationMetas = classMap.get(typeName);
        if (annotationMetas == null || annotationMetas.isEmpty()) {
            annotationMetas = AnnotationUtil.getHeadClassAnnotation(headClass);
        }
        return new ArrayList<>(annotationMetas);

    }


    public static List<AnnotationMeta> getOrGenClassMap(MetadataModel metadataModel) {
        List<AnnotationMeta> annotationMetas = metadataModel.getFieldMap();
        Assert.notEmpty(annotationMetas,"配置错误");
        return new ArrayList<>(annotationMetas);

    }


}
