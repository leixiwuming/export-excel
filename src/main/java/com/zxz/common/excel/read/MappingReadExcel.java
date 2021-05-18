package com.zxz.common.excel.read;

import com.zxz.common.excel.ExcelConfig;
import com.zxz.common.excel.annotation.Mapping;
import com.zxz.common.excel.check.BaseCheck;
import com.zxz.common.excel.model.AnnotationMeta;
import com.zxz.common.excel.reflect.ReflectStrategy;
import com.zxz.common.excel.util.CellUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MappingReadExcel<T> extends DefaultReadExel<T> {
    private BaseCheck baseCheck;
    private Map<Class, Map<Integer, AnnotationMeta>> classAnnotationMetaCache;

    public MappingReadExcel(ReflectStrategy reflectStrategy) {
        super(reflectStrategy);
        classAnnotationMetaCache = new HashMap<>();
    }

    public void setBaseCheck(BaseCheck baseCheck) {
        this.baseCheck = baseCheck;
    }

    /**
     * 读取第一个head行
     *
     * @param sheet
     * @param targetClass
     * @return
     */
    @Override
    protected Map<Integer, AnnotationMeta> readHead(Sheet sheet, Class<T> targetClass) {
        Map<Integer, AnnotationMeta> mapping = classAnnotationMetaCache.get(targetClass);
        if (mapping != null) {
            return mapping;
        }

        int lastRowNum = sheet.getLastRowNum();
        for (int i = 0; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            Object cellValue = CellUtil.getCellValue(row.getCell(0));
            if (!ExcelConfig.HEAD_TAG.equals(cellValue)) {
                continue;
            }
            mapping = readHeadRow(row, targetClass);
        }

        classAnnotationMetaCache.put(targetClass, mapping);
        return mapping;
    }

    private Map<Integer, AnnotationMeta> readHeadRow(Row row, Class<T> targetClass) {
        Map<Integer, AnnotationMeta> res = new HashMap<>();
        List<Mapping> annotations = reflectStrategy.getAnnotations(targetClass, Mapping.class);
        short lastCellNum = row.getLastCellNum();
        for (int i = 1; i < lastCellNum; i++) {
            Object cellValue = CellUtil.getCellValue(row.getCell(i));
            if (cellValue == null) {
                continue;
            }
            for (Mapping annotation : annotations) {
                if (cellValue.equals(annotation.value())) {
                    res.put(i, annotation)
                }
            }
        }
    }

    public AnnotationMeta getAnnotationMetaByMapping(Mapping annotation,Class<T> targetClass) {
        AnnotationMeta annotationMeta = new AnnotationMeta();
        annotationMeta.setValue(annotation.value());
        annotationMeta.setDefaultValue(annotation.defaultValue());
    }
}
