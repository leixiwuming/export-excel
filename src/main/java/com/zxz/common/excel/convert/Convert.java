package com.zxz.common.excel.convert;

@FunctionalInterface
public interface Convert<T, R> {
    R convert(T r);

    static Convert emptyConvert() {
        return s -> s;
    }
}
