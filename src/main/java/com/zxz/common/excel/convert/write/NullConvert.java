package com.zxz.common.excel.convert.write;

import com.zxz.common.excel.convert.Convert;

public  class NullConvert implements Convert<Object,String> {
    @Override
    public String convert(Object o) {
        return "\\";
    }
}
