package com.zxz.common.poi.excel.util;

import com.zxz.common.exception.BaseException;
import com.zxz.common.util.StringUtils;

public class ClassUtil {

    public static Class getPackClass(Class baseClass) {
        if (baseClass.isPrimitive()) {
            if (baseClass.equals(int.class)) {
                return Integer.class;
            } else if (baseClass.equals(char.class)) {
                return Character.class;
            } else {
                try {
                    return Class.forName("java.lang." +
                            StringUtils.firstToUp(baseClass.getSimpleName()), false, Thread
                            .currentThread().getContextClassLoader());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    throw new BaseException(baseClass.getSimpleName() + "加载包装类失败");
                }
            }
        } else {
            return baseClass;
        }
    }
}
