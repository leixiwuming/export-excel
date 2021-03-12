package com.zxz.common.poi.excel.convert.set;

import com.zxz.common.poi.excel.convert.Convert;

public class ObjectToStringConvert implements Convert<String, Object> {
    @Override
    public String convert(Object o) {
        return String.valueOf(o);
    }
}
