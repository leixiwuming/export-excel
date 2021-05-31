package com.zxz.common.excel.convert.get;

import com.zxz.common.excel.convert.Convert;

public class StringToBooleanConvert implements Convert<Boolean, String> {
    @Override
    public Boolean convert(String s) {
        if (s.equals("true")) {
            return true;
        }
        return false;
    }
}
