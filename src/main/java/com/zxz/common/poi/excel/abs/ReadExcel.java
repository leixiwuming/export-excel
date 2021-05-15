package com.zxz.common.poi.excel.abs;

import com.zxz.common.poi.excel.read.ReadResult;
import com.zxz.common.poi.excel.usermodel.AnnotationMeta;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.InputStream;
import java.util.List;

public interface ReadExcel {

    <T> ReadResult readWorkBook(InputStream excelInputSteam, Class<T> dto);

    <T> void readHead(Row row, Class<T> dto, int sheetIndex);

    <T> List<T> readData(Sheet sheet, Class<T> dto);

    <T> T readDateRow(Row row, Class<T> dto);

    <T> List<String> checkDto(T t);

    List<AnnotationMeta> getConditionCheckMapping();
}
