package com.zxz.common.excel.write;

import com.zxz.common.excel.util.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public abstract class WriteExcel<T> {
    //写入头部
    protected abstract int writeHead(Sheet sheet, int startRowIndex);

    //写入数据
    protected abstract void writeData(Sheet sheet, int startRowIndex);

    public Workbook write(String sheetName, List<T> entitys, Class<T> entityClass) {
        sheetName = StringUtils.isNullReplace(sheetName, WriteExcelConfig.DEFAULT_SHEET_NAME);
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);
        writeData(sheet, writeHead(sheet, 0));
        return workbook;
    }


}
