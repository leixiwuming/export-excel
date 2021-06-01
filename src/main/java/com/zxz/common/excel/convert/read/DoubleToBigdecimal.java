package com.zxz.common.excel.convert.read;

import com.zxz.common.excel.convert.Convert;

import java.math.BigDecimal;

public class DoubleToBigdecimal implements Convert<Double,BigDecimal> {
    @Override
    public BigDecimal convert(Double aDouble) {
        return new BigDecimal(String.valueOf(aDouble));
    }
}
