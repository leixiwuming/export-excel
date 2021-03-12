package com.zxz.common.poi.excel.read.enums;

import com.zxz.common.poi.excel.abs.BaseCheck;
import com.zxz.common.poi.excel.read.impl.NormalCheckModel;
import com.zxz.common.poi.excel.read.impl.StrictCheckModel;

/**
 * @author 24447
 */
public enum  CheckModel {
    STRICT(new StrictCheckModel()),
    NORMAL(new NormalCheckModel());
    private BaseCheck baseCheck;

    public BaseCheck getBaseCheck() {
        return baseCheck;
    }

    public CheckModel setBaseCheck(BaseCheck baseCheck) {
        this.baseCheck = baseCheck;
        return this;
    }

    CheckModel(BaseCheck baseCheck) {
        this.baseCheck = baseCheck;
    }
}
