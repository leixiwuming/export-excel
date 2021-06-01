package com.zxz.common.excel.convert.read;

import com.zxz.common.excel.convert.Convert;

import java.text.DecimalFormat;

public class DoubleToStringConvert implements Convert<Double,String> {
    DecimalFormat df = new DecimalFormat("##########.##########");

    @Override
    public String convert(Double aDouble) {

        return df.format(aDouble);
    }
}
