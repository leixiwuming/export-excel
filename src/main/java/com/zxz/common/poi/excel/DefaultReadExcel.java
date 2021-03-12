package com.zxz.common.poi.excel;

import com.zxz.common.exception.BaseException;
import com.zxz.common.poi.excel.abs.ReadExcel;
import com.zxz.common.poi.excel.annotation.Mapping;
import com.zxz.common.poi.excel.convert.BaseConvert;
import com.zxz.common.poi.excel.convert.get.ReadConverts;
import com.zxz.common.poi.excel.read.ReadResult;
import com.zxz.common.poi.excel.read.enums.CheckModel;
import com.zxz.common.poi.excel.usermodel.ReadStyleConfig;
import com.zxz.common.poi.excel.util.AnnotationUtil;
import com.zxz.common.poi.excel.util.CellUtil;
import com.zxz.common.poi.excel.util.ClassUtil;
import com.zxz.common.util.Assert;
import com.zxz.common.util.CollectionUtil;
import com.zxz.common.util.ExcelReadDtoList;
import com.zxz.common.util.IOUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DefaultReadExcel implements ReadExcel {
    private int maxErrorWdth = 0;

    private DefaultReadExcel(Builder builder) {
        checkModel = builder.checkModel;
        baseConvert = builder.baseConvert;
        if (builder.readStyleConfig != null) {
            readStyleConfig = builder.readStyleConfig;
        } else {
            readStyleConfig = new ReadStyleConfig();
        }

        if (builder.baseConvert == null) {
            this.baseConvert = new ReadConverts();
        }
        if (checkModel == null) {
            this.checkModel = CheckModel.STRICT;
        }
    }

    private CheckModel checkModel;
    private Map<Integer, Mapping> annotations = null;
    private List<Mapping> conditionCheckMapping = null;
    private BaseConvert baseConvert;
    private ReadStyleConfig readStyleConfig;

    private CellStyle errorHeadStyle;
    private CellStyle errorDataStyle;


    @Override
    public <T> ReadResult<T> readWorkBook(InputStream excelInputSteam, Class<T> dto) {
        return read(excelInputSteam, dto);
    }

    private <T> ReadResult<T> read(InputStream excelInputSteam, Class<T> dto) {
        ReadResult<T> readResult = new ReadResult<>();
        byte[] excelBytes = null;
        try {
            excelBytes = IOUtil.inputStreamToArrayByte(excelInputSteam);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                excelInputSteam.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Workbook workbook = getWorkBook(excelBytes);
        initArg(workbook);
        Assert.notNull(workbook, "非excel文件");
        for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            Row headRow = sheet.getRow(0);
            if (headRow == null) {
                continue;
            }
            readHead(headRow, dto, sheetIndex);
            final Cell cell = headRow.createCell(annotations.size());
            cell.setCellValue("错误说明");
            cell.setCellStyle(errorHeadStyle);
            maxErrorWdth = "错误说明".getBytes(StandardCharsets.UTF_8).length;
            readResult.setDtos(readData(sheet, dto));
            sheet.setColumnWidth(cell.getColumnIndex(), readStyleConfig.excelCalculationWidth().apply(maxErrorWdth));
        }
        readResult.setWorkbook(workbook);
        return readResult;
    }

    private void initArg(Workbook workbook) {
        errorHeadStyle = readStyleConfig.createErrorHeadStyle(workbook);
        errorDataStyle = readStyleConfig.createErrorDataStyle(workbook);
    }


    public <T> Map<Integer, Mapping> getAnnotations(Class<T> dto) {
        if (annotations == null) {
            Assert.notNull(dto, "数据错误");
            annotations = CollectionUtil.toMap(AnnotationUtil.getHeadClassAnnotation(dto));
        }
        return annotations;
    }

    @Override
    public <T> void readHead(Row row, Class<T> dto, int sheetIndex) {
        boolean check = checkModel.getBaseCheck().check(row, getAnnotations(dto));
        Assert.state(check, "第" + (sheetIndex + 1) + "页模板不匹配");
    }

    @Override
    public <T> List<T> readData(Sheet sheet, Class<T> dto) {
        List<T> dtos = new ExcelReadDtoList<>();
        int lastRowNum = sheet.getLastRowNum();
        for (int rowIndex = 1; rowIndex <= lastRowNum; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                continue;
            }
            if (dtos.add(readDateRow(row, dto))) {
                --rowIndex;
            }
        }
        return dtos;
    }

    @Override
    public <T> T readDateRow(Row row, Class<T> dto) {
        T t = getInstance(dto);
        getAnnotations(null).forEach((k, v) -> {
            Object cellValue = CellUtil.getCellValue(row.getCell(k));
            setDtoValue(cellValue, v, t);
        });
        List<String> errorMsg = checkDto(t);
        if (errorMsg == null) {
            row.getSheet().shiftRows(row.getRowNum() + 1, row.getSheet().getLastRowNum(), -1);
            return t;
        } else {
            writeErrorMsg(row, errorMsg);
            return null;
        }

    }

    private void writeErrorMsg(Row row, List<String> errorMsg) {
        final Cell cell = row.createCell(annotations.size());
        String join = CollectionUtil.join(",", errorMsg);
        cell.setCellValue(join);
        int length = join.getBytes(StandardCharsets.UTF_8).length;
        if (maxErrorWdth < length) {
            maxErrorWdth = length;
        }
        cell.setCellStyle(errorDataStyle);
    }


    @Override
    public <T> List<String> checkDto(T t) {
        List<String> errorMsg = null;
        List<Mapping> conditionCheckMapping = getConditionCheckMapping();
        Class<?> dtoClass = t.getClass();
        for (Mapping mapping : conditionCheckMapping) {
            if (dealStatus(t, mapping, dtoClass)) {
                if (errorMsg == null) {
                    errorMsg = new ArrayList<>();
                }
                errorMsg.add(mapping.value() + "不能为空");
            }
        }
        return errorMsg;
    }

    private <T> boolean dealStatus(T t, Mapping mapping, Class<?> dtoClass) {
        try {
            Object invoke = dtoClass.getMethod(mapping.getMethodName()).invoke(t);
            if (invoke == null) {
                return true;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return false;
    }

    private <T> void setDtoValue(Object cellValue, Mapping mapping, T t) {
        if (mapping == null) {
            return;
        }
        try {
            cellValue = dealDict(cellValue, mapping);
            Class<?> tagetClass = t.getClass().getDeclaredField(mapping.fieldName()).getType();
            Method method = t.getClass().getMethod(mapping.setMethodName(), tagetClass);
            if (cellValue == null) {
                if (!("").equals(mapping.defaultValue())) {
                    method.invoke(t,
                            baseConvert.getConvert(String.class, tagetClass).convert(mapping.defaultValue()));
                }
                return;
            }
            method.invoke(t,
                    cellValue.getClass().equals(ClassUtil.getPackClass(tagetClass)) ?
                            cellValue : baseConvert.getConvert(cellValue, tagetClass).convert(cellValue));
        } catch (NoSuchMethodException e) {
            throw new BaseException("no " + mapping.setMethodName() + " method");
        } catch (NoSuchFieldException e) {
            throw new BaseException("no " + mapping.fieldName() + " field");
        } catch (IllegalAccessException e) {
            throw new BaseException("invoke set method error");
        } catch (InvocationTargetException e) {
            throw new BaseException("invoke set method error");
        }

    }

    private Object dealDict(Object cellValue, Mapping annotation) {
        if (cellValue == null) {
            return null;
        }
        if (annotation.dict().length != 0) {
            for (int i = 0; i < annotation.dict().length; i++) {
                if (cellValue.equals(annotation.dict()[i])) {
                    return i * (annotation.dictStep() + 1) + annotation.dictIndex();
                }
            }
            return null;
        }
        return cellValue;
    }

    private <T> T getInstance(Class<T> dto) {
        try {
            return dto.newInstance();
        } catch (InstantiationException e) {
            throw new BaseException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new BaseException(e.getMessage());
        }
    }


    private Workbook getWorkBook(byte[] excelBytes) {
        Workbook workbook = null;
        Assert.notNull(excelBytes, "获取文件字节为空");
        try {
            workbook = new XSSFWorkbook(new ByteArrayInputStream(excelBytes));
        } catch (Exception e) {
            try {
                workbook = new HSSFWorkbook(new ByteArrayInputStream(excelBytes));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return workbook;
    }


    @Override
    public List<Mapping> getConditionCheckMapping() {
        if (conditionCheckMapping == null) {
            conditionCheckMapping = getAnnotations(null).values().
                    stream().filter(Mapping::notNull).collect(Collectors.toList());
        }
        return conditionCheckMapping;
    }

    public static class Builder {
        private BaseConvert baseConvert;
        private CheckModel checkModel;
        private ReadStyleConfig readStyleConfig;

        public Builder readStyleConfig(ReadStyleConfig readStyleConfig) {
            this.readStyleConfig = readStyleConfig;
            return this;
        }

        public Builder() {

        }

        public Builder checkModel(CheckModel checkModel) {
            this.checkModel = checkModel;
            return this;
        }

        public Builder baseConvert(BaseConvert baseConvert) {
            this.baseConvert = baseConvert;
            return this;
        }


        public DefaultReadExcel build() {
            return new DefaultReadExcel(this);
        }


    }
}
