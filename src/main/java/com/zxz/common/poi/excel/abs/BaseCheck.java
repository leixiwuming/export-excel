package com.zxz.common.poi.excel.abs;

import com.zxz.common.poi.excel.annotation.Mapping;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;
import java.util.Map;

/**
 * @author 24447
 */
public interface BaseCheck {
    /**
     * 检查excel是不是模板
     *
     * @param row 头部行
     * @return 是否通过
     */
    boolean check(Row row, Map<Integer, Mapping> headClassAnnotation);

    default boolean checkType(Cell cell) {
        if (cell == null) {
            return true;
        }
        CellType cellType = cell.getCellType();
        if (cellType == CellType._NONE || cellType == CellType.BLANK || cellType == CellType.ERROR) {
            return true;
        }
        return false;
    }
}
