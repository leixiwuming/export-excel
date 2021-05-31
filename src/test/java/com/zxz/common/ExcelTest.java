package com.zxz.common;


import com.zxz.common.entity.Entity;
import com.zxz.common.excel.read.ReadExcel;
import com.zxz.common.excel.read.ReadSimpleExcel;
import com.zxz.common.excel.write.WriteExcel;
import com.zxz.common.excel.write.WriteSimpleExcel;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class ExcelTest {

    public Entity initEntity() {
        Entity entity = new Entity();
        entity.setName("这是一个测试");
        entity.setDeleted(0);
        entity.setFlag(true);
        entity.setLength(30.2D);
        entity.setPay(new BigDecimal("50.8"));
        entity.setSize(10);
        entity.setCreateDate(LocalDateTime.now());
        entity.setIdCard("430124189920121234");
        return entity;
    }

    @Test
    public void createExcel() throws IOException {
        WriteExcel<Entity> writeExcel = new WriteSimpleExcel();
        Workbook workbook = writeExcel.writeExcel("导出测试", Arrays.asList(initEntity(), initEntity()), Entity.class, true);
        OutputStream outputStream = new FileOutputStream("test.xlsx");
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
    }

    @Test
    public void readExcel() throws IOException {
        ReadExcel<Entity> readExcel = new ReadSimpleExcel();
        Object read = readExcel.read(new FileInputStream("test.xlsx"), Entity.class);
        System.out.println(read);

    }
}
