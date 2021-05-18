package com.zxz.common.excel.convert.get;

import com.zxz.common.excel.convert.Convert;

public class StringToDoubleConvert implements Convert<Double, String> {
    @Override
    public Double convert(String s) {
        return Double.parseDouble(s);
    }
}
