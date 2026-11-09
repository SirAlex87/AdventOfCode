package com.borzacchiello.advent_of_code.util.algorithm.range;

public record IntRange(long start, long end) {

    public boolean contains(long value) {
        return value >= start && value <= end;
    }

    public boolean overlaps(IntRange other) {
        return start <= other.end
                && other.start <= end;
    }

    public long length() {
        return end - start + 1;
    }
}
