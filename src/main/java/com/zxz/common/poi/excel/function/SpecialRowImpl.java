package com.zxz.common.poi.excel.function;

import com.zxz.common.poi.excel.function.abs.BaseSpecialRow;

public class SpecialRowImpl<T> extends BaseSpecialRow<T> {

    @Override
    public void configSpecialRow() {
        getSpecialRows().add((sheet, rowIndex, nowData, nextData) -> rowIndex);
    }

}
