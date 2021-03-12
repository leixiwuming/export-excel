package com.zxz.common.poi.excel.convert;

public interface Convert<T,R> {
    T convert(R r);
}
