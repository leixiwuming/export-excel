package com.zxz.common.excel.check;

import com.zxz.common.poi.excel.abs.BaseCheck;
import com.zxz.common.poi.excel.usermodel.AnnotationMeta;
import com.zxz.common.poi.excel.util.CellUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;
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
    public boolean check(Row row, List<AnnotationMeta> headClassAnnotation) {
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
            AnnotationMeta mapping = headClassAnnotation.get(i);
            if (mapping == null) {
                return false;
            }
            if (!mapping.getValue().equals(CellUtil.getCellValue(cell))) {
                return false;
            }
        }
        return true;
    }

}
