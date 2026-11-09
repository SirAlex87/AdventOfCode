package com.borzacchiello.advent_of_code.util.algorithm.graph;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class GraphAlgorithms {

    private GraphAlgorithms() {
        // Classe di algoritmi non istanziabile.
    }

    public static <N> BigInteger countPaths(
            Map<N, List<N>> graph,
            N start,
            N target) {

        return countPaths(
                graph,
                start,
                target,
                new HashMap<>(),
                new HashSet<>());
    }

    private static <N> BigInteger countPaths(
            Map<N, List<N>> graph,
            N current,
            N target,
            Map<N, BigInteger> memo,
            Set<N> activePath) {

        if (current.equals(target)) {
            return BigInteger.ONE;
        }

        BigInteger cached =
                memo.get(current);

        if (cached != null) {
            return cached;
        }

        if (!activePath.add(current)) {
            throw new IllegalArgumentException(
                    "Graph contains a cycle");
        }

        BigInteger total =
                BigInteger.ZERO;

        List<N> neighbours =
                graph.get(current);

        if (neighbours != null) {

            for (N next : neighbours) {

                total = total.add(
                        countPaths(
                                graph,
                                next,
                                target,
                                memo,
                                activePath));
            }
        }

        activePath.remove(current);

        memo.put(
                current,
                total);

        return total;
    }
}