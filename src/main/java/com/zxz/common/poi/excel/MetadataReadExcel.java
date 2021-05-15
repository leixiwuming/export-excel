package com.zxz.common.poi.excel;

import com.zxz.common.exception.BaseException;
import com.zxz.common.poi.excel.read.ReadResult;
import com.zxz.common.poi.excel.usermodel.MetadataModel;

import java.io.InputStream;

/**
 * @author 24447
 */
public class MetadataReadExcel extends DefaultReadExcel {
    MetadataReadExcel(Builder builder) {
        super(builder);
    }

    public ReadResult readWorkBook(InputStream excelInputSteam, MetadataModel metadataModel) {
        try {
            return super.readWorkBook(excelInputSteam, Class.forName(metadataModel.getClassFullPath()));
        } catch (ClassNotFoundException e) {
            throw new BaseException("类加载失败");
        }

    }

}
