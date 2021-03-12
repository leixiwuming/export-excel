package com.zxz.common.poi.excel;

import com.zxz.common.poi.excel.abs.Sequence;

public class Increasing implements Sequence {
    private int i = 0;

    @Override
    public Object getSequenceVal() {
        return ++i;
    }

    @Override
    public Object testVal() {
        return i;
    }


}
