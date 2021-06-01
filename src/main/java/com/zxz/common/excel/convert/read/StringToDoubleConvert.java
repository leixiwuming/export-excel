package com.zxz.common.excel.convert.read;

import com.zxz.common.excel.convert.Convert;

public class StringToDoubleConvert implements Convert<String,Double> {
    @Override
    public Double convert(String s) {
        return Double.parseDouble(s);
    }
}
