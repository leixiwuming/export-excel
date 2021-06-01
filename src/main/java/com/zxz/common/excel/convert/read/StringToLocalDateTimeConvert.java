package com.zxz.common.excel.convert.read;

import com.zxz.common.excel.convert.Convert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringToLocalDateTimeConvert implements Convert<String, LocalDateTime> {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public LocalDateTime convert(String s) {
        return LocalDateTime.parse(s, formatter);
    }
}
