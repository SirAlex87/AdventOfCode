package com.borzacchiello.advent_of_code.strategy.impl.task11;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.task11.DeviceGraph;

public final class PathCounter {

    private PathCounter() {
        // Classe di utility non istanziabile.
    }

   public static BigInteger countPaths(
            DeviceGraph graph,
            String start,
            String destination) {

        if (!graph.containsDevice(start)) {
            throw new InvalidTaskInputException(
                    "Task 11 start device not found: "
                            + start);
        }

        if (!graph.containsDevice(destination)) {
            throw new InvalidTaskInputException(
                    "Task 11 destination not found: "
                            + destination);
        }

        Map<String, BigInteger> memo =
                new HashMap<>();

        Set<String> currentPath =
                new HashSet<>();

        return countPaths(
                graph,
                start,
                destination,
                memo,
                currentPath);
    }

    private static BigInteger countPaths(
            DeviceGraph graph,
            String current,
            String destination,
            Map<String, BigInteger> memo,
            Set<String> currentPath) {

        if (current.equals(destination)) {
            return BigInteger.ONE;
        }

        BigInteger cached =
                memo.get(current);

        if (cached != null) {
            return cached;
        }

        // Il requisito rappresenta un grafo di instradamento.
        // Proteggiamo comunque l'algoritmo da eventuali cicli
        // nell'input, evitando ricorsione infinita.
        if (!currentPath.add(current)) {
            throw new InvalidTaskInputException(
                    "Task 11 graph contains a cycle "
                            + "involving device: "
                            + current);
        }

        BigInteger total =
                BigInteger.ZERO;

        for (String next :
                graph.destinations(current)) {

            total = total.add(
                    countPaths(
                            graph,
                            next,
                            destination,
                            memo,
                            currentPath));
        }

        currentPath.remove(current);

        memo.put(
                current,
                total);

        return total;
    }
}