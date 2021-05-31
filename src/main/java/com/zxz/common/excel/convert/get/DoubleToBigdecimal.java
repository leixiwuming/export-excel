package com.zxz.common.excel.convert.get;

import com.zxz.common.excel.convert.Convert;

import java.math.BigDecimal;

public class DoubleToBigdecimal implements Convert<BigDecimal, Double> {
    @Override
    public BigDecimal convert(Double aDouble) {
        return new BigDecimal(String.valueOf(aDouble));
    }
}
