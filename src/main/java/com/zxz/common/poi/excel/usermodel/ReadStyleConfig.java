package com.zxz.common.poi.excel.usermodel;

import com.zxz.common.poi.excel.usermodel.abs.ExportStyleConfigAbs;
import com.zxz.common.poi.excel.usermodel.abs.ReadStyleConfigAbs;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.function.Function;

public class ReadStyleConfig implements ReadStyleConfigAbs {
    private ExportStyleConfigAbs config = new ExportStyleConfig();

    @Override
    public CellStyle createErrorHeadStyle(Workbook workbook) {
        return config.createNtNullCellHeadStyle(workbook);
    }

    @Override
    public CellStyle createErrorDataStyle(Workbook workbook) {
        CellStyle cellStyle = config.createDataStyle(workbook);
        Font font = workbook.createFont();
        font.setColor(Font.COLOR_RED);
        cellStyle.setFont(font);
        return cellStyle;
    }

    @Override
    public Function<Integer, Integer> excelCalculationWidth() {
        return width -> width * 256 + 196;
    }


}
