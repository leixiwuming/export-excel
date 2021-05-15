package com.zxz.common.poi.excel.function.abs;

import com.zxz.common.poi.excel.usermodel.AnnotationMeta;
import org.apache.poi.ss.usermodel.Row;

@FunctionalInterface
public interface SpecialCell<T> {
    int apply(Row row, Integer cellIndex, T nowDto, T nextDto, Object value, AnnotationMeta mapping);
}
