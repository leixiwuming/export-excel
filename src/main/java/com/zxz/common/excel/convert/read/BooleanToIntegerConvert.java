package com.zxz.common.excel.convert.read;

import com.zxz.common.excel.convert.Convert;

public class BooleanToIntegerConvert implements Convert<Boolean,Integer> {
    @Override
    public Integer convert(Boolean b) {
        return b ? 1 : 0;
    }
}
