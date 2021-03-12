package com.zxz.common.poi.excel.read.impl;

import com.zxz.common.poi.excel.abs.BaseCheck;
import com.zxz.common.poi.excel.annotation.Mapping;
import com.zxz.common.poi.excel.util.CellUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.util.Map;

/**
 * @author 24447
 */
public class StrictCheckModel implements BaseCheck {
    /**
     * 检查excel是不是模板
     * 属性的序号和个数必须一致
     *
     * @param row 头部行
     * @return 是否通过
     */
    @Override
    public boolean check(Row row, Map<Integer, Mapping> headClassAnnotation) {
        int lastCellNum = row.getLastCellNum();
        if (lastCellNum == 0) {
            return false;
        }

        if (headClassAnnotation.size() != lastCellNum ) {
            return false;
        }
        for (int i = 0; i < lastCellNum; i++) {
            Cell cell = row.getCell(i);
            if (checkType(cell)) {
                return false;
            }
            Mapping mapping = headClassAnnotation.get(i);
            if (mapping == null) {
                return false;
            }
            if (!mapping.value().equals(CellUtil.getCellValue(cell))) {
                return false;
            }
        }
        return true;
    }

}
