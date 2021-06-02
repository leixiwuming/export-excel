package com.zxz.common.excel.convert;

import com.zxz.common.excel.reflect.ReflectStrategy;
import com.zxz.common.excel.util.ClassUtil;
import com.zxz.common.exception.BaseException;
import com.zxz.common.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 24447
 */
public abstract class BaseConvert {

    //转换器列表
    private List<Convert> converts;

    /**
     * 反射策略
     */
    private ReflectStrategy reflectStrategy;

    /**
     * 缓存，相同类型的属性在不同的导入导出接口中，有可能存在不同的转换器
     * 所以不用ComCache
     */
    private Map<String, Convert> convertCache;


    /**
     * 待转换的值为null的转换器
     *
     * @return
     */
    protected abstract Convert getNullConvert();

    public BaseConvert(ReflectStrategy reflectStrategy) {
        this.converts = new ArrayList<>();
        this.reflectStrategy = reflectStrategy;
        convertCache = new HashMap<>();
    }

    public List<Convert> getConverts() {
        return converts;
    }

    //添加转换器
    public void addConvert(Convert convert) {
        this.converts.add(convert);
    }

    //添加转换器
    public void addConvert(int index, Convert convert) {
        this.converts.add(index, convert);
    }

    //添加转换器
    public void addConvert(List<Convert> converts) {
        if (converts == null || converts.isEmpty()) {
            return;
        }
        this.converts.addAll(converts);
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

    /**
     * 获取vlue类型的第一个转换器，并把value转换
     *
     * @param value
     * @return
     */
    public Object getConvertValue(Object value) {
        return getConvert(value).convert(value);
    }

    /**
     * 获取把source转换成目标类型的转换器
     *
     * @param source
     * @param target
     * @return
     */
    private Convert getConvert(Object source, Class target) {
        Assert.notNull(target, "taget must not null");
        //如果原类型的值为null
        if (source != null) {
            //先获取缓存里的转换器
            Class sourceClass = source.getClass();
            Convert convert = convertCache.get(sourceClass.getSimpleName() + "&" + target.getSimpleName());
            if (convert != null) {
                return convert;
            }
            //缓存不存在
            //如果原类型值和目标类型一致，不做处理,如果时基础数据类型就转换成包装类型
            Class targetTmpClass = ClassUtil.getPackClass(target);

            if (sourceClass.equals(targetTmpClass)) {
                convertCache.put(sourceClass.getSimpleName() + "&" + target.getSimpleName(), Convert.emptyConvert());
                return Convert.emptyConvert();
            }
            List<Convert> converts = getConverts();

            //遍历转换器，找到目标转换器
            for (int i = converts.size() - 1; i >= 0; i--) {
                convert = converts.get(i);
                //获取接口泛型列表
                List<Class> genericClasses = reflectStrategy.getInterfaceGeneric(convert.getClass());
                if (genericClasses == null && genericClasses.size() != 2) {
                    continue;
                }
                //转换器可以转换的类型如果是source的超类
                if (genericClasses.get(0).isAssignableFrom(sourceClass)) {
                    //转换器转换后的类型如果是target的超类，说明这个转换器支持这个转换工作
                    if (genericClasses.get(1).isAssignableFrom(targetTmpClass)) {
                        convertCache.put(source.getClass().getSimpleName() + "&" + target.getSimpleName(), convert);
                        return convert;
                    }
                }

            }
            //未找到转换器错误
            throw new BaseException(String.format(" covert [%s to %s] not find",
                    sourceClass.getSimpleName(), target.getSimpleName()));
        } else {
            //查看缓存中有没有null的转换器
            Convert convert = convertCache.get(null);
            if (convert == null) {
                //获取null转换器,如果没设置，默认为emptyConvert
                convert = getNullConvert() == null ? Convert.emptyConvert() : getNullConvert();
                convertCache.put(null, convert);
            }
            return convert;
        }

    }


    /**
     * 获取可以把source进行转换的转换器，不管转成啥类型
     *
     * @param source
     * @return
     */
    public Convert getConvert(Object source) {
        if (source != null) {
            Class<?> sourceClass = source.getClass();
            //先获取缓存里的转换器
            Convert convert = convertCache.get(sourceClass.getSimpleName());
            if (convert != null) {
                return convert;
            }
            //缓存不存在，遍历转换器
            for (int i = converts.size() - 1; i >= 0; i--) {
                convert = converts.get(i);
                //获取接口泛型列表
                List<Class> genericClasses = reflectStrategy.getInterfaceGeneric(convert.getClass());
                if (genericClasses == null && !genericClasses.isEmpty()) {
                    continue;
                }
                //转换器可以转换的类型如果是source的超类
                if (genericClasses.get(0).isAssignableFrom(source.getClass())) {
                    convertCache.put(sourceClass.getSimpleName(), convert);
                    return convert;
                }
            }
            return Convert.emptyConvert();

        } else {
            //查看缓存中有没有null的转换器
            Convert convert = convertCache.get(source);
            if (convert == null) {
                //获取null转换器,如果没设置，默认为emptyConvert
                convert = getNullConvert() == null ? Convert.emptyConvert() : getNullConvert();
                convertCache.put(null, convert);

            }
            return convert;
        }


    }


}
