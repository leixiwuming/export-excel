package com.zxz.common.poi.excel.abs;

import com.zxz.common.poi.excel.usermodel.AnnotationMeta;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

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
    boolean check(Row row, Map<Integer, AnnotationMeta> headClassAnnotation);

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
