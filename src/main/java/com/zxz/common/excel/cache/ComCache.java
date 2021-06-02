package com.zxz.common.excel.cache;


import java.util.function.Function;

public abstract class ComCache<K, V> {

    public abstract V get(String space, K key);

    public abstract V get(String space, K key, Function<K,Object> function);

    public abstract void put(String space, K key, V val);
}
