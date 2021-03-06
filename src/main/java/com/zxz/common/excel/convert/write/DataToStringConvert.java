package com.zxz.common.excel.convert.write;

import com.zxz.common.excel.convert.Convert;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataToStringConvert implements Convert<Date,String> {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public String convert(Date date) {
        return formatter.format(date);
    }
}
