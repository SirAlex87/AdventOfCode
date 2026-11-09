package com.borzacchiello.advent_of_code.model.task08;

import java.util.List;

public record JunctionBoxData(
        List<JunctionBox> boxes) {

    public JunctionBoxData {
        boxes = List.copyOf(boxes);
    }
}