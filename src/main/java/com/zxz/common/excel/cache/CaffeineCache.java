package com.zxz.common.excel.cache;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CaffeineCache<K, V> extends ComCache<K, V> {
    private volatile static CaffeineCache caffeineCache;

    private CaffeineCache() {

    }

    private Map<Object, Cache> spaceCache = new HashMap<>();

    protected Cache build() {
        Cache build = Caffeine.newBuilder().maximumSize(100).build();
        return build;
    }

    @Override
    public V get(String space, K key) {
        Cache cache = spaceCache.get(space);
        if (cache == null) {
            cache = build();
            spaceCache.put(space, cache);
            return null;
        }
        return (V) cache.getIfPresent(key);
    }

    @Override
    public V get(String space, K key, Function<K, Object> function) {
        Cache cache = spaceCache.get(space);
        if (cache == null) {
            cache = build();
            spaceCache.put(space, cache);
        }
        return (V) cache.get(key, function);
    }

    @Override
    public void put(String space, K key, V val) {
        Cache cache = spaceCache.get(space);
        if (cache == null) {
            cache = build();
            spaceCache.put(space, cache);
        }
        if (val != null) {
            cache.put(key, val);
        }
    }

    public static ComCache getInstance() {
        if (caffeineCache == null) {
            synchronized (CaffeineCache.class) {
                if (caffeineCache == null) {
                    return new CaffeineCache();
                }
            }
        }
        return caffeineCache;
    }
}
