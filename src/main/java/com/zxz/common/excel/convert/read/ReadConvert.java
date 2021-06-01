package com.zxz.common.excel.convert.read;

import com.zxz.common.excel.convert.BaseConvert;
import com.zxz.common.excel.convert.Convert;
import com.zxz.common.excel.read.ReadExcelConfig;

import java.util.List;

public class ReadConvert extends BaseConvert {
    public ReadConvert() {
        super(ReadExcelConfig.getReflectStrategy());
        List<Convert> converts = getConverts();
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

    /**
     * 待转换的值为null的转换器
     *
     * @return
     */
    @Override
    protected Convert getNullConvert() {
        return null;
    }
}
