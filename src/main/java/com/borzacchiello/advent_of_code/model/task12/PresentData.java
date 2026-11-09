package com.borzacchiello.advent_of_code.model.task12;

import java.util.List;

public record PresentData(
        List<PresentShape> shapes,
        List<PresentRegion> regions) {

    public PresentData {
        shapes = List.copyOf(shapes);
        regions = List.copyOf(regions);
    }
}
