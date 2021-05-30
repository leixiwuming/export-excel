package com.zxz.common.excel.write.conf;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class CellStyleConfig {
    /**
     * 默认头部样式
     *
     * @param workbook
     * @return 单元格样式
     */
   public abstract CellStyle getHeadStyle(Workbook workbook);

    /**
     * 默认数据行样式
     *
     * @param workbook
     * @return 单元格样式
     */
    public abstract CellStyle getDataStyle(Workbook workbook);

    /**
     * 文本列样式
     *
     * @param workbook
     * @return 单元格样式
     */
    public abstract CellStyle getTextStyle(Workbook workbook);

    /**
     * 不能为null的列头部样式
     *
     * @param workbook
     * @return 单元格样式
     */
    public abstract CellStyle getNotNullCellHeadStyle(Workbook workbook);
}
