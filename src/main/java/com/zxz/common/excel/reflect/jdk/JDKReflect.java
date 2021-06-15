package com.zxz.common.excel.reflect.jdk;

import com.zxz.common.excel.ExcelConfig;
import com.zxz.common.excel.annotation.AnnotationType;
import com.zxz.common.excel.annotation.Mapping;
import com.zxz.common.excel.annotation.adapter.AnnotationAdapter;
import com.zxz.common.excel.cache.CaffeineCache;
import com.zxz.common.excel.cache.ComCache;
import com.zxz.common.excel.model.AnnotationMeta;
import com.zxz.common.excel.reflect.MethodParameter;
import com.zxz.common.excel.reflect.ReflectStrategy;
import com.zxz.common.exception.BaseException;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JDKReflect implements ReflectStrategy {
    private static final String JDKREFLECT_SPACE = "JDKReflect";
    private ComCache comCache;

    public JDKReflect() {
        this.comCache = CaffeineCache.getInstance();
    }

    public void setComCache(ComCache comCache) {
        this.comCache = comCache;
    }

    @Override
    public List<Field> getAllField(Class targetClass) {
        String s = "field" + targetClass.getName();
        List<Field> fields = (List<Field>) comCache.get(JDKREFLECT_SPACE, s);
        if (fields != null) {
            return fields;
        }
        fields = new ArrayList<>();
        while (!targetClass.equals(Object.class)) {
            fields.addAll(Arrays.asList(targetClass.getDeclaredFields()));
            targetClass = targetClass.getSuperclass();
        }
        comCache.put(JDKREFLECT_SPACE, s, fields);
        return fields;
    }


    @Override
    public List<AnnotationMeta> getAnnotationMetas(Class targetClass) {
        String s = "meta" + targetClass.getName();
        List<AnnotationMeta> annotations = (List<AnnotationMeta>) comCache.get(JDKREFLECT_SPACE, s);
        if (annotations != null) {
            return annotations;
        }
        annotations = new ArrayList<>();
        Class type = getAnnotationType(targetClass);
        AnnotationAdapter annotationAdapter = getAnnotationAdapter(type);
        List<Field> allField = getAllField(targetClass);
        if (allField == null || allField.isEmpty()) {
            return new ArrayList<>();
        }
        for (Field field : allField) {
            Annotation annotation = field.getAnnotation(type);
            if (annotation == null) {
                continue;
            }
            annotations.add(annotationAdapter.getHeadAnnotation(field.getName(), annotation));
        }
        comCache.put(JDKREFLECT_SPACE, s, annotations);
        return annotations;
    }


    @NotNull
    private <T extends Annotation> Class<T> getAnnotationType(Class targetClass) {
        String s = "type" + targetClass.getName();
        Class<T> type = (Class<T>) comCache.get(JDKREFLECT_SPACE, s);
        if (type != null) {
            return type;
        }
        AnnotationType typeAnnotation = (AnnotationType) targetClass.getAnnotation(AnnotationType.class);
        if (typeAnnotation == null) {
            type = (Class<T>) Mapping.class;
        } else {
            type = typeAnnotation.value();
        }
        comCache.put(JDKREFLECT_SPACE, s, type);
        return type;
    }

    @Override
    public Object invoke(Object instance, MethodParameter methodParameter) {
        Class<?> aClass = instance.getClass();
        Method method;
        try {
            method = aClass.getMethod(methodParameter.getMethodName(), methodParameter.getMethodParamType());
        } catch (NoSuchMethodException e) {
            throw new BaseException("未找到" + methodParameter.getMethodName() + "方法");
        }
        try {
            return method.invoke(instance, methodParameter.getMethodParamValue());
        } catch (IllegalAccessException e) {
            throw new BaseException("执行方法出错");
        } catch (InvocationTargetException e) {
            throw new BaseException("执行方法出错");
        }
    }

    @Override
    public <T> T newInstance(Class<T> targetClass) {
        try {
            return targetClass.newInstance();
        } catch (Exception e) {
            throw new BaseException("实例化失败");
        }
    }

    @Override
    public Class getFieldType(Class targetClass, String fieldName) {
        String s = "fileType" + targetClass.getName() + fieldName;
        Class typeClass = (Class) comCache.get(JDKREFLECT_SPACE, s);
        if (typeClass != null) {
            return typeClass;
        }
        typeClass = getField(targetClass, fieldName).getType();
        comCache.put(JDKREFLECT_SPACE, s, typeClass);
        return typeClass;
    }

    /**
     * 获取目标类超类接口的泛型
     *
     * @param targetClass
     * @return
     */
    @Override
    public List<Class> getInterfaceGeneric(Class targetClass) {
        String s = "interfaceGeneric" + targetClass.getName();
        List<Class> list = (List<Class>) comCache.get(JDKREFLECT_SPACE, s);
        if (list != null) {
            return list;
        }
        Type genericInterface = targetClass.getGenericInterfaces()[0];
        if (genericInterface instanceof ParameterizedType) {
            list = Arrays.asList(typeArrToClass(((ParameterizedType) genericInterface).getActualTypeArguments()));
            comCache.put(JDKREFLECT_SPACE, s, list);
            return list;
        }
        return new ArrayList<>();
    }

    protected Class[] typeArrToClass(Type[] types) {
        Class[] classes = new Class[types.length];
        for (int i = 0; i < types.length; i++) {
            classes[i] = (Class) types[i];
        }
        return classes;
    }

    /**
     * 获取目标类父类的泛型
     *
     * @param targetClass
     * @return
     */
    @Override
    public List<Class> getSuperclassGeneric(Class targetClass) {
        String s = "superclassGeneric" + targetClass.getName();
        List<Class> list = (List<Class>) comCache.get(JDKREFLECT_SPACE, s);
        if (list != null) {
            return list;
        }
        Type genericSuperclass = targetClass.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            list = Arrays.asList(typeArrToClass(((ParameterizedType) genericSuperclass).getActualTypeArguments()));
            comCache.put(JDKREFLECT_SPACE, s, list);
            return list;
        }
        return new ArrayList<>();
    }

    private Field getField(Class targetClass, String filedName) {
        Field field = null;
        try {
            //反射获取属性
            field = targetClass.getDeclaredField(filedName);
        } catch (NoSuchFieldException e) {
            throw new BaseException("未找到该属性： " + filedName);
        }
        return field;
    }
}
