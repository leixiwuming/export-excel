package com.zxz.common.excel.convert;

public interface Convert<T,R> {
    T convert(R r);
}
