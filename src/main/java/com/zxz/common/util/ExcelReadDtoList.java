package com.zxz.common.util;

import java.util.ArrayList;

public class ExcelReadDtoList<T> extends ArrayList<T> {
    @Override
    public void add(int index, T element) {
        if (element == null) {
            return;
        }
        super.add(index, element);
    }

    @Override
    public boolean add(T t) {
        if (t == null) {
            return false;
        }
        return super.add(t);
    }
}
