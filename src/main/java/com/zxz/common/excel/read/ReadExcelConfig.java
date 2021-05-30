package com.zxz.common.excel.read;

import com.zxz.common.excel.convert.BaseConvert;
import com.zxz.common.excel.convert.get.ReadConverts;
import com.zxz.common.excel.reflect.ReflectStrategy;
import com.zxz.common.excel.reflect.jdk.JDKReflect;
import com.zxz.common.excel.util.InitThreadLocal;

public class ReadExcelConfig {
    //反射策略
    private static ThreadLocal<ReflectStrategy> reflectStrategyThreadLocal = new InitThreadLocal<>(new JDKReflect());

    public static void setReflectStrategy(ReflectStrategy reflectStrategy) {
        reflectStrategyThreadLocal.set(reflectStrategy);
    }

    public static ReflectStrategy getReflectStrategy() {
        return reflectStrategyThreadLocal.get();
    }

    //转换器
    private static ThreadLocal<BaseConvert> convertThreadLocal = new InitThreadLocal<>(new ReadConverts());

    public static void setConvertThreadLocal(BaseConvert baseConvert) {
        convertThreadLocal.set(baseConvert);
    }


    public static BaseConvert getConvertThreadLocal() {
        return convertThreadLocal.get();
    }

    //检查规则
//    private static ThreadLocal<BaseCheck> checkThreadLocal = new InitThreadLocal(new NormalCheckModel());
//
//    public static void setCheckThreadLocal(BaseCheck baseCheck) {
//        checkThreadLocal.set(baseCheck);
//    }
//
//    public static BaseCheck getCheckThreadLocal() {
//        return checkThreadLocal.get();
//    }

    public static void remove() {
        reflectStrategyThreadLocal.remove();
        convertThreadLocal.remove();
//        checkThreadLocal.remove();
    }

}
