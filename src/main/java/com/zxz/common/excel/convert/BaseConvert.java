package com.zxz.common.excel.convert;

import com.zxz.common.excel.convert.set.ToNullConvert;
import com.zxz.common.exception.BaseException;
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
    //转换器列表
    private List<Convert<?, ?>> converts;

    //缓存
    private Map<String, Convert<?, ?>> convertMap;

    public List<Convert<?, ?>> getConverts() {
        return converts;
    }

    //添加转换器
    public void addConvert(Convert<T, R> convert) {
        this.converts.add(convert);
    }

    //添加转换器
    public void addConvert(int index, Convert<T, R> convert) {
        this.converts.add(index, convert);
    }

    //添加转换器
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


    /**
     * target
     * 获取转换器
     *
     * @param source
     * @param target
     * @return
     */
    public Convert getConvert(Object source, Class target) {
        Assert.notNull(target, "taget must not null");
        //如果source为null，返回ToNullConvert
        if (source == null) {
            Convert<?, ?> convert = convertMap.get(null);
            if (convert == null) {
                convertMap.put(null, new ToNullConvert());
            }
            return convertMap.get(null);
        }
        //缓存获取转换器
        Convert convert = convertMap.get(source.getClass().getSimpleName() + "&" + target.getSimpleName());
        if (convert == null) {
            //缓存不存在，通过泛型获取转换器并设置缓存
            setConvert(source, target);
            convert = convertMap.get(source.getClass().getSimpleName() + "&" + target.getSimpleName());
        }
        notNullCovEx(convert, source.getClass(), target);
        return convert;
    }

    /**
     * 把source转换成target类型
     *
     * @param source
     * @param target
     * @return
     */
    public Object getConvertValue(Object source, Class target) {
        return getConvert(source, target).convert(source);
    }

    private Convert setConvert(Object source, Class taget) {
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
                    return convert;
                }

            }
        }
        throw new BaseException(String.format(" covert [%s to %s] not find",
                sourceClass.getSimpleName(), taget.getSimpleName()));
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
