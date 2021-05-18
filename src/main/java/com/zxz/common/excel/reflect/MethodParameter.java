package com.zxz.common.excel.reflect;

public abstract class MethodParameter {
    /**
     * 方法名称
     * @return
     */
    public abstract String getMethodName();

    /**
     * 参数类型
     * @return
     */
    public abstract Class[] getMethodParamType();

    /**
     * 参数值
     * @return
     */
    public abstract Object[] getMethodParamValue();
}
