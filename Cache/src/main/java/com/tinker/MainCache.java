package com.tinker;

import com.tinker.cache.LFUCache;
import com.tinker.cache.LRUCache;

public class MainCache {
    public static void main(String[] args) {
        LRUCache<Integer, String> lruCache = new LRUCache<>(3);

        lruCache.put(1, "first");
        lruCache.put(2, "second");
        lruCache.put(3, "third");

        System.out.println(lruCache.get(1)); // first
        System.out.println(lruCache.get(3)); // third
        System.out.println(lruCache.getLruKey()); // 2

        lruCache.put(4, "four");
        System.out.println(lruCache.getLruKey()); // 1
        lruCache.put(4, "Four");
        System.out.println(lruCache.get(4));
        System.out.println(lruCache.getLruKey());

        try {
            lruCache.get(6);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        LFUCache<Integer, String> lfuCache = new LFUCache<>(3);

        lfuCache.put(1, "first");
        lfuCache.put(2, "second");
        lfuCache.put(3, "third");

        System.out.println(lfuCache.get(3)); // third
        System.out.println(lfuCache.get(1)); // first
        System.out.println(lfuCache.getLfuKey()); // 2

        lfuCache.put(4, "four");
        System.out.println(lfuCache.getLfuKey()); // 4
        lfuCache.put(4, "Four");
        System.out.println(lfuCache.get(4));
        System.out.println(lfuCache.getLfuKey()); // 1 or 3 or 4

        try {
            lfuCache.get(6);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }
}