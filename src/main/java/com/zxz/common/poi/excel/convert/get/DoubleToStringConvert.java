package com.zxz.common.poi.excel.convert.get;

import com.zxz.common.poi.excel.convert.Convert;

import java.text.DecimalFormat;

public class DoubleToStringConvert implements Convert<String, Double> {
    DecimalFormat df = new DecimalFormat("##########.##########");

    @Override
    public String convert(Double aDouble) {
        return df.format(aDouble);
    }
}
