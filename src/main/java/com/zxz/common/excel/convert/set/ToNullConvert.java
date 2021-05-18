package com.zxz.common.excel.convert.set;

import com.zxz.common.excel.convert.Convert;

public class ToNullConvert implements Convert {
    @Override
    public Object convert(Object object) {
        return null;
    }
}
