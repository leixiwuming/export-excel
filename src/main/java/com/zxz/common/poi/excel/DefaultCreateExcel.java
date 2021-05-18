package com.zxz.common.poi.excel;

import com.zxz.common.exception.BaseException;
import com.zxz.common.poi.excel.abs.CreateExcel;
import com.zxz.common.poi.excel.abs.Sequence;
import com.zxz.common.poi.excel.annotation.SequenceMapping;
import com.zxz.common.poi.excel.cache.CacheData;
import com.zxz.common.poi.excel.convert.BaseConvert;
import com.zxz.common.poi.excel.convert.set.WriteConverts;
import com.zxz.common.poi.excel.exception.ExcelDtoException;
import com.zxz.common.poi.excel.function.abs.BaseSpecialCell;
import com.zxz.common.poi.excel.function.abs.BaseSpecialRow;
import com.zxz.common.poi.excel.function.abs.SpecialCell;
import com.zxz.common.poi.excel.function.abs.SpecialRow;
import com.zxz.common.poi.excel.usermodel.AnnotationMeta;
import com.zxz.common.poi.excel.usermodel.ExamplesModel;
import com.zxz.common.poi.excel.usermodel.ExportStyleConfig;
import com.zxz.common.poi.excel.usermodel.abs.ExportStyleConfigAbs;
import com.zxz.common.poi.excel.util.GreaterMap;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @author zxc
 */
public class DefaultCreateExcel<T> implements CreateExcel<T> {
    /**
     * 特殊行处理
     */
    private BaseSpecialRow<T> baseSpecialRow;
    /**
     * 特殊单元格
     */
    private BaseSpecialCell<T> baseSpecialCell;
    /**
     * 使用序号，序号为list下标+1
     */
    private boolean useSequence;
    /**
     * 计算列长和默认style的配置
     */
    private ExportStyleConfigAbs exportStyleConfigAbs;
    /**
     * 属性转换
     */
    private BaseConvert baseConvert;
    /**
     * 默认数据列的style
     */
    private CellStyle defaultDataStyle;
    /**
     * 默认头部列的style
     */
    private CellStyle defaultHeadStyle;
    /**
     *
     */
    /**
     * 默认文本的style
     */
    private CellStyle defaultTextStyle;

    /**
     * 默认数据列的style
     */
    private CellStyle defaultNotNullStyle;
    /**
     * 列数和最大的字节长度
     */
    private Map<Integer, Integer> fieldMaxLength;
    /**
     * 注解列表
     */
    private List<AnnotationMeta> classAnnotation;

    public void setClassAnnotation(List<AnnotationMeta> classAnnotation) {
        this.classAnnotation = classAnnotation;
    }

    public List<AnnotationMeta> getClassAnnotation() {
        return classAnnotation;
    }

    /**
     * 序号生成
     */
    private Sequence sequence;


    DefaultCreateExcel(Builder builder) {
        this.exportStyleConfigAbs = builder.exportStyleConfigAbs;
        this.setConverts(builder.BaseConvert);
        this.baseSpecialRow = builder.baseSpecialRow;
        this.baseSpecialCell = builder.baseSpecialCell;
        this.useSequence = builder.useSequence;
        this.sequence = builder.sequence;
        fieldMaxLength = new GreaterMap<>();
    }

    public void setConverts(BaseConvert BaseConvert) {
        this.baseConvert = BaseConvert;
    }


    @Override
    public Workbook createExcel(Class<T> headClass, List<T> dtos, String sheetName) {
        Workbook workBook = createWorkBook();
        Sheet sheet = createSheet(workBook, sheetName);
        int rowIndex = createHead(sheet, headClass, false);
        if (dtos != null && !dtos.isEmpty()) {
            createData(sheet, rowIndex, dtos);
        }
        setColumnWidth(sheet);
        return workBook;
    }


    @Override
    public Workbook createWorkBook() {
        Workbook workbook = new SXSSFWorkbook();
        initArg(workbook);
        return workbook;
    }


     void setColumnWidth(Sheet sheet) {
        fieldMaxLength.forEach((k, v) -> {
            sheet.setColumnWidth(k, exportStyleConfigAbs.excelCalculationWidth().apply(v));
        });
    }

