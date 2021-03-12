package com.zxz.common.poi.excel.convert.get;

import com.zxz.common.poi.excel.convert.Convert;

public class StringToDoubleConvert implements Convert<Double, String> {
    @Override
    public Double convert(String s) {
        return Double.parseDouble(s);
    }
}
