package com.zxz.common.poi.excel.abs;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

/**
 * @author 24447
 */
public interface CreateExcel<T> {
     Workbook createExcel(Class<T> headClass, List<T> dtos, String sheetName);

    Sheet createSheet(Workbook workBook, String sheetName);

    Workbook createWorkBook();

    int createHead(Sheet sheet, Class<T> headClass,boolean isTemplate);


    int createData(Sheet sheet, int rowIndex, List<T> dtos);
}
