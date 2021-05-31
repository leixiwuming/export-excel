package com.zxz.common.excel.write;

import com.zxz.common.excel.ExcelConfig;
import com.zxz.common.excel.model.AnnotationMeta;
import com.zxz.common.excel.model.Example;
import com.zxz.common.excel.reflect.ReflectStrategy;
import com.zxz.common.excel.util.Assert;
import com.zxz.common.excel.write.conf.CellStyleConfig;
import com.zxz.common.excel.write.conf.WriteExcelConfig;
import com.zxz.common.excel.write.function.SpecialRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.*;
import java.util.stream.Collectors;

public class WriteSimpleExcel<T> extends DefaultWriteData<T> {

    @Override
    protected int writeTemplate(Sheet sheet, int startRowIndex, Class<T> entity) {
        ReadHeadResult result = writeHead(sheet, startRowIndex, entity, false);
        startRowIndex = result.getNextEndHeadRowIndex();
        if (Example.class.isAssignableFrom(entity)) {
            ReflectStrategy reflectStrategy = WriteExcelConfig.getReflectStrategy();
            T t = reflectStrategy.newInstance(entity);
            startRowIndex = writeData(sheet, startRowIndex, result.getMetaMap(), Arrays.asList((T) ((Example) t).getExamples()), null);
        }
        Row row = sheet.getRow(startRowIndex - 1);
        Cell cell = row.createCell(row.getLastCellNum());
        setCellVal("示例数据", cell);
        return startRowIndex;
    }

    @Override
    protected ReadHeadResult writeHead(Sheet sheet, int startRowIndex, Class<T> entity, Boolean useSequence) {
        Map<Integer, AnnotationMeta> metaMap = getMaping(entity, useSequence);
        //特殊行
        List<SpecialRow> specialRows = WriteExcelConfig.getSpecialRowThreadLocal();
        //配置
        CellStyleConfig cellStyleConfig = WriteExcelConfig.getCellStyleConfigThreadLocal();
        //默认样式
        CellStyle headStyle = cellStyleConfig.getHeadStyle(sheet.getWorkbook());
        CellStyle notNullCellHeadStyle = cellStyleConfig.getNotNullCellHeadStyle(sheet.getWorkbook());
        //处理特殊行
        startRowIndex = dealSpeciaRow(specialRows, sheet, startRowIndex, null, null);
        //创建行
        Row row = sheet.createRow(startRowIndex);
        //设置第一列为头标记
        setCellVal(ExcelConfig.HEAD_TAG, row.createCell(0));
        metaMap.forEach((cellIndex, meta) -> {
            Cell cell = row.createCell(cellIndex);
            setCellVal(meta.getValue(), cell);
            if (meta.getNotNull()) {
                cell.setCellStyle(notNullCellHeadStyle);
            } else {
                cell.setCellStyle(headStyle);
            }
            setLenth(cellIndex, meta, meta.getValue());
        });
        sheet.setColumnHidden(0, true);
        return new ReadHeadResult(++startRowIndex, metaMap);
    }

    //生成映射关系 useSequence 开始列添加序列标识
    protected Map<Integer, AnnotationMeta> getMaping(Class<T> entityClass, boolean useSequence) {
        Map<Integer, AnnotationMeta> res = new HashMap<>();
        Assert.notNull(entityClass, "导出对象不能为null");
        //获取策略
        ReflectStrategy reflectStrategy = WriteExcelConfig.getReflectStrategy();
        //获取AnnotationMeta
        List<AnnotationMeta> annotations = reflectStrategy.getAnnotationMetas(entityClass);
        Assert.notEmpty(annotations, "未找到导出对象里的注解信息");
        //按照sort排序
        annotations = annotations.stream().filter(AnnotationMeta::getStatus).sorted(Comparator.comparingInt(AnnotationMeta::getSort)).collect(Collectors.toList());
        //判断是否添加序号
        if (useSequence) {
            //序号AnnotationMeta，添加到首位
            AnnotationMeta annotationMeta = new AnnotationMeta();
            annotationMeta.setStatus(true);
            annotationMeta.setValue("序号");
            annotationMeta.setSequence(true);
            annotationMeta.setTextColumn(true);
            annotations.add(0, annotationMeta);
        }
        //标头开始列索引为1，0索引存放标识
        int startCellIndex = 1;
        for (AnnotationMeta annotation : annotations) {
            res.put(startCellIndex, annotation);
            ++startCellIndex;
        }
        return res;
    }
}
