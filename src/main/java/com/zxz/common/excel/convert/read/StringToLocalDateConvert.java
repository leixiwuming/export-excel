package com.zxz.common.excel.convert.read;

import com.zxz.common.excel.convert.Convert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StringToLocalDateConvert implements Convert<String,LocalDate> {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDate convert(String s) {
        return LocalDate.parse(s, formatter);
    }
}
