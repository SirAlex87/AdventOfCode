package com.borzacchiello.advent_of_code.model.task12;

import java.util.Set;

public record PresentShape(
        int index,
        Set<GridPosition> cells) {

    public PresentShape {
        cells = Set.copyOf(cells);
    }

    public int area() {
        return cells.size();
    }
}