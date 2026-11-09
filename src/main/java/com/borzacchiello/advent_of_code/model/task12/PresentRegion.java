package com.borzacchiello.advent_of_code.model.task12;

import java.util.List;

public record PresentRegion(
        int width,
        int height,
        List<Integer> shapeQuantities) {

    public PresentRegion {
        shapeQuantities =
                List.copyOf(shapeQuantities);
    }

    public int area() {
        return Math.multiplyExact(
                width,
                height);
    }
}