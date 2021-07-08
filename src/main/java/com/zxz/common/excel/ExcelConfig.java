package com.zxz.common.excel;

import com.zxz.common.excel.annotation.adapter.AnnotationAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class ExcelConfig {
    public static final String HEAD_TAG = "#_HEAD";
    public static final String EXAMPLE_TAG = "#_EXAMPLE";
    public static final List<AnnotationAdapter> annotationAdapters = new ArrayList();

    static {
        ServiceLoader<AnnotationAdapter> loader = ServiceLoader.load(AnnotationAdapter.class);
        for (AnnotationAdapter annotationAdapter : loader) {
            annotationAdapters.add(annotationAdapter);
        }
    }

}
