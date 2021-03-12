package com.zxz.common.poi.excel.function;

import com.zxz.common.poi.excel.function.abs.BaseSpecialCell;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

public class SpecialCellmpl<T> extends BaseSpecialCell<T> {

    @Override
    public void configFunction(Workbook workbook) {
//        CellStyle cellDataStyle = createHeaderStyle(workbook);
//        getCellPredicates().put(cell -> {
//            if (cell.getRowIndex() == 1 && cell.getColumnIndex() == 3) {
//                return true;
//            }
//            return false;
//        }, cellDataStyle);
//
//        getCellPredicates().put(cell -> {
//            if (cell.getRowIndex() == 2 && cell.getColumnIndex() == 0) {
//                return true;
//            }
//            return false;
//        }, cellDataStyle);
    }

    @Override
    public void configSpecialCell() {
        getSpecialCells().add((row, cellIndex, nowData, nextData, value,mapping) -> cellIndex);
    }



    //    public static void main(String[] args) {
//        RowFunction rowFunction = new RowFunction();
//        rowFunction.setWorkbook(new XSSFWorkbook());
//        List<Function<Integer, CellStyle>> rowFunction1 = rowFunction.getRowFunction();
//        for (int i = 0; i < 50000; i++) {
//            final CellStyle apply = rowFunction1.get(0).apply(0);
//
//        }
//        try {
//            Thread.sleep(4000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(1);
//}
//    public static void main(String[] args) {
//        String s="15116189692";
//        System.out.println(s.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2"));
//    }
    private CellStyle createHeaderStyle(Workbook wb) {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyle;
    }

    private CellStyle createCellDataStyle(Workbook wb) {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyle;
    }
}
