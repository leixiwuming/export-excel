package com.zxz.common.excel.convert.read;

import com.zxz.common.excel.convert.Convert;

public class DoubleToIntegerConvert implements Convert<Double,Integer> {
    @Override
    public Integer convert(Double aDouble) {
        return (int)aDouble.doubleValue();
    }
}
