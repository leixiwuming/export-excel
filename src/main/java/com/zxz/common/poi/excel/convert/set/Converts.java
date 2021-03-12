package com.zxz.common.poi.excel.convert.set;

import com.zxz.common.poi.excel.convert.BaseConvert;
import com.zxz.common.poi.excel.convert.Convert;
import com.zxz.common.poi.excel.convert.get.DoubleToStringConvert;
import com.zxz.common.poi.excel.convert.set.*;

import java.util.List;

public class Converts<T, R> extends BaseConvert<T, R> {


    public Converts() {
        List<Convert<?, ?>> converts = getConverts();
        converts.add(new NullConvert());
        converts.add(new ObjectToStringConvert());
        converts.add(new LocalDataToStringConvert());
        converts.add(new LocalDataTimeToStringConvert());
        converts.add(new DataToStringConvert());
        converts.add(new DoubleToStringConvert());
    }


}
