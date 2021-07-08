package com.zxz.common.excel.read.res;

import java.util.ArrayList;
import java.util.List;

public class ReadResult<T> {
    private List<T> successData;
    private List<ErrorResult<T>> errorData;

    public ReadResult() {
        successData = new ArrayList<>();
        errorData = new ArrayList<>();
    }

    public void addSuccessData(T t) {
        successData.add(t);
    }

    public void addErrorData(ErrorResult<T> t) {
        errorData.add(t);
    }
}
