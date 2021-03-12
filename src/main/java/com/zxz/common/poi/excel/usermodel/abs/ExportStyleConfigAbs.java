package com.zxz.common.poi.excel.usermodel.abs;


import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.function.Function;

public interface ExportStyleConfigAbs {
    CellStyle createHeadStyle(Workbook workbook);

    CellStyle createDataStyle(Workbook workbook);

    CellStyle createtTextStyle(Workbook workbook);

    CellStyle createNtNullCellHeadStyle(Workbook workbook);


    Function<Integer, Integer> excelCalculationWidth();
}
