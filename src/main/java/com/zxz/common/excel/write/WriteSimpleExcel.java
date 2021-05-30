package com.zxz.common.excel.write;

import com.zxz.common.excel.ExcelConfig;
import com.zxz.common.excel.model.AnnotationMeta;
import com.zxz.common.excel.model.Example;
import com.zxz.common.excel.reflect.ReflectStrategy;
import com.zxz.common.excel.write.conf.CellStyleConfig;
import com.zxz.common.excel.write.conf.WriteExcelConfig;
import com.zxz.common.excel.write.function.SpecialRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class WriteSimpleExcel<T> extends DefaultWriteData<T> {

    @Override
    protected int writeTemplate(Sheet sheet, int startRowIndex, Class<T> entity) {
        startRowIndex = writeHead(sheet, startRowIndex, entity);
        if (Example.class.isAssignableFrom(entity)) {
            ReflectStrategy reflectStrategy = WriteExcelConfig.getReflectStrategy();
            T t = reflectStrategy.newInstance(entity);
            startRowIndex = writeData(sheet, startRowIndex, getMaping(entity, false), Arrays.asList((T) ((Example) t).getExamples()), null);
        }
        Row row = sheet.getRow(startRowIndex - 1);
        Cell cell = row.createCell(row.getLastCellNum());
        setCellVal("示例数据", cell);
        return startRowIndex;
    }

    @Override
    protected int writeHead(Sheet sheet, int startRowIndex, Class<T> entity) {
        Map<Integer, AnnotationMeta> metaMap = getMaping(entity, false);
        //特殊行
        List<SpecialRow> specialRows = WriteExcelConfig.getSpecialRowThreadLocal();
        //配置
        CellStyleConfig cellStyleConfig = WriteExcelConfig.getCellStyleConfigThreadLocal();
        //默认样式
        CellStyle headStyle = cellStyleConfig.getHeadStyle(sheet.getWorkbook());
        CellStyle notNullCellHeadStyle = cellStyleConfig.getNotNullCellHeadStyle(sheet.getWorkbook());
        //处理特殊行
        startRowIndex = dealSpeciaRow(specialRows, sheet, startRowIndex, null, null);
        //创建行
        Row row = sheet.createRow(startRowIndex);
        //设置第一列为头标记
        setCellVal(ExcelConfig.HEAD_TAG, row.createCell(0));
        metaMap.forEach((cellIndex, meta) -> {
            Cell cell = row.createCell(cellIndex);
            setCellVal(meta.getValue(), cell);
            if (meta.getNotNull()) {
                cell.setCellStyle(notNullCellHeadStyle);
            } else {
                cell.setCellStyle(headStyle);
            }
        });
        sheet.setColumnHidden(0, true);
        return ++startRowIndex;
    }
}
