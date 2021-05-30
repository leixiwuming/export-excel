package com.zxz.common.excel.write.function;

import org.apache.poi.ss.usermodel.Sheet;

@FunctionalInterface
public interface SpecialRow<T> {
    int apply(Sheet sheet, Integer rowIndex, T nowData, T nextData);
}
