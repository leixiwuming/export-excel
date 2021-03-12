package com.zxz.common.poi.excel.convert;

import com.zxz.common.exception.BaseException;
import com.zxz.common.poi.excel.convert.set.NullConvert;
import com.zxz.common.poi.excel.convert.set.ToNullConvert;
import com.zxz.common.poi.excel.util.ClassUtil;
import com.zxz.common.util.Assert;
import org.apache.poi.ss.usermodel.Cell;

import java.lang.reflect.ParameterizedType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author 24447
 */
public abstract class BaseConvert<T, R> {
    private List<Convert<?, ?>> converts;

    private Map<String, Convert<?, ?>> convertMap;

    public List<Convert<?, ?>> getConverts() {
        return converts;
    }

    public void addConvert(Convert<T, R> convert) {
        this.converts.add(convert);
    }

    public void addConvert(List<Convert<T, R>> converts) {
        if (converts == null || converts.isEmpty()) {
            return;
        }
        this.converts.addAll(converts);
    }

    public BaseConvert() {
        this.converts = new ArrayList<>();
        this.convertMap = new HashMap<>();
    }

    public Map<String, Convert<?, ?>> getConvertMap() {
        return convertMap;
    }

//    public <E> void genFieldConvert(Class<E> entityClass, boolean useSequence, Sequence sequence) {
//        Field[] declaredFields = entityClass.getDeclaredFields();
//        if (fieldConvert == null) {
//            if (useSequence) {
//                fieldConvert = new HashMap<>(declaredFields.length + 1);
//                setConvertByIntype(sequence.testVal().getClass());
//            } else {
//                fieldConvert = new HashMap<>(declaredFields.length);
//            }
//        }
//        for (Field declaredField : declaredFields) {
//            Class<?> type = declaredField.getType();
//            setConvertByIntype(type);
//        }
//    }

    private void setConvertByIntype(Class<?> type) {
        if (type == null) {
            convertMap.put(null, new NullConvert());
            return;
        }
        for (int i = converts.size() - 1; i >= 0; i--) {
            Convert<?, ?> convert = converts.get(i);
            Class covertClass = (Class) ((ParameterizedType) convert.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[1];
            /* Type[] genericInterfaces = convert.getClass().getGenericInterfaces(); */
            if (type.isPrimitive()) {
                type = ClassUtil.getPackClass(type);
            }
            if (covertClass.isAssignableFrom(type)) {
                convertMap.put(type.getSimpleName(), convert);
                break;
            }

        }
    }

    public Convert getConvert(Class type) {
        Convert convert = convertMap.get(type != null ? type.getSimpleName() : null);
        if (convert == null) {
            setConvertByIntype(type);
            convert = convertMap.get(type != null ? type.getSimpleName() : null);
        }
        notNullCovEx(convert, type, String.class);
        return convert;
    }

    public Convert getConvert(Object source, Class taget) {
        Assert.notNull(taget, "taget must not null");
        if (source == null) {
            Convert<?, ?> convert = convertMap.get(null);
            if (convert == null) {
                convertMap.put(null, new ToNullConvert());
            }
            return convertMap.get(null);
        }
        Convert convert = convertMap.get(source.getClass().getSimpleName() + "&" + taget.getSimpleName());
        if (convert == null) {
            setConvert(source, taget);
            convert = convertMap.get(source.getClass().getSimpleName() + "&" + taget.getSimpleName());
        }
        notNullCovEx(convert, source.getClass(), taget);
        return convert;
    }

    void setConvert(Object source, Class taget) {
        Class sourceClass = null;
        Class tagetClass = null;
        for (int i = converts.size() - 1; i >= 0; i--) {
            Convert<?, ?> convert = converts.get(i);
            Class covertClass = (Class) ((ParameterizedType) convert.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[1];
            /* Type[] genericInterfaces = convert.getClass().getGenericInterfaces(); */
            if (sourceClass == null) {
                if (source.getClass().isPrimitive()) {
                    sourceClass = ClassUtil.getPackClass(source.getClass());
                } else {
                    sourceClass = source.getClass();
                }
            }
            //入参类型相同
            if (covertClass.isAssignableFrom(sourceClass)) {
                //返回值相同
                covertClass = (Class) ((ParameterizedType) convert.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
                if (tagetClass == null) {
                    if (taget.isPrimitive()) {
                        tagetClass = ClassUtil.getPackClass(taget);
                    } else {
                        tagetClass = taget;
                    }
                }
                if (covertClass.isAssignableFrom(taget)) {
                    convertMap.put(source.getClass().getSimpleName() + "&" + taget.getSimpleName(), convert);
                    break;
                }

            }

        }

    }

    public Object getConvertValue(Object value) {
        if (value == null) {
            return getConvert(null).convert(value);
        }
        return getConvert(value.getClass()).convert(value);
    }

    private void notNullCovEx(Convert o, Class source, Class taget) {
        if (!notNull(o)) {
            throw new BaseException(String.format(" covert [%s to %s] not find",
                    source.getSimpleName(), taget.getSimpleName()));
        }
    }

    private boolean notNull(Object o) {
        return o != null;
    }

    public void convertToCell(Object value, Cell cell) {
        Assert.notNull(value, "value is null");
        if (value instanceof String) {
            cell.setCellValue((String) value);
        }
        if (value instanceof Date) {
            cell.setCellValue((Date) value);
        }
        if (value instanceof LocalDateTime) {
            cell.setCellValue((LocalDateTime) value);
        }
        if (value instanceof LocalDate) {
            cell.setCellValue((LocalDate) value);
        } else {
            cell.setCellValue(String.valueOf(value));
        }
    }

}
