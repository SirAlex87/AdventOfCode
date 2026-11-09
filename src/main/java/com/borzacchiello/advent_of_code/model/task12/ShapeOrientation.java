package com.borzacchiello.advent_of_code.model.task12;

import java.util.Set;

public record ShapeOrientation(
        Set<GridPosition> cells,
        int width,
        int height) {
    }