    @Override
    public Sheet createSheet(Workbook workBook, String sheetName) {
        return isNotBlank(sheetName) ? workBook.createSheet(sheetName) : workBook.createSheet();
    }

    @Override
    public int createHead(Sheet sheet, Class<T> headClass, boolean isTemplate) {
        int rowIndex = 0;
        if (isNull(classAnnotation)) {
            classAnnotation = CacheData.getOrGenClassMap(headClass);
        }
        rowIndex = setSpecialRow(sheet, rowIndex, null, null);
        rowIndex = setHead(sheet, rowIndex, isTemplate, headClass);
        if (isTemplate) {
            setColumnWidth(sheet);
        }
        return rowIndex;
    }


    int setHead(Sheet sheet, int rowIndex, boolean isTemplate, Class<T> headClass) {
        rowIndex = setHeadRow(sheet, rowIndex, isTemplate, headClass);
        return rowIndex;
    }

    @Override
    public int createData(Sheet sheet, int rowIndex, List<T> dtos) {
        return setData(sheet, rowIndex, dtos);
    }


    private int setData(Sheet sheet, int rowIndex, List<T> dtos) {
        if (!notEmpty(dtos)) {
            return rowIndex;
        }
        for (int i = 0; i < dtos.size(); i++) {
            rowIndex = createDataRow(sheet, rowIndex, dtos.get(i), i >= dtos.size() - 1 ? null : dtos.get(i + 1), classAnnotation);
        }
        return rowIndex;
    }

    private void initArg(Workbook workbook) {
        initDefaultStyle(workbook);
        initCellKeys(workbook);
    }

    private void initCellKeys(Workbook workbook) {
        if (baseSpecialCell != null) {
            baseSpecialCell.configFunction(workbook);
            baseSpecialCell.initCellKeyPredicate();
        }
    }

    private void initDefaultStyle(Workbook workbook) {
        defaultDataStyle = exportStyleConfigAbs.createDataStyle(workbook);
        defaultHeadStyle = exportStyleConfigAbs.createHeadStyle(workbook);
        defaultTextStyle = exportStyleConfigAbs.createtTextStyle(workbook);
        defaultNotNullStyle = exportStyleConfigAbs.createNtNullCellHeadStyle(workbook);
    }

    private int createDataRow(Sheet sheet, int rowIndex, T nowDto, T nextDto, List<AnnotationMeta> classAnnotation) {
        rowIndex = setSpecialRow(sheet, rowIndex, nowDto, nextDto);
        Row row = sheet.createRow(rowIndex);
//        setRowStyle(row);
        setRowDataCell(row, nowDto, nextDto, classAnnotation);
        return ++rowIndex;
    }

     int setSpecialRow(Sheet sheet, int rowIndex, T nowDto, T nextDto) {
        if (baseSpecialRow == null || baseSpecialRow.getSpecialRows() == null) {
            return rowIndex;
        }
        for (SpecialRow<T> specialRow : baseSpecialRow.getSpecialRows()) {
            rowIndex = specialRow.apply(sheet, rowIndex, nowDto, nextDto);
        }
        return rowIndex;
    }

    private int setSpecialCell(Row row, int cellIndex, T nowDto, T nextDto, Object value, AnnotationMeta annotation) {
        if (baseSpecialCell == null) {
            return cellIndex;
        }
        List<SpecialCell<T>> specialCells = baseSpecialCell.getSpecialCells();
        if (specialCells == null) {
            return cellIndex;
        }
        for (int i = specialCells.size() - 1; i >= 0; i--) {
            int ri = specialCells.get(i).apply(row, cellIndex, nowDto, nextDto, value, annotation);
            if (ri != cellIndex) {
                return ri;
            }
        }
        return cellIndex;
    }

