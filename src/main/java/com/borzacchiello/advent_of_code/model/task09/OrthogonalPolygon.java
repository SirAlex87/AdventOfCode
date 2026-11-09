package com.borzacchiello.advent_of_code.model.task09;

import java.util.List;

public record OrthogonalPolygon(
        List<TilePosition> vertices) {

    public OrthogonalPolygon {
        vertices = List.copyOf(vertices);
    }
}
