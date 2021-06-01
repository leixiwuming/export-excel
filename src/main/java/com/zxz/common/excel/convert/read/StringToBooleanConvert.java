package com.zxz.common.excel.convert.read;

import com.zxz.common.excel.convert.Convert;

public class StringToBooleanConvert implements Convert<String,Boolean> {
    @Override
    public Boolean convert(String s) {
        if (s.equals("true")) {
            return true;
        }
        return false;
    }
}
