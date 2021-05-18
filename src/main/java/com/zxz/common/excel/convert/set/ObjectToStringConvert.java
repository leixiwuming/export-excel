package com.zxz.common.excel.convert.set;

import com.zxz.common.excel.convert.Convert;

public class ObjectToStringConvert implements Convert<String, Object> {
    @Override
    public String convert(Object o) {
        return String.valueOf(o);
    }
}
