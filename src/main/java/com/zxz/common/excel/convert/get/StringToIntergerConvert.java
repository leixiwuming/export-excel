package com.zxz.common.excel.convert.get;

import com.zxz.common.excel.convert.Convert;

public class StringToIntergerConvert implements Convert<Integer, String> {
    @Override
    public Integer convert(String s) {
        return Integer.parseInt(s);
    }
}
