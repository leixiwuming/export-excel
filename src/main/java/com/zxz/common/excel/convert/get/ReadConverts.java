package com.zxz.common.excel.convert.get;

import com.zxz.common.excel.convert.BaseConvert;
import com.zxz.common.excel.convert.Convert;

import java.util.List;

public class ReadConverts<T, R> extends BaseConvert<T, R> {
    public ReadConverts() {
        List<Convert<?, ?>> converts = getConverts();
        converts.add(new StringToLocalDateConvert());
        converts.add(new StringToLocalDateTimeConvert());
        converts.add(new StringToDoubleConvert());
        converts.add(new StringToIntergerConvert());
        converts.add(new DoubleToIntegerConvert());
        converts.add(new DoubleToLocalDateConvert());
        converts.add(new DoubleToLocalDateTimeConvert());
        converts.add(new DoubleToStringConvert());
        converts.add(new BooleanToIntegerConvert());
        converts.add(new DoubleToLongConvert());
        converts.add(new StringToBigDecimalConvert());
        converts.add(new StringToBooleanConvert());
    }
}
