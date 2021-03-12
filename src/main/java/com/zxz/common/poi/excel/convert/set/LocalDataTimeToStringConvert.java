package com.zxz.common.poi.excel.convert.set;

import com.zxz.common.poi.excel.convert.Convert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDataTimeToStringConvert implements Convert<String,LocalDateTime> {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public String convert(LocalDateTime localDateTime) {
        return formatter.format(localDateTime);
    }
}
