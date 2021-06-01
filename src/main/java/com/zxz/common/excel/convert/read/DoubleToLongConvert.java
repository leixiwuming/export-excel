package com.zxz.common.excel.convert.read;

import com.zxz.common.excel.convert.Convert;

public class DoubleToLongConvert implements Convert<Double,Long> {
    @Override
    public Long convert(Double aDouble) {
        return aDouble.longValue();
    }
}
