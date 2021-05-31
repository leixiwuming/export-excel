package com.zxz.common.excel.write.conf;

import org.apache.poi.ss.usermodel.*;

public class DefaultCellStyleConfig extends CellStyleConfig {

    public DefaultCellStyleConfig() {
        this.borderStyle = BorderStyle.THIN;
        this.fillType = FillPatternType.SOLID_FOREGROUND;
        this.horizontalalignment = HorizontalAlignment.CENTER;
        this.verticalalignment = VerticalAlignment.CENTER;
    }

    public DefaultCellStyleConfig(short colorIndex) {
        this();
        this.colorIndex = colorIndex;

    }

    /**
     * 默认头部颜色 line
     */
    private short colorIndex = 50;
    /**
     * 默认边框设置
     */
    private BorderStyle borderStyle;
    /**
     * 默认填充类型
     */
    private FillPatternType fillType;
    /**
     * 默认居中
     */
    private HorizontalAlignment horizontalalignment;
    private VerticalAlignment verticalalignment;

    private CellStyle headStyle;
    private CellStyle dataStyle;
    private CellStyle textStyle;
    private CellStyle notNullCellHeadStyle;


    /**
     * 默认头部样式
     *
     * @param workbook
     * @return 单元格样式
     */
    @Override
    public CellStyle getHeadStyle(Workbook workbook) {
        if (headStyle != null) {
            return headStyle;
        }
        headStyle = workbook.createCellStyle();
        headStyle.setFillForegroundColor(colorIndex);
        headStyle.setFillPattern(fillType);
        setDefaultBorder(headStyle);
        headStyle.setAlignment(horizontalalignment);
        headStyle.setVerticalAlignment(verticalalignment);
        headStyle.setWrapText(true);
        return headStyle;
    }

    /**
     * 默认数据行样式
     *
     * @param workbook
     * @return 单元格样式
     */
    @Override
    public CellStyle getDataStyle(Workbook workbook) {
        if (dataStyle != null) {
            return dataStyle;
        }
        dataStyle = workbook.createCellStyle();
        setDefaultBorder(dataStyle);
        dataStyle.setAlignment(horizontalalignment);
        dataStyle.setVerticalAlignment(verticalalignment);
        dataStyle.setWrapText(true);
        return dataStyle;
    }

    /**
     * 文本列样式
     *
     * @param workbook
     * @return 单元格样式
     */
    @Override
    public CellStyle getTextStyle(Workbook workbook) {
        if (textStyle != null) {
            return textStyle;
        }
        textStyle = workbook.createCellStyle();
        setDefaultBorder(textStyle);
        textStyle.setAlignment(horizontalalignment);
        textStyle.setVerticalAlignment(verticalalignment);
        DataFormat format = workbook.createDataFormat();
        textStyle.setDataFormat(format.getFormat("@"));
        textStyle.setWrapText(true);
        return textStyle;
    }

    /**
     * 不能为null的列头部样式
     *
     * @param workbook
     * @return 单元格样式
     */
    @Override
    public CellStyle getNotNullCellHeadStyle(Workbook workbook) {
        if (notNullCellHeadStyle != null) {
            return notNullCellHeadStyle;
        }
        notNullCellHeadStyle = workbook.createCellStyle();
        notNullCellHeadStyle.setFillForegroundColor(colorIndex);
        notNullCellHeadStyle.setFillPattern(fillType);
        setDefaultBorder(headStyle);
        notNullCellHeadStyle.setAlignment(horizontalalignment);
        notNullCellHeadStyle.setVerticalAlignment(verticalalignment);
        notNullCellHeadStyle.setWrapText(true);
        Font font = workbook.createFont();
        font.setColor(Font.COLOR_RED);
        notNullCellHeadStyle.setFont(font);
        return notNullCellHeadStyle;
    }

    private void setDefaultBorder(CellStyle cellStyle) {
        cellStyle.setBorderLeft(borderStyle);
        cellStyle.setBorderRight(borderStyle);
        cellStyle.setBorderBottom(borderStyle);
        cellStyle.setBorderTop(borderStyle);
    }
}
