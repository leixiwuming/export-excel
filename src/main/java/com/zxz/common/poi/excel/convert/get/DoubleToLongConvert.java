package com.zxz.common.poi.excel.convert.get;

import com.zxz.common.poi.excel.convert.Convert;

public class DoubleToLongConvert implements Convert<Long, Double> {
    @Override
    public Long convert(Double aDouble) {
        return aDouble.longValue();
    }
}
