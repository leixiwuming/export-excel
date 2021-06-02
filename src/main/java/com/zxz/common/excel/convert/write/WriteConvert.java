package com.zxz.common.excel.convert.write;

import com.zxz.common.excel.convert.BaseConvert;
import com.zxz.common.excel.convert.Convert;
import com.zxz.common.excel.convert.read.DoubleToStringConvert;
import com.zxz.common.excel.write.conf.WriteExcelConfig;

import java.util.List;

public class WriteConvert extends BaseConvert {


    public WriteConvert() {
        super(WriteExcelConfig.getReflectStrategy());
        List<Convert> converts = getConverts();
//        converts.add(new ObjectToStringConvert());
        converts.add(new LocalDataToStringConvert());
        converts.add(new LocalDataTimeToStringConvert());
        converts.add(new DataToStringConvert());
        converts.add(new DoubleToStringConvert());
    }


    /**
     * 待转换的值为null的转换器
     *
     * @return
     */
    @Override
    protected Convert getNullConvert() {
        return new NullConvert();
    }
}
