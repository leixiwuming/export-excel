package com.zxz.common.excel.convert.get;

import com.zxz.common.config.GlobalConfig;
import com.zxz.common.excel.convert.Convert;
import org.apache.poi.ss.usermodel.DateUtil;

import java.time.LocalDate;

public class DoubleToLocalDateConvert implements Convert<LocalDate, Double> {


    @Override
    public LocalDate convert(Double aDouble) {

        return DateUtil.toLocalDateTime(DateUtil.getJavaDate(aDouble, GlobalConfig.CHINA)).toLocalDate();
    }
}
