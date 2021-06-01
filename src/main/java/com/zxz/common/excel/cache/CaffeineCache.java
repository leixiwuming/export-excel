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
    public V get(Object space, K key) {
        Cache cache = spaceCache.get(space);
        if (cache == null) {
            cache = build();
            spaceCache.put(space, cache);
            return null;
        }
        return (V) cache.getIfPresent(key);
    }

    @Override
    public V get(Object space, K key, Function function) {
        Cache cache = spaceCache.get(space);
        if (cache == null) {
            cache = build();
            spaceCache.put(space, cache);
            return null;
        }
        return (V) cache.get(key, function);
    }

    @Override
    public void put(Object space, K key, V val) {
        Cache cache = spaceCache.get(space);
        if (cache == null) {
            cache = build();
            spaceCache.put(space, cache);
        }
        cache.put(key, val);
    }

    public static ComCache getInstance() {
        if (caffeineCache == null) {
            synchronized (caffeineCache) {
                if (caffeineCache == null) {
                    return new CaffeineCache();
                }
            }
        }
        return caffeineCache;
    }
}
