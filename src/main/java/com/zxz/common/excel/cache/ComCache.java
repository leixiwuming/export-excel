package com.zxz.common.excel.cache;


import java.util.function.Function;

public abstract class ComCache<K, V> {

    public abstract V get(Object space, K key);

    public abstract V get(Object space, K key, Function function);

    public abstract void put(Object space, K key, V val);
}
