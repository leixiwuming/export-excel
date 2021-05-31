package com.zxz.common.excel.write;

import com.zxz.common.excel.convert.BaseConvert;
import com.zxz.common.excel.model.AnnotationMeta;
import com.zxz.common.excel.reflect.ReflectStrategy;
import com.zxz.common.excel.reflect.SingpleMethodParameter;
import com.zxz.common.excel.util.CollectionUtil;
import com.zxz.common.excel.write.conf.CellStyleConfig;
import com.zxz.common.excel.write.conf.WriteExcelConfig;
import com.zxz.common.excel.write.function.Sequence;
import com.zxz.common.excel.write.function.SpecialCell;
import com.zxz.common.excel.write.function.SpecialRow;
import com.zxz.common.util.Assert;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class DefaultWriteData<T> extends WriteExcel<T> {
    @Override
    protected int writeData(Sheet sheet, int startRowIndex, Map<Integer, AnnotationMeta> map, List<T> entitys, Sequence sequence) {
        //默认数据样式
        CellStyleConfig cellStyleConfig = WriteExcelConfig.getCellStyleConfigThreadLocal();
        CellStyle textStyle = cellStyleConfig.getTextStyle(sheet.getWorkbook());
        CellStyle dataStyle = cellStyleConfig.getDataStyle(sheet.getWorkbook());
        //设置每列默认样式,setDefaultColumnStyle 这个方法对创建的单元格无效，貌似官方也不会解决这个问题
        //详见https://bz.apache.org/bugzilla/show_bug.cgi?id=51037
        //但是为啥这串代码还在呢，它可以解决另一个问题
        //当用户在某一列输入身份证时，excel会识别成数字，会丢失精度,这一步可以帮助用户设置单元格默认为文本格式
        map.forEach((cellIndex, meta) -> {
            sheet.setDefaultColumnStyle(cellIndex, meta.getTextColumn() ? textStyle : dataStyle);
        });
        if (CollectionUtil.isEmpty(entitys)) {
            return startRowIndex;
        }

        //获取特殊行的函数
        List<SpecialRow> specialRows = WriteExcelConfig.getSpecialRowThreadLocal();
        //获取处理特殊单元格函数
        List<SpecialCell> specialCells = WriteExcelConfig.getSpecialCellThreadLocal();
        //反射策略
        ReflectStrategy reflectStrategy = WriteExcelConfig.getReflectStrategy();
        //转换器
        BaseConvert convert = WriteExcelConfig.getConvertThreadLocal();
        T nowEntity = null, nextEntity;
        for (int i = 0; i < entitys.size(); i++) {
            if (nowEntity == null) {
                nowEntity = entitys.get(i);
            }
            if (i + 1 >= entitys.size()) {
                nextEntity = null;
            } else {
                nextEntity = entitys.get(i + 1);
            }
            //获取特殊行的函数
            startRowIndex = dealSpeciaRow(specialRows, sheet, startRowIndex, nowEntity, nextEntity);
            //放这么多参数是减少ThreadLocal get方法的hash计算（不确定这么搞会不会快一点≡(▔﹏▔)≡）
            startRowIndex = writeRow(sheet, startRowIndex, nowEntity, nextEntity, map, specialCells, reflectStrategy, sequence, convert, cellStyleConfig);
            nowEntity = nextEntity;
        }
        return startRowIndex;
    }

    private int writeRow(Sheet sheet, int rowIndex, T nowEntity, T nextEntity,
                         Map<Integer, AnnotationMeta> map, List<SpecialCell> specialCells,
                         ReflectStrategy reflectStrategy, Sequence sequence,
                         BaseConvert convert, CellStyleConfig cellStyleConfig) {
        //创建行
        Row row = sheet.createRow(rowIndex);
        //遍历映射关系，写入单元格
        map.forEach((cellIndex, meta) -> {
            //转换后的属性值（遍历配置的的转换器，找到第一个支持该类型的转换器进行转换）
            //要转一遍的原因，可能有些属性的类型比较奇怪，添加转换器就可以适配，不会报错，一般转成str就行了
            Object convertValue = null;
            if (meta.getSequence()) {
                //如果是序号列，获取序号
                convertValue = convert.getConvertValue(sequence.getSequenceVal());
            } else {
                if (meta.getDict().length != 0) {
                    convertValue = dealDict(meta, reflectStrategy.invoke(nowEntity, new SingpleMethodParameter(meta.getGetMethodName())));
                } else {
                    convertValue = convert.getConvertValue(reflectStrategy.invoke(nowEntity, new SingpleMethodParameter(meta.getGetMethodName())));
                }
            }
            CellStyle cellStyle = meta.getTextColumn() ? cellStyleConfig.getTextStyle(sheet.getWorkbook()) : cellStyleConfig.getDataStyle(sheet.getWorkbook());
            if (!specialCells.isEmpty()) {
                //执行特殊列
                for (SpecialCell specialCell : specialCells) {
                    if (specialCell.apply(row, cellIndex, nowEntity, nextEntity, convertValue, meta, cellStyle) != cellIndex) {
                        return;
                    }
                }
            }
            //非特殊列导出
            Cell cell = row.createCell(cellIndex);
            setCellVal(convertValue, cell);
            cell.setCellStyle(cellStyle);
            if (convertValue != null) {
                setLenth(cellIndex, meta, convertValue);
            }
        });
        return ++rowIndex;
    }

    protected void setLenth(int cellIndex, AnnotationMeta annotation, Object convertValue) {
        int lenth = String.valueOf(convertValue).getBytes(StandardCharsets.UTF_8).length;
        if (lenth > annotation.getMaxwidth() && annotation.getMaxwidth() != 0) {
            lenth = annotation.getMaxwidth();
        }
        if (lenth < annotation.getMinWidth() && annotation.getMinWidth() != 0) {
            lenth = annotation.getMinWidth();
        }
        WriteExcelConfig.putCellWidth(cellIndex, WriteExcelConfig.getCalculationWidthThreadLocal().apply(lenth));
    }


    protected Object dealDict(AnnotationMeta annotation, Object value) {
        if (annotation.getDict().length == 0) {
            return value;
        }
        if (value instanceof Integer) {
            int index = ((int) value - annotation.getDictIndex()) / (annotation.getDictStep() + 1);

            if (index < annotation.getDict().length) {
                value = annotation.getDict()[index];
            }
        }
        return value;

    }

    protected void setCellVal(Object value, Cell cell) {
        Assert.notNull(cell, "cell is null");
        if (value == null) {
            return;
        }
        if (value instanceof String) {
            cell.setCellValue((String) value);
        }
        if (value instanceof Date) {
            cell.setCellValue((Date) value);
        }
        if (value instanceof LocalDateTime) {
            cell.setCellValue((LocalDateTime) value);
        }
        if (value instanceof LocalDate) {
            cell.setCellValue((LocalDate) value);
        } else {
            cell.setCellValue(String.valueOf(value));
        }
    }

    protected int dealSpeciaRow(List<SpecialRow> specialRows, Sheet sheet, int rowIndex, T nowDto, T nextDto) {
        if (specialRows.isEmpty()) {
            return rowIndex;
        }
        //循环执行特殊行
        for (SpecialRow specialRow : specialRows) {
            rowIndex = specialRow.apply(sheet, rowIndex, nowDto, nextDto);
        }
        return rowIndex;
    }
}
