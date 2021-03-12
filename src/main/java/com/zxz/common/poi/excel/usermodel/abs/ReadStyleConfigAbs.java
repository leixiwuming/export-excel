package com.zxz.common.poi.excel.usermodel.abs;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.function.Function;

public interface ReadStyleConfigAbs {
    CellStyle createErrorHeadStyle(Workbook workbook);

    CellStyle createErrorDataStyle(Workbook workbook);

    Function<Integer, Integer> excelCalculationWidth();
}
