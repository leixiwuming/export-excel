package com.zxz.common.poi.excel.util;

import java.util.HashMap;

public class GreaterMap<K> extends HashMap<K, Integer> {
    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old
     * value is replaced.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with <tt>key</tt>, or
     * <tt>null</tt> if there was no mapping for <tt>key</tt>.
     * (A <tt>null</tt> return can also indicate that the map
     * previously associated <tt>null</tt> with <tt>key</tt>.)
     */
    @Override
    public Integer put(K key, Integer value) {
        Integer oldValue = this.get(key);
        if (oldValue != null && oldValue >= value) {
            return oldValue;
        }
        return super.put(key, value);
    }
}
