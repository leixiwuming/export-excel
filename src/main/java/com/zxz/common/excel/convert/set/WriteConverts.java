package com.zxz.common.excel.convert.set;

import com.zxz.common.excel.convert.BaseConvert;
import com.zxz.common.excel.convert.Convert;
import com.zxz.common.excel.convert.get.DoubleToStringConvert;

import java.util.List;

public class WriteConverts<T, R> extends BaseConvert<T, R> {


    public WriteConverts() {
        List<Convert<?, ?>> converts = getConverts();
        converts.add(new NullConvert());
        converts.add(new ObjectToStringConvert());
        converts.add(new LocalDataToStringConvert());
        converts.add(new LocalDataTimeToStringConvert());
        converts.add(new DataToStringConvert());
        converts.add(new DoubleToStringConvert());
    }


}
