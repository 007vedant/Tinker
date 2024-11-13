package com.tinker.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Class to test {@link com.tinker.cache.LRUCache}.
 */
public class LRUCacheTest {
    private LRUCache<Integer, String> cache;

    @BeforeEach
    public void setUp() {
        cache = new LRUCache<>(3);
    }

    @Test
    void testPutAndGet() {
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");

        assertEquals("one", cache.get(1));
        assertEquals("two", cache.get(2));
        assertEquals("three", cache.get(3));
    }

    @Test
    void testGetUpdatesLRUOrder() {
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");

        // making "one" most recently used
        assertEquals("one", cache.get(1));

        // "two" is least recently used
        cache.put(4, "four");
        assertThrows(IllegalArgumentException.class, () -> cache.get(2));
    }

    @Test
    void testEvictionWhenCapacityIsExceeded() {
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");

        // "one" is least recently used
        cache.put(4, "four");

        assertThrows(IllegalArgumentException.class, () -> cache.get(1));
        assertEquals("two", cache.get(2));
        assertEquals("three", cache.get(3));
        assertEquals("four", cache.get(4));
    }

    @Test
    void testGetLruKey() {
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");

        // The least recently used should be "one"
        assertEquals(1, cache.getLruKey());

        // Access key "one", now "two" should be the least recently used
        cache.get(1);
        assertEquals(2, cache.getLruKey());

        // Add another element, which should evict key "two"
        cache.put(4, "four");
        assertEquals(3, cache.getLruKey());
    }

    @Test
    void testPutReplacesExistingKey() {
        cache.put(1, "one");
        cache.put(1, "ONE");

        assertEquals("ONE", cache.get(1));
    }

    @Test
    void testGetThrowsExceptionForMissingKey() {
        cache.put(1, "one");
        assertThrows(IllegalArgumentException.class, () -> cache.get(2));
    }
}
