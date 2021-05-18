package com.zxz.common.excel.convert.get;

import com.zxz.common.config.GlobalConfig;
import com.zxz.common.excel.convert.Convert;
import org.apache.poi.ss.usermodel.DateUtil;

import java.time.LocalDateTime;

public class DoubleToLocalDateTimeConvert implements Convert<LocalDateTime, Double> {
    @Override
    public LocalDateTime convert(Double aDouble) {

        return DateUtil.toLocalDateTime(DateUtil.getJavaDate(aDouble, GlobalConfig.CHINA));
    }
}