    private void setRowDataCell(Row row, T nowDto, T nextDto, List<AnnotationMeta> classAnnotation) {
//        int cellIndex = cellSequence(row, dtoIndex);
        for (int i = 0; i < classAnnotation.size(); ) {
            i = setDataCell(row, i, nowDto, nextDto, classAnnotation.get(i));
        }

    }

//    private String getMethodName(String fieldName) {
//        char[] ch = fieldName.toCharArray();
//        if (ch[0] >= 'a' && ch[0] <= 'z') {
//            ch[0] = (char) (ch[0] - 32);
//        }
//        return "get" + new String(ch);
//    }

//    private int cellSequence(Row row, int dtoIndex) {
//        int cellIndex = 0;
//        if (useSequence) {
//            Cell cell = row.createCell(cellIndex);
//            cell.setCellStyle(defaultDataStyle);
//            if (sequence != null) {
//                setDataCell(Row row, int cellIndex, T nowDto, T nextDto, Object value, Mapping annotation);
//            } else {
//                cell.setCellValue(++dtoIndex);
//            }
//            setDataCellStyle(cell);
//            ++cellIndex;
//        }
//        return cellIndex;
//    }

    private int setDataCell(Row row, int cellIndex, T nowDto, T nextDto, AnnotationMeta annotation) {
        Object value = null;
        try {
            if (annotation.isSequence()) {
                value = sequence.getSequenceVal();
            } else {
                Class dtoClass = nowDto.getClass();
                Method method = dtoClass.getMethod(annotation.getGetMethodName());
                value = method.invoke(nowDto);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new ExcelDtoException("no get method");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new ExcelDtoException("invoke method error");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new ExcelDtoException("invoke method error");
        }

        int i = setSpecialCell(row, cellIndex, nowDto, nextDto, value, annotation);
        Cell cell = row.createCell(cellIndex);
        if (i == cellIndex) {
            setDataCellStyle(cell);
            Object convertValue = null;
            if (annotation.getDict().length != 0) {
                convertValue = dealDict(annotation, value);
            } else {
                convertValue = getConvertValue(value);
            }
            if (value != null) {
                baseConvert.convertToCell(convertValue, cell);
                setLenth(cellIndex, annotation, convertValue);
            }
        }
        return ++i;
    }

    private void setLenth(int cellIndex, AnnotationMeta annotation, Object convertValue) {
        int lenth = String.valueOf(convertValue).getBytes(StandardCharsets.UTF_8).length;
        if (lenth > annotation.getMaxwidth() && annotation.getMaxwidth() != 0) {
            lenth = annotation.getMaxwidth();
        }
        if (lenth < annotation.getMinWidth() && annotation.getMinWidth() != 0) {
            lenth = annotation.getMinWidth();
        }
        fieldMaxLength.put(cellIndex, lenth);
    }

    private Object dealDict(AnnotationMeta annotation, Object value) {
        if (value instanceof Integer) {
            int index = ((int) value - annotation.getDictIndex()) / (annotation.getDictStep() + 1);

            if (index < annotation.getDict().length) {
                value = annotation.getDict()[index];
            }
        }
        return value;

    }

    private Object getConvertValue(Object value) {
        return baseConvert.getConvertValue(value);
    }


    private boolean isNotBlank(String s) {
        return s != null && s.length() != 0;
    }

    private int setHeadRow(Sheet sheet, int rowIndex, boolean isTemplate, Class<T> headClass) {
        if (useSequence) {
            if (!classAnnotation.get(0).isSequence()) {
                classAnnotation.add(0, new SequenceMapping());
            }
        }
        int cellIndex = 0;
        Row row = sheet.createRow(rowIndex);
        for (AnnotationMeta annotation : classAnnotation) {
            String name = annotation.getValue();
            fieldMaxLength.put(cellIndex, name.getBytes(StandardCharsets.UTF_8).length);
            cellIndex = setHeadCell(row, name, cellIndex, annotation, isTemplate);
        }
        if (ExamplesModel.class.isAssignableFrom(headClass) && isTemplate) {
            rowIndex = setExaCell(sheet, ++rowIndex, headClass);
        }
        return rowIndex;
    }

    private int setExaCell(Sheet sheet, int rowIndex, Class<T> headClass) {
        ExamplesModel exampleModel = (ExamplesModel) ((ExamplesModel) getInstance(headClass)).getExamplesModel();
        return createData(sheet, rowIndex, Arrays.asList((T) exampleModel));
    }

    private T getInstance(Class<T> dto) {
        try {
            return dto.newInstance();
        } catch (InstantiationException e) {
            throw new BaseException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new BaseException(e.getMessage());
        }
    }


    private int setHeadCell(Row row, String value, int cellIndex, AnnotationMeta annotation, boolean isTemplate) {
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(value);
        if (annotation.isTextColumn()) {
            if (isTemplate) {
                cell.setCellStyle(defaultHeadStyle);
                row.getSheet().setDefaultColumnStyle(cellIndex, defaultTextStyle);
                return ++cellIndex;
            }
        }
        setHeadCellStyle(cell, isTemplate && annotation.isNotNull() ? defaultNotNullStyle : defaultHeadStyle);
        if (annotation.getDict().length != 0) {
            setDataValidation(row, annotation.getDict(), cellIndex);
        }
        return ++cellIndex;
    }

    private void setDataValidation(Row row, String[] dict, int cellIndex) {
        DataValidationHelper validationHelper = row.getSheet().getDataValidationHelper();
        // 加载下拉列表内容
        DataValidationConstraint explicitListConstraint = validationHelper.createExplicitListConstraint(dict);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(row.getRowNum() + 1, 1000, cellIndex, cellIndex);
        // 数据有效性对象
        DataValidation validation = validationHelper.createValidation(explicitListConstraint, regions);
        validation.setSuppressDropDownArrow(true);
        validation.createErrorBox("该值不被允许", "请从下拉列表选取");
        //错误警告框
        validation.setShowErrorBox(true);
        row.getSheet().addValidationData(validation);
    }

    private void setHeadCellStyle(Cell cell, CellStyle defaultStyle) {
        if (isNull(baseSpecialCell) || isNull(baseSpecialCell.getCellPredicates())) {
            cell.setCellStyle(defaultStyle);
            return;
        }
        setFunctionStyle(cell, defaultStyle);
    }

    private void setDataCellStyle(Cell cell) {
        if (isaNullCellKey()) {
            cell.setCellStyle(defaultDataStyle);
            return;
        }
        setFunctionStyle(cell, defaultDataStyle);
    }

    private void setFunctionStyle(Cell cell, CellStyle defaultStyle) {
        for (int i = baseSpecialCell.getCellKeyPredicate().size() - 1; i >= 0; i--) {
            Predicate<Cell> cellPredicate = baseSpecialCell.getCellKeyPredicate().get(i);
            if (cellPredicate.test(cell)) {
                cell.setCellStyle(baseSpecialCell.getCellPredicates().get(cellPredicate));
                return;
            }
        }
        cell.setCellStyle(defaultStyle);
    }

    private boolean isaNullCellKey() {
        return isNull(baseSpecialCell) || !notEmpty(baseSpecialCell.getCellKeyPredicate());
    }

    public
    boolean isNull(Object o) {
        return o == null;
    }

    private boolean notEmpty(Collection collection) {
        return collection != null && !collection.isEmpty();
    }

    private boolean notEmpty(Map map) {
        return map != null && !map.isEmpty();
    }

    public static class Builder {
        private ExportStyleConfigAbs exportStyleConfigAbs;
        private BaseConvert BaseConvert;
        private BaseSpecialRow baseSpecialRow;
        private BaseSpecialCell baseSpecialCell;
        private boolean useSequence = true;
        private Sequence sequence;

        public Builder() {
            this.exportStyleConfigAbs = new ExportStyleConfig();
            this.BaseConvert = new WriteConverts();
            this.sequence = new Increasing();

        }

        public Builder sequence(Sequence sequence) {
            this.sequence = sequence;
            return this;
        }

        public Builder styleConfigAbs(ExportStyleConfigAbs exportStyleConfigAbs) {
            this.exportStyleConfigAbs = exportStyleConfigAbs;
            return this;
        }

        public Builder baseConvert(BaseConvert baseConvert) {
            BaseConvert = baseConvert;
            return this;
        }

        public Builder useSequence(boolean useuseSequence) {
            this.useSequence = useuseSequence;
            return this;
        }

        public Builder rowFunction(BaseSpecialRow baseSpecialRow) {
            this.baseSpecialRow = baseSpecialRow;
            return this;
        }

        public Builder cellFunction(BaseSpecialCell baseSpecialCell) {
            this.baseSpecialCell = baseSpecialCell;
            return this;
        }

        public DefaultCreateExcel build() {
            return new DefaultCreateExcel(this);
        }

    }
}
