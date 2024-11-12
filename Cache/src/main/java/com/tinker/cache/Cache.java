package com.tinker.cache;

/**
 * Interface to model Cache.
 */
public interface Cache<K, V> {
    V get(K key);

    void put(K key, V val);
}
