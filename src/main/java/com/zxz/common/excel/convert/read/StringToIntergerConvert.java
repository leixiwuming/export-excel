package com.zxz.common.excel.convert.read;

import com.zxz.common.excel.convert.Convert;

public class StringToIntergerConvert implements Convert<String,Integer> {
    @Override
    public Integer convert(String s) {
        return Integer.parseInt(s);
    }
}
