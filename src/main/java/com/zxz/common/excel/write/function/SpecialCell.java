package com.zxz.common.excel.write.function;

import com.zxz.common.excel.model.AnnotationMeta;
import org.apache.poi.ss.usermodel.Row;

@FunctionalInterface
public interface SpecialCell<T> {
    int apply(Row row, Integer cellIndex, T nowDto, T nextDto, Object value, AnnotationMeta mapping);
}
