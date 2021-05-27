package com.zxz.common.excel.util;

public class InitThreadLocal<T> extends ThreadLocal<T> {
    private T t;

    public InitThreadLocal(T t) {
        this.t = t;
    }

    @Override
    protected T initialValue() {
        return t;
    }
}
