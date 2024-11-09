package com.tinker.consistentHashing.base;

/**
 * Class to represent a storage node.
 */
public class StorageNode {
    private final String host;
    private final String name;

    public StorageNode(String host, String name) {
        this.host = host;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getHost() {
        return this.host;
    }

    public String getName() {
        return this.name;
    }

    private String fetchFile(String path) {
        return String.format("Provided resource from host: %s, located at %s", this.host, path);
    }

    private String putFile(String path) {
        return String.format("Saved resource to host: %s, at location %s", this.host, path);
    }
}
