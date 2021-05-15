package com.zxz.common.poi.excel;

import com.zxz.common.poi.excel.cache.CacheData;
import com.zxz.common.poi.excel.usermodel.MetadataModel;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public class MetadataCreateExcel extends DefaultCreateExcel {
    MetadataCreateExcel(Builder builder) {
        super(builder);
    }

    public Workbook createExcel(MetadataModel metadataModel, List dtos, String sheetName) {
        Workbook workBook = createWorkBook();
        Sheet sheet = createSheet(workBook, sheetName);
        int rowIndex = createHead(sheet, metadataModel, false);
        if (dtos != null && !dtos.isEmpty()) {
            createData(sheet, rowIndex, dtos);
        }
        setColumnWidth(sheet);
        return workBook;
    }

    public int createHead(Sheet sheet, MetadataModel metadataModel, boolean isTemplate) {
        int rowIndex = 0;
        if (isNull(getClassAnnotation())) {
            setClassAnnotation(CacheData.getOrGenClassMap(metadataModel));
        }
        rowIndex = setSpecialRow(sheet, rowIndex, null, null);
        try {
            rowIndex = setHead(sheet, rowIndex, isTemplate, Class.forName(metadataModel.getClassFullPath()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (isTemplate) {
            setColumnWidth(sheet);
        }
        return rowIndex;
    }
}
