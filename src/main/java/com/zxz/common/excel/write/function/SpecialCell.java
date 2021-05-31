package com.zxz.common.excel.write.function;

import com.zxz.common.excel.model.AnnotationMeta;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

@FunctionalInterface
public interface SpecialCell<T> {
    /**
     *  * 当你使用这个类时，免不了要添加样式，千万不要尝试在在此方法里重复创建CellStyle
     *      * 听说excel 对cellStyle数量有限制。可能会导致样式失效（我也没测过，有知道的大佬可以纠正~）
     * @param row
     * @param cellIndex
     * @param nowDto
     * @param nextDto
     * @param value
     * @param mapping
     * @param defaultCellStyle
     * @return
     */
    int apply(Row row, Integer cellIndex, T nowDto, T nextDto, Object value, AnnotationMeta mapping, CellStyle defaultCellStyle);
}
