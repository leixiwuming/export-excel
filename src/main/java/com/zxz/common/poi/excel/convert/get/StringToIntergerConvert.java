package com.zxz.common.poi.excel.convert.get;

import com.zxz.common.poi.excel.convert.Convert;

public class StringToIntergerConvert implements Convert<Integer, String> {
    @Override
    public Integer convert(String s) {
        return Integer.parseInt(s);
    }
}
