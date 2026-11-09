package com.borzacchiello.advent_of_code.util.algorithm.geometry;

public record Point2D(int x, int y) {

    public Point2D translate(int dx, int dy) {
        return new Point2D(x + dx, y + dy);
    }

    public int manhattanDistance(Point2D other) {
        return Math.abs(x - other.x)
                + Math.abs(y - other.y);
    }
}
