package com.zxz.common.util;

import com.zxz.common.exception.BaseException;

import java.util.Collection;

/**
 * @author 24447
 */
public class Assert {
    public static void notNull(Object o, String msg) {
        if (o == null) {
            throw new BaseException(msg);
        }
    }

    public static void notNull(Object o, String msg, int code) {
        if (o == null) {
            throw new BaseException(msg, code);
        }
    }

    public static void notEmpty(Collection collection, String msg) {
        if (collection == null || collection.isEmpty()) {
            throw new BaseException(msg);
        }
    }

    public static void notEmpty(Collection collection, BaseException e) {
        if (collection == null || collection.isEmpty()) {
            throw e;
        }
    }

    public static void notEmpty(Collection collection, String msg, int code) {
        if (collection == null || collection.isEmpty()) {
            throw new BaseException(msg, code);
        }
    }

    public static void state(boolean expression, String message) {
        if (!expression) {
            throw new BaseException(message);
        }
    }
    public static void state(boolean expression, String message,int code) {
        if (!expression) {
            throw new BaseException(message,code);
        }
    }
}
