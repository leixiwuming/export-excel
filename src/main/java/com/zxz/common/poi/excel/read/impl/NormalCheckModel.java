package com.zxz.common.poi.excel.read.impl;

import com.zxz.common.poi.excel.abs.BaseCheck;
import com.zxz.common.poi.excel.annotation.Mapping;
import com.zxz.common.poi.excel.usermodel.AnnotationMeta;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.util.Map;
import java.util.stream.Collectors;

public class NormalCheckModel implements BaseCheck {
    /**
     * 检查excel是不是模板
     *
     * @param row                 头部行
     * @param headClassAnnotation
     * @return 是否通过
     */
    @Override
    public boolean check(Row row, Map<Integer, AnnotationMeta> headClassAnnotation) {
        if (row == null) {
            return false;
        }
        int lastCellNum = row.getLastCellNum();
        if (lastCellNum == 0) {
            return false;
        }
        if (headClassAnnotation.size() > lastCellNum) {
            return false;
        }
        Map<String, AnnotationMeta> collect = headClassAnnotation.values().
                stream().collect(Collectors.toMap(AnnotationMeta::getValue, s -> s));
        headClassAnnotation.clear();

        int errorCellNum = 0;
        for (int i = 0; i < lastCellNum; i++) {
            Cell cell = row.getCell(i);
            if (checkType(cell) || !cell.getCellType().equals(CellType.STRING)) {
                errorCellNum++;
                continue;
            }
            String stringCellValue = cell.getStringCellValue();
            AnnotationMeta mapping = collect.get(stringCellValue);
            if (mapping != null) {
                headClassAnnotation.put(i,mapping);
            }
        }
        if (headClassAnnotation.size() > lastCellNum - errorCellNum) {
            return false;
        }
        return true;
    }
}
