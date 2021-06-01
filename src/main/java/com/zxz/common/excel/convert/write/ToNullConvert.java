package com.zxz.common.excel.convert.write;

import com.zxz.common.excel.convert.Convert;

public class ToNullConvert<Object, R> implements Convert<Object, R> {
    @Override
    public R convert(Object object) {
        return null;
    }
}
