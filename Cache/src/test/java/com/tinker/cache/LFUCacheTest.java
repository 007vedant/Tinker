package com.tinker.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Class to test {@link com.tinker.cache.LRUCache}.
 */
public class LFUCacheTest {
    private LFUCache<Integer, String> cache;

    @BeforeEach
    public void setUp() {
        cache = new LFUCache<>(3);
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
    void testGetUpdatesLFUOrder() {
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");

        // making "one" and "three" most frequently used
        assertEquals("one", cache.get(1));
        assertEquals("three", cache.get(3));

        // "two" is least frequently used
        cache.put(4, "four");
        assertThrows(IllegalArgumentException.class, () -> cache.get(2));
    }

    @Test
    void testEvictionWhenCapacityIsExceeded() {
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");

        // all keys have same frequency with "one" being least recently used
        cache.put(4, "four");

        assertThrows(IllegalArgumentException.class, () -> cache.get(1));
        assertEquals("two", cache.get(2));
        assertEquals("three", cache.get(3));
        assertEquals("four", cache.get(4));
    }

    @Test
    void testGetLfuKey() {
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");

        // all have same frequency with "one" being least recently used
        assertEquals(1, cache.getLfuKey());

        // Access key "one", with "two" being least recently used
        cache.get(1);
        assertEquals(2, cache.getLfuKey());

        // Add another element, which should evict key "two"
        cache.put(4, "four");
        assertEquals(3, cache.getLfuKey());
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
