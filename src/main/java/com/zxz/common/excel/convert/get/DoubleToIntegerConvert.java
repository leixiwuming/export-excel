package com.zxz.common.excel.convert.get;

import com.zxz.common.excel.convert.Convert;

public class DoubleToIntegerConvert implements Convert<Integer, Double> {
    @Override
    public Integer convert(Double aDouble) {
        return (int)aDouble.doubleValue();
    }
}
