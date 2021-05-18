package com.zxz.common.poi.excel.util;

import com.zxz.common.excel.annotation.Mapping;
import com.zxz.common.poi.excel.exception.AnnotationNotExitException;
import com.zxz.common.poi.excel.usermodel.AnnotationMeta;
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

    public static List<AnnotationMeta> getHeadClassAnnotation(Class headClass) {
        List<AnnotationMeta> classMappings = new ArrayList<>();
        while (!headClass.equals(Object.class)) {
            Field[] declaredFields = headClass.getDeclaredFields();
            FieldToMapping(declaredFields, classMappings);
            headClass = headClass.getSuperclass();
        }
        Assert.notEmpty(classMappings, new AnnotationNotExitException("未找到注解"));
        classMappings.sort((o1, o2) -> o1.getSort() - o2.getSort());
        return classMappings;
    }


    private static void FieldToMapping(Field[] declaredFields, List<AnnotationMeta> classMappings) {
        for (Field declaredField : declaredFields) {
            Mapping annotation = declaredField.getAnnotation(Mapping.class);
            if (annotation != null && annotation.status()) {
                classMappings.add(mapToMate(annotation, declaredField));
            }
        }
    }

    private static AnnotationMeta mapToMate(Mapping mapping, Field tagField) {
        AnnotationMeta annotationMeta = new AnnotationMeta();
        InvocationHandler h = Proxy.getInvocationHandler(mapping);
        Map memberValues = null;
        try {
            Field hField = h.getClass().getDeclaredField("memberValues");
            hField.setAccessible(true);
            memberValues = (Map) hField.get(h);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Assert.notNull(memberValues, "error");
        // 获取 memberValues
        Field[] declaredFields = annotationMeta.getClass().getDeclaredFields();

        try {
            //将注解属性复制到对象
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                declaredField.set(annotationMeta, memberValues.get(declaredField.getName()));
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (!isNotBlank(annotationMeta.getValue())) {
            annotationMeta.setValue(tagField.getName());
        }
        annotationMeta.setSequence(false);
        annotationMeta.setFieldName(tagField.getName());
        annotationMeta.setGetMethodName(GET + StringUtils.firstToUp(tagField.getName()));
        annotationMeta.setSetMethodName(SET + StringUtils.firstToUp(tagField.getName()));
//        memberValues.put("setMethodName", SET + StringUtils.firstToUp(tagField.getName()));
        return annotationMeta;
    }
}
