package com.zxz.common.poi.excel.usermodel;

import com.zxz.common.poi.excel.usermodel.abs.ExportStyleConfigAbs;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import java.util.function.Function;


public class ExportStyleConfig implements ExportStyleConfigAbs {

    public ExportStyleConfig() {
        this.headColor = HSSFColor.HSSFColorPredefined.LIME;
        this.borderStyle = BorderStyle.THIN;
        this.fillType = FillPatternType.SOLID_FOREGROUND;
        this.horizontalalignment = HorizontalAlignment.CENTER;
        this.verticalalignment = VerticalAlignment.CENTER;
    }

    public HSSFColor.HSSFColorPredefined getHeadColor() {
        return headColor;
    }

    public void setHeadColor(HSSFColor.HSSFColorPredefined headColor) {
        this.headColor = headColor;
    }

    public BorderStyle getBorderStyle() {
        return borderStyle;
    }

    public void setBorderStyle(BorderStyle borderStyle) {
        this.borderStyle = borderStyle;
    }

    public FillPatternType getFillType() {
        return fillType;
    }

    public void setFillType(FillPatternType fillType) {
        this.fillType = fillType;
    }

    public HorizontalAlignment getHorizontalalignment() {
        return horizontalalignment;
    }

    public void setHorizontalalignment(HorizontalAlignment horizontalalignment) {
        this.horizontalalignment = horizontalalignment;
    }

    public VerticalAlignment getVerticalalignment() {
        return verticalalignment;
    }

    public void setVerticalalignment(VerticalAlignment verticalalignment) {
        this.verticalalignment = verticalalignment;
    }

    /**
     * 默认头部颜色
     */
    private HSSFColor.HSSFColorPredefined headColor;
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

    private void setDefaultBorder(CellStyle cellStyle) {
        cellStyle.setBorderLeft(borderStyle);
        cellStyle.setBorderRight(borderStyle);
        cellStyle.setBorderBottom(borderStyle);
        cellStyle.setBorderTop(borderStyle);
    }


    @Override
    public CellStyle createHeadStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(headColor.getIndex());
        cellStyle.setFillPattern(fillType);
        setDefaultBorder(cellStyle);
        cellStyle.setAlignment(horizontalalignment);
        cellStyle.setVerticalAlignment(verticalalignment);
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    @Override
    public CellStyle createDataStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(horizontalalignment);
        cellStyle.setVerticalAlignment(verticalalignment);
        setDefaultBorder(cellStyle);
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    @Override
    public CellStyle createtTextStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(horizontalalignment);
        cellStyle.setVerticalAlignment(verticalalignment);
        DataFormat format = workbook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("@"));
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    @Override
    public CellStyle createNtNullCellHeadStyle(Workbook workbook) {
        CellStyle cellStyle = createHeadStyle(workbook);
        Font font = workbook.createFont();
        font.setColor(Font.COLOR_RED);
        cellStyle.setFont(font);
        return cellStyle;
    }

    @Override
    public Function<Integer, Integer> excelCalculationWidth() {
        return width -> width * 256+196;
    }

}
