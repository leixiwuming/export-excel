package com.zxz.common.excel.convert.read;

import com.zxz.common.excel.convert.Convert;
import org.apache.poi.ss.usermodel.DateUtil;

import java.time.LocalDate;
import java.util.TimeZone;

public class DoubleToLocalDateConvert implements Convert<Double,LocalDate> {


    @Override
    public LocalDate convert(Double aDouble) {
        return DateUtil.toLocalDateTime(DateUtil.getJavaDate(aDouble, TimeZone.getTimeZone("GMT+:08:00"))).toLocalDate();
    }
}
