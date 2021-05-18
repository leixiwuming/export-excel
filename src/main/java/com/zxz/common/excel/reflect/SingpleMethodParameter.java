package com.zxz.common.excel.reflect;

public class SingpleMethodParameter extends MethodParameter {
    private String methodName;

    private Class[] methodParamType;

    private Object[] methodParamValue;

    @Override
    public String getMethodName() {
        return null;
    }

    @Override
    public Class[] getMethodParamType() {
        return new Class[0];
    }

    @Override
    public Object[] getMethodParamValue() {
        return new Object[0];
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
