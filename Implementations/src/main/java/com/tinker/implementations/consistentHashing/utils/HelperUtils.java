package com.tinker.implementations.consistentHashing.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Class to contain utility methods.
 */
public class HelperUtils {

    /**
     * Creates integer equivalent of SHA256 hash of given key.
     *
     * @param key        key
     * @param totalSlots total slots in hash sparce
     * @return hash value converted to BigInt
     */
    public static BigInteger hashFunction(String key, int totalSlots) {
        String algorithmName = "SHA-256";
        try {
            MessageDigest md = MessageDigest.getInstance(algorithmName);
            md.update(key.getBytes(StandardCharsets.UTF_8));
            String hexDigest = byteToHex(md.digest());

            return new BigInteger(hexDigest, 16).mod(BigInteger.valueOf(totalSlots));

        } catch (NoSuchAlgorithmException e) {
            System.out.printf("%s algorithm not found!", algorithmName);
            e.printStackTrace();
            return BigInteger.valueOf(-1);
        }
    }

    private static String byteToHex(byte[] byteArray) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : byteArray) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    /**
     * Java implementation of Python's bisect_left() and bisect_right(). Returns the position at which the provided key
     * needs to be inserted into the sorted to list to maintain the order.
     * type = left -> all(elem >= x for elem in a[pos+1 : end])
     * type = right -> all(elem > x for elem in a[pos+1 : end])
     *
     * @param sortedList sorted list
     * @param key        value to be inserted
     * @param type       left / right
     * @return insertion position
     */
    public static int bisect(List<BigInteger> sortedList, BigInteger key, String type) {
        int left = 0;
        int right = sortedList.size();

        while (left < right) {
            int mid = left + (right - left) / 2;
            boolean comparator = Objects.equals(type, "left")
                    ? key.compareTo(sortedList.get(mid)) > 0 : key.compareTo(sortedList.get(mid)) >= 0;

            if (comparator) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    /**
     * Pretty prints input list to console.
     *
     * @param list input list
     * @param <T>  type of input list
     */
    public static <T> void printList(List<T> list) {
        String result = list.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", ", "[", "]"));

        System.out.println(result);
    }
}
