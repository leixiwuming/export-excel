package com.zxz.common.excel.read;

import com.zxz.common.excel.model.AnnotationMeta;
import com.zxz.common.excel.util.Assert;
import com.zxz.common.excel.util.IOUtil;
import com.zxz.common.exception.BaseException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class ReadExcel<T> {

    /**
     * 读取excel 头部，
     *
     * @return 索引和对应的注解数据
     */
    protected abstract Map<Integer, AnnotationMeta> readHead(Sheet sheet, Class<T> targetClass);

    /**
     * 读取excel 数据，返回
     *
     * @return 索引和对应的注解数据
     */
    protected abstract List<T> readData(Sheet sheet, Class<T> targetClass, Map<Integer, AnnotationMeta> mapping);

    /**
     * 读取excel
     *
     * @param inputStream 文件流
     * @param targetClass 每行的实体模型
     * @return
     */
    public final Object read(InputStream inputStream, Class<T> targetClass) {
        Workbook workBook = getWorkBook(inputStream);
        int numberOfSheets = workBook.getNumberOfSheets();
        List res = new ArrayList();
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = workBook.getSheetAt(i);
            res.addAll(readData(sheet, targetClass, readHead(sheet, targetClass)));
        }
        ReadExcelConfig.remove();
        return res;
    }

    public final Workbook getWorkBook(InputStream inputStream) {
        try {
           return WorkbookFactory.create(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new BaseException("读取excel出错");
//        byte[] bytes = null;
//        try {
//            bytes = IOUtil.inputStreamToArrayByte(inputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Workbook workbook = null;
//        try {
//            workbook = new XSSFWorkbook(new ByteArrayInputStream(bytes));
//        } catch (IOException e) {
//            try {
//                workbook = new HSSFWorkbook(new ByteArrayInputStream(bytes));
//            } catch (IOException ioException) {
//                ioException.printStackTrace();
//            }
//        }
//        Assert.notNull(workbook, "读取excel错误");
//        return workbook;
    }

}
