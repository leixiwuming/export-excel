package com.zxz.common.poi.excel.function.abs;

import com.zxz.common.poi.excel.annotation.Mapping;
import org.apache.poi.ss.usermodel.Row;

@FunctionalInterface
public interface SpecialCell<T> {
    int apply(Row row, Integer cellIndex, T nowDto, T nextDto, Object value, Mapping mapping);
}
