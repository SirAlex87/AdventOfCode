package com.borzacchiello.advent_of_code.model.task09;

import java.math.BigInteger;

public record TileRectangle(
        TilePosition first,
        TilePosition second) {

    public long minX() {
        return Math.min(
                first.x(),
                second.x());
    }

    public long maxX() {
        return Math.max(
                first.x(),
                second.x());
    }

    public long minY() {
        return Math.min(
                first.y(),
                second.y());
    }

    public long maxY() {
        return Math.max(
                first.y(),
                second.y());
    }

    public BigInteger area() {

        BigInteger width =
                BigInteger.valueOf(maxX())
                        .subtract(
                                BigInteger.valueOf(minX()))
                        .add(BigInteger.ONE);

        BigInteger height =
                BigInteger.valueOf(maxY())
                        .subtract(
                                BigInteger.valueOf(minY()))
                        .add(BigInteger.ONE);

        return width.multiply(height);
    }
}