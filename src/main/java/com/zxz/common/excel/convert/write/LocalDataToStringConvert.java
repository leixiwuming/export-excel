package com.zxz.common.excel.convert.write;

import com.zxz.common.excel.convert.Convert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDataToStringConvert implements Convert<LocalDate,String> {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public String convert(LocalDate localDate) {
        return formatter.format(localDate);
    }
}
