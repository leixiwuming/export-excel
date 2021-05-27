package com.zxz.common.excel.read;

import com.zxz.common.excel.ExcelConfig;
import com.zxz.common.excel.check.BaseCheck;
import com.zxz.common.excel.model.AnnotationMeta;
import com.zxz.common.excel.util.CellUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleReadExcelHead<T> extends DefaultReadExelData<T> {
    private BaseCheck baseCheck;
    private Map<Class, Map<Integer, AnnotationMeta>> classAnnotationMetaCache;

    public SimpleReadExcelHead() {
        classAnnotationMetaCache = new HashMap<>();
    }

    public void  吧(BaseCheck baseCheck) {
        this.baseCheck = baseCheck;
    }

    /**
     * 读取第一个head行
     *
     * @param sheet
     * @param targetClass
     * @return
     */
    @Override
    protected Map<Integer, AnnotationMeta> readHead(Sheet sheet, Class<T> targetClass) {
        Map<Integer, AnnotationMeta> mapping = classAnnotationMetaCache.get(targetClass);
        if (mapping != null) {
            return mapping;
        }
        //获取最后一行的下标
        int lastRowNum = sheet.getLastRowNum();
        //遍历行
        for (int i = 0; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            //读取第一列标记列的值
            Object cellValue = CellUtil.getCellValue(row.getCell(0));
            if (ExcelConfig.HEAD_TAG.equals(cellValue)) {
                mapping = readHeadRow(row, targetClass);
                break;
            }
        }
        classAnnotationMetaCache.put(targetClass, mapping);
        return mapping;

    }

    /**
     * 读取行转换成每一列对应的AnnotationMeta注解信息
     * @param row
     * @param targetClass
     * @return
     */
    private Map<Integer, AnnotationMeta> readHeadRow(Row row, Class<T> targetClass) {
        Map<Integer, AnnotationMeta> res = new HashMap<>();
        List<AnnotationMeta> annotations = reflectStrategy.getAnnotations(targetClass);
        short lastCellNum = row.getLastCellNum();
        for (int i = 1; i < lastCellNum; i++) {
            Object cellValue = CellUtil.getCellValue(row.getCell(i));
            if (cellValue == null) {
                continue;
            }
            for (AnnotationMeta annotation : annotations) {
                if (cellValue.equals(annotation.getValue())) {
                    res.put(i, annotation);
                }
            }
        }
        return res;
    }

}
