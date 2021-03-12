package com.zxz.common.poi.excel.util;

import com.zxz.common.exception.BaseException;
import com.zxz.common.poi.excel.annotation.Mapping;
import com.zxz.common.poi.excel.exception.AnnotationNotExitException;
import com.zxz.common.util.Assert;
import com.zxz.common.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.zxz.common.util.StringUtils.isNotBlank;

public class AnnotationUtil {
    public static final String GET = "get";
    public static final String SET = "set";

    public static List<Mapping> getHeadClassAnnotation(Class headClass) {
        List<Mapping> classMappings = new ArrayList<>();
        while (!headClass.equals(Object.class)) {
            Field[] declaredFields = headClass.getDeclaredFields();
            try {
                FieldToMapping(declaredFields, classMappings);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new BaseException("修改注解值失败");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                throw new BaseException("修改注解值失败");
            }
            headClass = headClass.getSuperclass();
        }
        Assert.notEmpty(classMappings, new AnnotationNotExitException("未找到注解"));
        classMappings.sort((o1, o2) -> o1.sort() - o2.sort());
        return classMappings;
    }

    private static void FieldToMapping(Field[] declaredFields, List<Mapping> classMappings) throws NoSuchFieldException, IllegalAccessException {
        for (Field declaredField : declaredFields) {
            Mapping annotation = declaredField.getAnnotation(Mapping.class);
            if (annotation != null && annotation.status()) {
                String value = annotation.value();
                InvocationHandler h = Proxy.getInvocationHandler(annotation);
                Field hField = h.getClass().getDeclaredField("memberValues");
                hField.setAccessible(true);
                // 获取 memberValues
                Map memberValues = (Map) hField.get(h);
                memberValues.put("fieldName", declaredField.getName());
                memberValues.put("getMethodName", GET + StringUtils.firstToUp(declaredField.getName()));
                memberValues.put("setMethodName", SET + StringUtils.firstToUp(declaredField.getName()));
                if (!isNotBlank(value)) {
                    // 修改 value 属性值
                    memberValues.put("value", declaredField.getName());
                }
                classMappings.add(annotation);
            }
        }
    }
}
