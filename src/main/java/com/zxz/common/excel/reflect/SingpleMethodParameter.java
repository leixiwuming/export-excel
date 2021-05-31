package com.zxz.common.excel.reflect;

public class SingpleMethodParameter extends MethodParameter {
    private String methodName;

    private Class[] methodParamType;

    private Object[] methodParamValue;

    @Override
    public String getMethodName() {
        return methodName;
    }

    @Override
    public Class[] getMethodParamType() {
        return methodParamType;
    }

    @Override
    public Object[] getMethodParamValue() {
        return methodParamValue;
    }

    public void setMethodParamType(Class... methodParamType) {
        this.methodParamType = methodParamType;
    }

    public void setMethodParamValue(Object... methodParamValue) {
        this.methodParamValue = methodParamValue;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public SingpleMethodParameter(String methodName) {
        this.methodName = methodName;
    }
}
