package com.borzacchiello.advent_of_code.util.algorithm.search;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Utility class containing generic search algorithms.
 *
 * <p>The algorithms are independent of the underlying data structure.
 * Neighbor generation is delegated to a function provided by the caller,
 * allowing the same algorithms to be used with graphs, grids and other
 * searchable state spaces.</p>
 */
public final class SearchAlgorithms {

    private SearchAlgorithms() {
        // Utility class: prevents instantiation.
    }

    /**
     * Performs a breadth-first search starting from the specified element.
     *
     * @param start             the starting element
     * @param targetCondition   condition identifying the target
     * @param neighborProvider  function returning neighboring elements
     * @param <T>               element type
     * @return the first matching element, or an empty Optional
     */
    public static <T> Optional<T> breadthFirstSearch(
            T start,
            Predicate<T> targetCondition,
            Function<T, ? extends Iterable<T>> neighborProvider) {

        Set<T> visited = new HashSet<>();
        Deque<T> queue = new ArrayDeque<>();

        visited.add(start);
        queue.addLast(start);

        while (!queue.isEmpty()) {

            T current = queue.removeFirst();

            if (targetCondition.test(current)) {
                return Optional.of(current);
            }

            for (T neighbor : neighborProvider.apply(current)) {

                if (visited.add(neighbor)) {
                    queue.addLast(neighbor);
                }
            }
        }

        return Optional.empty();
    }

    /**
     * Performs a depth-first search starting from the specified element.
     *
     * @param start             the starting element
     * @param targetCondition   condition identifying the target
     * @param neighborProvider  function returning neighboring elements
     * @param <T>               element type
     * @return the first matching element, or an empty Optional
     */
    public static <T> Optional<T> depthFirstSearch(
            T start,
            Predicate<T> targetCondition,
            Function<T, ? extends Iterable<T>> neighborProvider) {

        Set<T> visited = new HashSet<>();
        Deque<T> stack = new ArrayDeque<>();

        stack.push(start);

        while (!stack.isEmpty()) {

            T current = stack.pop();

            if (!visited.add(current)) {
                continue;
            }

            if (targetCondition.test(current)) {
                return Optional.of(current);
            }

            for (T neighbor : neighborProvider.apply(current)) {

                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
                }
            }
        }

        return Optional.empty();
    }

    /**
     * Returns all elements reachable from the specified starting element.
     *
     * @param start            the starting element
     * @param neighborProvider function returning neighboring elements
     * @param <T>              element type
     * @return all reachable elements, including the starting element
     */
    public static <T> Set<T> findReachable(
            T start,
            Function<T, ? extends Iterable<T>> neighborProvider) {

        Set<T> visited = new LinkedHashSet<>();
        Deque<T> queue = new ArrayDeque<>();

        visited.add(start);
        queue.addLast(start);

        while (!queue.isEmpty()) {

            T current = queue.removeFirst();

            for (T neighbor : neighborProvider.apply(current)) {

                if (visited.add(neighbor)) {
                    queue.addLast(neighbor);
                }
            }
        }

        return Set.copyOf(visited);
    }
}
