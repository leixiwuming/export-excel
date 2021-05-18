package com.zxz.common.excel.convert.set;

import com.zxz.common.excel.convert.Convert;

public  class NullConvert implements Convert<String, Object> {
    @Override
    public String convert(Object o) {
        return "\\";
    }
}
