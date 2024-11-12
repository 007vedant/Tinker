package com.tinker.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of Least Recently Used Cache.
 */
public class LRUCache<K, V> implements Cache<K, V> {
    private final Node head = new Node(null, null);
    private final Node tail = new Node(null, null);
    private final int capacity;
    private final Map<K, Node> map = new HashMap<>();

    public LRUCache(int capacity) {
        this.capacity = capacity;
        head.next = tail;
        tail.previous = head;
    }

    public V get(K key) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            remove(node);
            add(node);
            return node.val;
        } else {
            throw new IllegalArgumentException("No such key is present in the cache.");
        }
    }

    public void put(K key, V val) {
        if (map.containsKey(key)) {
            remove(map.get(key));
        }
        if (map.size() == capacity) {
            remove(tail.previous);
        }
        add(new Node(key, val));
    }

    public K getLruKey() {
        return tail.previous.key;
    }

    private void remove(Node node) {
        map.remove(node.key);
        node.previous.next = node.next;
        node.next.previous = node.previous;
    }

    private void add(Node node) {
        map.put(node.key, node);
        Node next = head.next;
        head.next = node;
        node.previous = head;
        next.previous = node;
        node.next = next;

    }

    private class Node {
        K key;
        V val;
        Node previous;
        Node next;

        Node(K key, V val) {
            this.key = key;
            this.val = val;
        }

    }
}
