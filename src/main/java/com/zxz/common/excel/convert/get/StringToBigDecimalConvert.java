package com.zxz.common.excel.convert.get;

import com.zxz.common.excel.convert.Convert;

import java.math.BigDecimal;

public class StringToBigDecimalConvert implements Convert<BigDecimal, String> {

    @Override
    public BigDecimal convert(String s) {
        return new BigDecimal(s);
    }
}
