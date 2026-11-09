package com.borzacchiello.advent_of_code.util.algorithm.collection;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public final class MapAlgorithms {

    private MapAlgorithms() {
        // Classe di algoritmi non istanziabile.
    }

    public static <K> void increment(
            Map<K, Integer> map,
            K key) {

        map.merge(
                key,
                1,
                Integer::sum);
    }

    public static <K> void addInteger(
            Map<K, Integer> map,
            K key,
            int value) {

        map.merge(
                key,
                value,
                Integer::sum);
    }

    public static <K> void addLong(
            Map<K, Long> map,
            K key,
            long value) {

        map.merge(
                key,
                value,
                Long::sum);
    }

    public static <K> void addBigInteger(
            Map<K, BigInteger> map,
            K key,
            BigInteger value) {

        map.merge(
                key,
                value,
                BigInteger::add);
    }

    public static <K, V> void addToList(
            Map<K, List<V>> map,
            K key,
            V value) {

        map.computeIfAbsent(
                key,
                ignored ->
                        new java.util.ArrayList<>())
                .add(value);
    }

    public static <K, V> void addAllToList(
            Map<K, List<V>> map,
            K key,
            Iterable<V> values) {

        List<V> destination =
                map.computeIfAbsent(
                        key,
                        ignored ->
                                new java.util.ArrayList<>());

        for (V value : values) {
            destination.add(value);
        }
    }
}
