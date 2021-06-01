package com.zxz.common.excel;

import com.zxz.common.excel.annotation.adapter.AnnotationAdapter;
import com.zxz.common.excel.annotation.adapter.MappingAnnotationAdapter;
import com.zxz.common.excel.cache.CaffeineCache;
import com.zxz.common.excel.cache.ComCache;

import java.util.ArrayList;
import java.util.List;

public class ExcelConfig {
    public static final String HEAD_TAG = "#_HEAD";
    public static final String EXAMPLE_TAG = "#_EXAMPLE";
    public static final List<AnnotationAdapter> annotationAdapters = new ArrayList<AnnotationAdapter>() {{
        add(new MappingAnnotationAdapter());
    }};
    public static ComCache comCache = CaffeineCache.getInstance();

}
