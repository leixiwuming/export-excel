package com.zxz.common.excel.read;

import com.zxz.common.excel.ExcelConfig;
import com.zxz.common.excel.convert.BaseConvert;
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

/**
 * 默认读exel类
 *
 * @param <T>
 */
public abstract class DefaultReadExelData<T> extends ReadExcel<T> {
    private static List EMPTY_LIST = new ArrayList(0);
    //反射策略
    protected ReflectStrategy reflectStrategy;
    //转换器
    private BaseConvert baseConvert;

    public DefaultReadExelData() {
        this.reflectStrategy = ReadExcelConfig.getReflectStrategy();
        this.baseConvert = ReadExcelConfig.getConvertThreadLocal();
    }

    protected abstract Map<Integer, AnnotationMeta> readHead(Sheet sheet, Class<T> targetClass);

    @Override
    protected List<T> readData(Sheet sheet, Class<T> targetClass, Map<Integer, AnnotationMeta> mapping) {
        //获取最大行数的行标
        int lastRowNum = sheet.getLastRowNum();
        //航标小于1，说明是个空页或者只含表头,返回空集合
        if (lastRowNum < 1) {
            return EMPTY_LIST;
        }
        //存放每一行的数据实体
        List<T> res = new ArrayList();
        //遍历每一行
        for (int rowIndex = 0; rowIndex <= lastRowNum; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            //检查是否为数据行
            if (!checkIsData(row)) {
                continue;
            }
            res.add(readLine(targetClass, row, mapping));
        }
        return res;

    }

    /**
     * 检查是否为数据行
     * @see ExcelConfig 首列出现其中标识的就不是数据行
     * @param row
     * @return
     */
    private boolean checkIsData(Row row) {
        if (row == null) {
            return false;
        }
        //获取第一列的值
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
