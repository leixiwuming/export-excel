package com.zxz.common.poi.excel.util;

import com.zxz.common.poi.excel.DefaultCreateExcel;
import com.zxz.common.poi.excel.DefaultReadExcel;
import com.zxz.common.poi.excel.Increasing;
import com.zxz.common.poi.excel.read.ReadResult;
import com.zxz.common.poi.excel.read.enums.CheckModel;
import com.zxz.common.util.Assert;
import com.zxz.common.util.CollectionUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

    public static <T> Workbook createExcel(Class<T> headClass, List<T> dtos, String sheetName) {
        DefaultCreateExcel<T> build = new DefaultCreateExcel.Builder().useSequence(true).sequence(new Increasing()).build();
        return build.createExcel(headClass, dtos, sheetName);
    }

    public static <T> Workbook createExcel(Map<String, List<T>> sheetDtos, Class<T> headClass) {
        DefaultCreateExcel<T> build = new DefaultCreateExcel.Builder().useSequence(true).sequence(new Increasing()).build();
        Workbook workBook = build.createWorkBook();
        if (sheetDtos == null || sheetDtos.size() == 0) {
            Sheet sheet = build.createSheet(workBook, "");
            build.createHead(sheet, headClass, false);
            return workBook;
        }
        sheetDtos.forEach((k, v) -> {
            Sheet sheet = build.createSheet(workBook, k);
            int rowIndex = build.createHead(sheet, headClass, false);
            build.createData(sheet, rowIndex, v);
        });
        return workBook;
    }

    public static <T> Workbook createTemplate(Class<T> headClass, String sheetName) {
        DefaultCreateExcel<T> build = new DefaultCreateExcel.Builder().useSequence(true).build();
        Workbook workBook = build.createWorkBook();
        Sheet sheet = build.createSheet(workBook, sheetName);
        build.createHead(sheet, headClass, true);
        return workBook;
    }

    public static <T> ReadResult<T> readExcel(Class<T> dto, InputStream inputStream) {
        DefaultReadExcel build = new DefaultReadExcel.Builder().checkModel(CheckModel.NORMAL).build();
        return build.readWorkBook(inputStream, dto);
    }


}
