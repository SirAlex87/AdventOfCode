package com.borzacchiello.advent_of_code.util.algorithm.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import org.javatuples.Pair;

public final class CollectionAlgorithms {

    private CollectionAlgorithms() {
        // Classe di algoritmi non istanziabile.
    }

    public static <T> List<Pair<T, T>> uniquePairs(
            List<T> values) {

        if (values == null || values.size() < 2) {
            return List.of();
        }

        List<Pair<T, T>> pairs =
                new ArrayList<>();

        for (int first = 0;
             first < values.size();
             first++) {

            for (int second = first + 1;
                 second < values.size();
                 second++) {

                pairs.add(
                        new Pair<>(
                                values.get(first),
                                values.get(second)));
            }
        }

        return List.copyOf(pairs);
    }

    public static <T> List<T> distinctPreservingOrder(
            Collection<T> values) {

        if (values == null || values.isEmpty()) {
            return List.of();
        }

        return List.copyOf(
                new LinkedHashSet<>(values));
    }

    public static <T> Partition<T> partition(
            Collection<T> values,
            Predicate<? super T> predicate) {

        if (values == null || values.isEmpty()) {
            return new Partition<>(
                    List.of(),
                    List.of());
        }

        List<T> matching =
                new ArrayList<>();

        List<T> notMatching =
                new ArrayList<>();

        for (T value : values) {

            if (predicate.test(value)) {
                matching.add(value);
            } else {
                notMatching.add(value);
            }
        }

        return new Partition<>(
                matching,
                notMatching);
    }

    public static <T> Map<T, Long> frequencies(
            Collection<T> values) {

        if (values == null || values.isEmpty()) {
            return Map.of();
        }

        Map<T, Long> frequencies =
                new LinkedHashMap<>();

        for (T value : values) {

            frequencies.merge(
                    value,
                    1L,
                    Long::sum);
        }

        return Map.copyOf(frequencies);
    }

    public static <T, K> Map<K, List<T>> groupBy(
            Collection<T> values,
            Function<? super T, ? extends K> classifier) {

        if (values == null || values.isEmpty()) {
            return Map.of();
        }

        Map<K, List<T>> groups =
                new LinkedHashMap<>();

        for (T value : values) {

            K key =
                    classifier.apply(value);

            groups.computeIfAbsent(
                            key,
                            ignored -> new ArrayList<>())
                    .add(value);
        }

        Map<K, List<T>> result =
                new LinkedHashMap<>();

        groups.forEach(
                (key, group) ->
                        result.put(
                                key,
                                List.copyOf(group)));

        return Map.copyOf(result);
    }

    public static <T> Set<T> intersection(
            Collection<T> first,
            Collection<T> second) {

        if (first == null
                || second == null
                || first.isEmpty()
                || second.isEmpty()) {

            return Set.of();
        }

        Set<T> secondSet =
                new LinkedHashSet<>(second);

        Set<T> result =
                new LinkedHashSet<>();

        for (T value : first) {

            if (secondSet.contains(value)) {
                result.add(value);
            }
        }

        return Set.copyOf(result);
    }

    public static <T extends Comparable<? super T>>
            Optional<MinMaxResult<T>> minMax(
                    Collection<T> values) {

        return minMax(
                values,
                Comparator.naturalOrder());
    }

    public static <T> Optional<MinMaxResult<T>> minMax(
            Collection<T> values,
            Comparator<? super T> comparator) {

        if (values == null || values.isEmpty()) {
            return Optional.empty();
        }

        T minimum = null;
        T maximum = null;

        for (T value : values) {

            if (minimum == null
                    || comparator.compare(
                            value,
                            minimum) < 0) {

                minimum = value;
            }

            if (maximum == null
                    || comparator.compare(
                            value,
                            maximum) > 0) {

                maximum = value;
            }
        }

        return Optional.of(
                new MinMaxResult<>(
                        minimum,
                        maximum));
    }

    public record Partition<T>(
            List<T> matching,
            List<T> notMatching) {

        public Partition {
            matching = List.copyOf(matching);
            notMatching =
                    List.copyOf(notMatching);
        }
    }

    public record MinMaxResult<T>(
            T min,
            T max) {
    }
}