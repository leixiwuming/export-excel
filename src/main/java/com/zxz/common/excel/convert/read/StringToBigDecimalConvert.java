package com.zxz.common.excel.convert.read;

import com.zxz.common.excel.convert.Convert;

import java.math.BigDecimal;

public class StringToBigDecimalConvert implements Convert<String,BigDecimal> {

    @Override
    public BigDecimal convert(String s) {
        return new BigDecimal(s);
    }
}
