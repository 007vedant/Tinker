package com.tinker;

import com.tinker.cache.LRUCache;

public class MainCache {
    public static void main(String[] args) {
        LRUCache<Integer, String> cache = new LRUCache<>(3);

        cache.put(1, "first");
        cache.put(2, "second");
        cache.put(3, "third");

        System.out.println(cache.get(1)); // first
        System.out.println(cache.get(3)); // third
        System.out.println(cache.getLruKey()); // 2

        cache.put(4, "four");
        System.out.println(cache.getLruKey()); // 1

        try {
            cache.get(6);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }
}