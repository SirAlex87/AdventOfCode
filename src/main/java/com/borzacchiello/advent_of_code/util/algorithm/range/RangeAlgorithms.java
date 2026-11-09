package com.borzacchiello.advent_of_code.util.algorithm.range;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Utility class containing common algorithms for working with integer ranges.
 *
 * <p>
 * Ranges are considered inclusive on both ends.
 * </p>
 */
public final class RangeAlgorithms {

    private RangeAlgorithms() {
        // Utility class: prevents instantiation.
    }

    /**
     * Checks whether two ranges overlap.
     *
     * @param first  the first range
     * @param second the second range
     * @return {@code true} if the ranges overlap
     */
    public static boolean overlaps(IntRange first, IntRange second) {
        return first.start() <= second.end()
                && second.start() <= first.end();
    }

    /**
     * Checks whether two ranges overlap or are directly adjacent.
     *
     * <p>
     * For example, the inclusive ranges [1, 5] and [6, 10] are adjacent
     * and can therefore be merged.
     * </p>
     *
     * @param first  the first range
     * @param second the second range
     * @return {@code true} if the ranges overlap or are adjacent
     */
    public static boolean areMergeable(
            IntRange first,
            IntRange second) {

        return overlaps(first, second)
                || areAdjacent(first.end(), second.start())
                || areAdjacent(second.end(), first.start());
    }

    /**
     * Returns the intersection between two ranges.
     *
     * @param first  the first range
     * @param second the second range
     * @return the intersection, or {@code null} if the ranges do not overlap
     */
    public static IntRange intersection(
            IntRange first,
            IntRange second) {

        if (!overlaps(first, second)) {
            return null;
        }

        return new IntRange(
                Math.max(first.start(), second.start()),
                Math.min(first.end(), second.end()));
    }

    /**
     * Merges two overlapping or adjacent ranges.
     *
     * @param first  the first range
     * @param second the second range
     * @return the merged range
     * @throws IllegalArgumentException if the ranges are not mergeable
     */
    public static IntRange merge(
            IntRange first,
            IntRange second) {

        if (!areMergeable(first, second)) {
            throw new IllegalArgumentException(
                    "Ranges must overlap or be adjacent");
        }

        return new IntRange(
                Math.min(first.start(), second.start()),
                Math.max(first.end(), second.end()));
    }

    /**
     * Merges all overlapping or adjacent ranges.
     *
     * <p>
     * The input list is not modified. The resulting ranges are sorted
     * according to their starting value.
     * </p>
     *
     * @param ranges the ranges to merge
     * @return an immutable list containing the merged ranges
     */
    public static List<IntRange> mergeAll(List<IntRange> ranges) {

        if (ranges.isEmpty()) {
            return List.of();
        }

        List<IntRange> sortedRanges = ranges.stream()
                .sorted(Comparator.comparingLong(IntRange::start))
                .toList();

        List<IntRange> result = new ArrayList<>();

        IntRange current = sortedRanges.getFirst();

        for (int i = 1; i < sortedRanges.size(); i++) {
            IntRange next = sortedRanges.get(i);

            if (areMergeable(current, next)) {
                current = merge(current, next);
            } else {
                result.add(current);
                current = next;
            }
        }

        result.add(current);

        return List.copyOf(result);
    }

    /**
     * Checks whether two values represent consecutive range boundaries.
     *
     * <p>
     * The explicit check against {@link Long#MAX_VALUE} prevents overflow
     * when adding one to the ending value.
     * </p>
     *
     * @param end   the ending value of the first range
     * @param start the starting value of the second range
     * @return {@code true} if {@code start} immediately follows {@code end}
     */
    private static boolean areAdjacent(long end, long start) {
        return end != Long.MAX_VALUE && end + 1 == start;
    }
}
