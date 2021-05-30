package com.zxz.common.excel.write.function.impl;


import com.zxz.common.excel.write.function.Sequence;

public class Increasing implements Sequence {
    private int i = 0;

    @Override
    public Object getSequenceVal() {
        return ++i;
    }

}
