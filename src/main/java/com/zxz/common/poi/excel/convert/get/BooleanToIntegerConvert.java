package com.zxz.common.poi.excel.convert.get;

import com.zxz.common.poi.excel.convert.Convert;

public class BooleanToIntegerConvert implements Convert<Integer, Boolean> {
    @Override
    public Integer convert(Boolean b) {
        return b ? 1 : 0;
    }
}
