package com.zxz.common.excel.read;

import com.zxz.common.excel.ExcelConfig;
import com.zxz.common.excel.convert.BaseConvert;
import com.zxz.common.excel.convert.get.ReadConverts;
import com.zxz.common.excel.model.AnnotationMeta;
import com.zxz.common.excel.reflect.ReflectStrategy;
import com.zxz.common.excel.reflect.SingpleMethodParameter;
import com.zxz.common.excel.util.CellUtil;
import com.zxz.common.excel.util.ClassUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class DefaultReadExel<T> extends ReadExcel<T> {
    private static List EMPTY_LIST = new ArrayList(0);
    protected ReflectStrategy reflectStrategy;
    private BaseConvert baseConvert = new ReadConverts();

    public DefaultReadExel(ReflectStrategy reflectStrategy) {
        this.reflectStrategy = reflectStrategy;
    }

    protected abstract Map<Integer, AnnotationMeta> readHead(Sheet sheet, Class<T> targetClass);

    @Override
    protected List<T> readData(Sheet sheet, Class<T> targetClass, Map<Integer, AnnotationMeta> mapping) {
        int lastRowNum = sheet.getLastRowNum();
        if (lastRowNum < 1) {
            return EMPTY_LIST;
        }
        List<T> res = new ArrayList();
        for (int rowIndex = 0; rowIndex <= lastRowNum; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (!checkIsData(row)) {
                continue;
            }
            res.add(readLine(targetClass, row, mapping));
        }
        return res;

    }

    private boolean checkIsData(Row row) {
        if (row == null) {
            return false;
        }
        Object cellValue = CellUtil.getCellValue(row.getCell(0));
        return !(ExcelConfig.EXAMPLE_TAG.equals(cellValue) || ExcelConfig.HEAD_TAG.equals(cellValue));
    }

    private T readLine(Class<T> targetClass, Row row, Map<Integer, AnnotationMeta> mapping) {
        T t = reflectStrategy.newInstance(targetClass);
        mapping.forEach(
                (k, v) -> {
                    Object cellValue = CellUtil.getCellValue(row.getCell(k));
                    setFieldValue(cellValue, v, t, targetClass);
                }
        );
        return t;
    }


    private void setFieldValue(Object cellValue, AnnotationMeta mapping, T t, Class<T> targetClass) {
        if (mapping == null) {
            return;
        }
        //获取属性的数据类型
        Class fieldType = reflectStrategy.getFieldType(targetClass, mapping.getFieldName());
        //组装set方法参数
        SingpleMethodParameter singpleMethodParameter = new SingpleMethodParameter(mapping.getSetMethodName());
        singpleMethodParameter.setMethodParamType(fieldType);
        //检查是否有默认值
        if (cellValue == null) {
            if (!("").equals(mapping.getDefaultValue())) {
                singpleMethodParameter.setMethodParamValue(baseConvert.getConvertValue(mapping.getDefaultValue(), fieldType));
                reflectStrategy.invoke(t, singpleMethodParameter);
            }
            return;
        }
        //检查字典
        cellValue = dealDict(cellValue, mapping);
        singpleMethodParameter.setMethodParamValue(
                cellValue.getClass().equals(ClassUtil.getPackClass(fieldType))
                        ? cellValue : baseConvert.getConvertValue(cellValue, fieldType));
        reflectStrategy.invoke(t, singpleMethodParameter);

    }

    /**
     * 字典处理
     *
     * @param cellValue
     * @param annotation
     * @return
     */
    private Object dealDict(Object cellValue, AnnotationMeta annotation) {
        if (cellValue == null) {
            return null;
        }
        if (annotation.getDict().length != 0) {
            for (int i = 0; i < annotation.getDict().length; i++) {
                if (cellValue.equals(annotation.getDict()[i])) {
                    return i * (annotation.getDictStep() + 1) + annotation.getDictIndex();
                }
            }
            return null;
        }
        return cellValue;
    }
}
