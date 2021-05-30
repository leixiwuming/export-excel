package com.zxz.common.util;


import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zxc
 */
public class CollectionUtil {

    public static <T, E> Map<E, List<T>> groupingBy(List<T> data, Function<T, E> function) {
        return data.stream().filter(s -> function.apply(s) != null).collect(Collectors.groupingBy(function));
    }

    public static <T, E, G> Map<E, G> toMap(List<T> data, Function<T, E> key, Function<T, G> val) {
        return data.stream().filter(s -> val.apply(s) != null).collect(Collectors.toMap(key, val));
    }

    public static <T> Map<Integer, T> toMap(List<T> dtos) {
        Map<Integer, T> map = new HashMap<>();
        Assert.notEmpty(dtos, "集合不能为空");
        for (int i = 0; i < dtos.size(); i++) {
            map.put(i, dtos.get(i));
        }
        return map;
    }


    public static String join(String separator, Object[] data) {
        return join(separator, Arrays.asList(data));
    }

    static CharSequence toString(Object part) {
        Assert.notNull(part, "迭代器里的对象不能为null");
        return (part instanceof CharSequence) ? (CharSequence) part : part.toString();
    }

    public static String join(String separator, Iterable data) {
        Assert.notNull(separator, "separator not allow null");
        Assert.notNull(data, "data not allow null");
        StringBuilder builder = new StringBuilder();
        final Iterator iterator = data.iterator();
        if (iterator.hasNext()) {
            builder.append(toString(iterator.next()));
            while (iterator.hasNext()) {
                builder.append(separator);
                builder.append(toString(iterator.next()));
            }
        }
        return builder.toString();
    }
}
