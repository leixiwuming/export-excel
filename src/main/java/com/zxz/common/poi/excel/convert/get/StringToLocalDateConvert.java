package com.zxz.common.poi.excel.convert.get;

import com.zxz.common.poi.excel.convert.Convert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StringToLocalDateConvert implements Convert<LocalDate, String> {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDate convert(String s) {
        return LocalDate.parse(s, formatter);
    }
}
