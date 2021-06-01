package com.zxz.common.excel.convert.write;

import com.zxz.common.excel.convert.Convert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDataTimeToStringConvert implements Convert<LocalDateTime,String> {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public String convert(LocalDateTime localDateTime) {
        return formatter.format(localDateTime);
    }
}
