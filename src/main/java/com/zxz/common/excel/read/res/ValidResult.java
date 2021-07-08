package com.zxz.common.excel.read.res;

import java.util.HashMap;
import java.util.Map;

public class ValidResult<T> extends Result<T>{
    private boolean isError;
    private Map<String, String> errorMsg;
    private T data;

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public Map<String, String> getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(Map<String, String> errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ValidResult() {
        errorMsg = new HashMap<>();
    }
}
