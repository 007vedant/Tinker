package com.tinker;

import com.tinker.consistentHashing.base.StorageNode;
import com.tinker.consistentHashing.core.ConsistentHash;

import java.util.List;
import java.util.ArrayList;

import static com.tinker.consistentHashing.utils.HelperUtils.printList;

public class ConsistentHashingMain {
    private ConsistentHashingMain() {
    }

    public static void main(String[] args) {
        // testing consistent hashing implementation
        ConsistentHash ch = new ConsistentHash(175);
        List<StorageNode> nodes = getStorageNodes();
        List<String> files = getFilesToStore();

        for (StorageNode node : nodes) {
            try {
                ch.addNode(node);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        printList(ch.getKeys()); // [17, 51, 56, 81, 149]
        printList(ch.getNodes()); // [B, D, C, A, E]
        for (String file : files) {
            System.out.printf("file %s <-> storage node: %s%n", file, ch.assign(file));
        }
        /*
            File file1.txt <-> storage node: {host=192.168.0.1,name='A'}
            File file2.txt <-> storage node: {host=192.168.0.2,name='B'}
            File file3.txt <-> storage node: {host=192.168.0.5,name='E'}
            File file4.txt <-> storage node: {host=192.168.0.3,name='C'}
            File file5.txt <-> storage node: {host=192.168.0.5,name='E'}
         */

        // removing storage node E
        try {
            ch.removeNode(nodes.get(4));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        printList(ch.getKeys()); // [17, 51, 56, 81]
        printList(ch.getNodes()); // [B, D, C, A]

        for (String file : files) {
            System.out.printf("File %s <-> storage node: %s%n", file, ch.assign(file));
        }
        /*
            File file1.txt <-> storage node: {host=192.168.0.1,name='A'}
            File file2.txt <-> storage node: {host=192.168.0.2,name='B'}
            File file3.txt <-> storage node: {host=192.168.0.2,name='B'}
            File file4.txt <-> storage node: {host=192.168.0.3,name='C'}
            File file5.txt <-> storage node: {host=192.168.0.2,name='B'}
         */

    }

    private static List<StorageNode> getStorageNodes() {
        List<StorageNode> storageNodes = new ArrayList<>();
        storageNodes.add(new StorageNode("192.168.0.1", "A"));
        storageNodes.add(new StorageNode("192.168.0.2", "B"));
        storageNodes.add(new StorageNode("192.168.0.3", "C"));
        storageNodes.add(new StorageNode("192.168.0.4", "D"));
        storageNodes.add(new StorageNode("192.168.0.5", "E"));

        return storageNodes;
    }

    private static List<String> getFilesToStore() {
        List<String> files = new ArrayList<>();
        files.add("file1.txt");
        files.add("file2.txt");
        files.add("file3.txt");
        files.add("file4.txt");
        files.add("file5.txt");

        return files;
    }
}