package com.zxz.common.poi.excel.function.abs;

import org.apache.poi.ss.usermodel.Sheet;

@FunctionalInterface
public interface SpecialRow<T> {
    int apply(Sheet sheet, Integer rowIndex, T nowData, T nextData);
}
