package com.tinker.consistentHashing.core;

import com.tinker.consistentHashing.base.StorageNode;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.tinker.consistentHashing.utils.HelperUtils.hashFunction;
import static com.tinker.consistentHashing.utils.HelperUtils.bisect;

/**
 * Represents array based implementation of consistent hashing algorithm.
 */
public class ConsistentHash {
    private int totalSlots;
    private List<BigInteger> keys;
    private List<StorageNode> nodes;

    public ConsistentHash(int slots) {
        this.keys = new ArrayList<>();
        this.nodes = new ArrayList<>();
        this.totalSlots = slots;
    }

    public List<BigInteger> getKeys() {
        return this.keys;
    }

    public List<StorageNode> getNodes() {
        return this.nodes;
    }

    /**
     * Adds new node to the system/ring and returns the key from the hash space where the node was placed.
     *
     * @param node storage node
     * @return key where node is placed.
     * @throws Exception if either hash space is full or collision occurs while adding node
     */
    public BigInteger addNode(StorageNode node) throws Exception {
        if (this.keys.size() == this.totalSlots) {
            throw new Exception("Hash space is full!");
        }
        BigInteger key = hashFunction(node.getHost(), this.totalSlots);
        int index = bisect(this.keys, key, "right");

        if (index > 0 && Objects.equals(this.keys.get(index - 1), key)) {
            throw new Exception("Collision occurred while adding node!");
        }
        this.keys.add(index, key);
        this.nodes.add(index, node);

        return key;
    }

    /**
     * Removes node from the system/ring and returns the key where the node was placed.
     *
     * @param node storage node
     * @return key where node was placed
     * @throws Exception if either hash space is empty or node is absent
     */
    public BigInteger removeNode(StorageNode node) throws Exception {
        if (this.keys.size() == 0) {
            throw new Exception("Hash space is empty!");
        }
        BigInteger key = hashFunction(node.getHost(), this.totalSlots);
        int index = bisect(this.keys, key, "left");

        if (index >= this.keys.size() || !Objects.equals(this.keys.get(index), key)) {
            throw new Exception("Node does not exist!");
        }
        this.keys.remove(index);
        this.nodes.remove(index);

        return key;
    }

    /**
     * Returns the node at which the input file/resource would be saved.
     *
     * @param file input file
     * @return storage node
     */
    public StorageNode assign(String file) {
        BigInteger key = hashFunction(file, this.totalSlots);
        int index = bisect(this.keys, key, "right") % this.keys.size();

        return this.nodes.get(index);
    }

}
