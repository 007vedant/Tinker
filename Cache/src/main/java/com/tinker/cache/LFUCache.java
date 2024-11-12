package com.tinker.cache;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;


/**
 * Implementation of Least Frequently Used Cache with O(1) runtime complexity for all operations.
 */
public class LFUCache<K, V> implements Cache<K, V> {
    private final int capacity;
    private final Map<K, ValueNode> map;
    private final FreqNode freqHead;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.freqHead = new FreqNode(0);
    }

    @Override
    public V get(K key) {
        if (map.containsKey(key)) {
            ValueNode valueNode = map.get(key);
            FreqNode oldFreq = valueNode.parent;
            FreqNode newFreq = oldFreq.next;

            // increasing freq of key, removing it from old freq node and adding to new freq node
            if (newFreq == freqHead || newFreq.frequency != oldFreq.frequency + 1) {
                newFreq = getNewNode(oldFreq.frequency + 1, oldFreq, oldFreq.next);
            }
            removeValueNode(valueNode);
            addValueNode(newFreq, valueNode);

            // if no keys with given frequency, delete the node
            if (oldFreq.head.next == oldFreq.tail) {
                deleteNode(oldFreq);
            }
            return valueNode.value;
        } else {
            throw new IllegalArgumentException("No such key is present in the cache.");
        }
    }

    @Override
    public void put(K key, V val) {
        if (map.containsKey(key)) {
            ValueNode valueNode = map.get(key);

            // updating value against the key
            valueNode.value = val;
            // to handle increment in frequency
            get(key);
            return;
        }

        if (map.size() == capacity) {
            FreqNode lowestFreq = freqHead.next;
            ValueNode lruNode = lowestFreq.head.next;
            map.remove(lruNode.key);
            removeValueNode(lruNode);

            if (lowestFreq.head.next == lowestFreq.tail) {
                deleteNode(lowestFreq);
            }
        }

        // adding to map if key is new, with freq = 1
        FreqNode freq = freqHead.next;
        if (freq == freqHead || freq.frequency != 1) {
            freq = getNewNode(1, freqHead, freq);
        }

        ValueNode newNode = new ValueNode(key, val);
        map.put(key, newNode);
        addValueNode(freq, newNode);
    }

    public K getLfuKey() {
        if (map.isEmpty()) {
            throw new RuntimeException("Cache is empty.");
        }

        // in case of tie, least recently used key will be returned.
        return freqHead.next.head.next.key;
    }

    private FreqNode getNewNode(int frequency, FreqNode previous, FreqNode next) {
        FreqNode newNode = new FreqNode(frequency);
        newNode.next = next;
        next.previous = newNode;
        newNode.previous = previous;
        previous.next = newNode;
        return newNode;
    }

    private void addValueNode(FreqNode freqNode, ValueNode valueNode) {
        ValueNode last = freqNode.tail.prev;
        last.next = valueNode;
        valueNode.prev = last;
        valueNode.next = freqNode.tail;
        freqNode.tail.prev = valueNode;
        valueNode.parent = freqNode;
    }

    private void deleteNode(FreqNode node) {
        node.previous.next = node.next;
        node.next.previous = node.previous;
    }

    private void removeValueNode(ValueNode valueNode) {
        valueNode.prev.next = valueNode.next;
        valueNode.next.prev = valueNode.prev;
    }

    class ValueNode {
        K key;
        V value;
        ValueNode prev;
        ValueNode next;
        FreqNode parent;  // Reference to its frequency node

        ValueNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private class FreqNode {
        int frequency;
        Set<K> keys;
        FreqNode next;
        FreqNode previous;
        ValueNode head;
        ValueNode tail;

        FreqNode(int frequency) {
            this.frequency = frequency;
            this.keys = new HashSet<>();
            this.previous = this;
            this.next = this;
            this.head = new ValueNode(null, null);
            this.tail = new ValueNode(null, null);
            this.head.next = tail;
            this.tail.prev = head;
        }
    }
}
