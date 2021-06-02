package com.zxz.common.excel.write;

import com.zxz.common.excel.model.AnnotationMeta;
import com.zxz.common.excel.util.Assert;
import com.zxz.common.excel.util.CollectionUtil;
import com.zxz.common.excel.util.StringUtils;
import com.zxz.common.excel.write.conf.WriteExcelConfig;
import com.zxz.common.excel.write.function.Sequence;
import com.zxz.common.excel.write.function.impl.Increasing;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class WriteExcel<T> {

    //生成 导出模板
    protected abstract int writeTemplate(Sheet sheet, int startRowIndex, Class<T> entity);

    //写入头部
    protected abstract ReadHeadResult writeHead(Sheet sheet, int startRowIndex, Class<T> entity, Boolean useSequence);

    //写入数据
    protected abstract int writeData(Sheet sheet, int startRowIndex, Map<Integer, AnnotationMeta> metaMap, List<T> entitys, Sequence sequence);


    protected void setDataValidation(Sheet sheet, String[] dict, int cellIndex, int startRowIndex, int endRowNum) {
        DataValidationHelper validationHelper = sheet.getDataValidationHelper();
        // 加载下拉列表内容
        DataValidationConstraint explicitListConstraint = validationHelper.createExplicitListConstraint(dict);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(startRowIndex, endRowNum, cellIndex, cellIndex);
        // 数据有效性对象
        DataValidation validation = validationHelper.createValidation(explicitListConstraint, regions);
        validation.setSuppressDropDownArrow(true);
        validation.createErrorBox("该值不被允许", "请从下拉列表选取");
        //错误警告框
        validation.setShowErrorBox(true);
        sheet.addValidationData(validation);
    }

    protected void setColumnWidth(Sheet sheet) {
        WriteExcelConfig.getCellWidth().forEach((k, v) -> {
            sheet.setColumnWidth(k, v);
        });
    }

    //创建sheet页，内容由writeHead和writeData 提供
    private Workbook writeSheetData(Workbook workBook, String sheetName, List<T> entitys, Class<T> entityClass, Object useSequence) {
        sheetName = StringUtils.isNullReplace(sheetName, WriteExcelConfig.DEFAULT_SHEET_NAME);
        Sequence sequence = getSequence(useSequence);
        wirteSheet(sheetName, entitys, entityClass, workBook, sequence);
        WriteExcelConfig.remove();
        return workBook;
    }

    //封装一下导出参数
    public Workbook writeExcel(String sheetName, List<T> entitys, Class<T> entityClass, boolean sequence) {
        Workbook workbook = createWorkBook();
        return writeSheetData(workbook, sheetName, entitys, entityClass, sequence);
    }

    public Workbook writeExcel(String sheetName, List<T> entitys, Class<T> entityClass, Sequence sequence) {
        Workbook workbook = createWorkBook();
        return writeSheetData(workbook, sheetName, entitys, entityClass, sequence);
    }

    public Workbook writeExcel(Map<String, List<T>> sheetMap, Class<T> entityClass, Class<? extends Sequence> sequence) {
        Workbook workbook = createWorkBook();
        sheetMap.forEach((sheetName, entitys) -> {
            writeSheetData(workbook, sheetName, entitys, entityClass, sequence);
        });
        return workbook;
    }

//    public Workbook writeExcel(Map<String, List<T>> sheetMap, Class<? extends Sequence> sequence) {
//        Workbook workbook = createWorkBook();
//        sheetMap.forEach((sheetName, entitys) -> {
//            writeSheetData(workbook, sheetName, entitys, entityClass, sequence);
//        });
//        return workbook;
//    }

    public Workbook writeExcel(Map<String, List<T>> sheetMap, Class<T> entityClass) {
        Workbook workbook = createWorkBook();
        Class<? extends Sequence> sequence = Increasing.class;
        sheetMap.forEach((sheetName, entitys) -> {
            writeSheetData(workbook, sheetName, entitys, entityClass, sequence);
        });
        return workbook;
    }

    public Workbook writeExcel(String sheetName, List<T> entitys, boolean sequence) {
        checkEntitys(entitys);
        Workbook workbook = createWorkBook();
        return writeSheetData(workbook, sheetName, entitys, (Class<T>) entitys.get(0).getClass(), sequence);
    }

    public Workbook writeExcel(String sheetName, List<T> entitys, Sequence sequence) {
        checkEntitys(entitys);
        Workbook workbook = createWorkBook();
        return writeSheetData(workbook, sheetName, entitys, (Class<T>) entitys.get(0).getClass(), sequence);
    }

    public Workbook writeExcel(List<List<T>> entitys, Class<? extends Sequence> sequence) {
        Workbook workbook = createWorkBook();
        checkEntitys(entitys);
        for (List<T> entity : entitys) {
            if (CollectionUtil.isEmpty(entity)) {
                continue;
            }
            writeSheetData(workbook, null, entity, (Class<T>) entity.get(0).getClass(), sequence);
        }
        return workbook;
    }

    private void checkEntitys(Collection entitys) {
        Assert.notEmpty(entitys, "导出列表不能为空");
    }


    protected Workbook createWorkBook() {
        Workbook workbook = new XSSFWorkbook();
        return workbook;
    }

    private Sequence getSequence(Object seq) {
        if (seq instanceof Sequence) {
            return (Sequence) seq;
        } else if (seq instanceof Boolean) {
            if ((boolean) seq) {
                return new Increasing();
            }
        } else if (seq instanceof Class) {
            return WriteExcelConfig.getReflectStrategy().newInstance((Class<? extends Sequence>) seq);
        }
        return null;
    }

    private Sheet wirteSheet(String sheetName, List<T> entitys, Class<T> entityClass, Workbook workbook, Sequence sequence) {
        Sheet sheet = workbook.createSheet(sheetName);
        boolean useSequence = sequence == null ? false : true;
        ReadHeadResult result = writeHead(sheet, 0, entityClass, useSequence);
        int endHeadIndex = result.getNextEndHeadRowIndex();
        int endDataIndex = writeData(sheet, endHeadIndex, result.getMetaMap(), entitys, sequence);
        setColumnWidth(sheet);
        WriteExcelConfig.removeCellWidth();
        if (endDataIndex != endHeadIndex) {
            result.getMetaMap().forEach(
                    (cellIndex, meta) -> {
                        if (meta.getDict() != null && 0 != meta.getDict().length) {
                            setDataValidation(sheet, meta.getDict(), cellIndex, endHeadIndex, endDataIndex-1);
                        }
                    }
            );
        }
        return sheet;
    }


}
