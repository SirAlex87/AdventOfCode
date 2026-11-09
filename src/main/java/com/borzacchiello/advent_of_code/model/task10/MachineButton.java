package com.borzacchiello.advent_of_code.model.task10;

import java.util.List;

public record MachineButton(
        List<Integer> positions) {

    public MachineButton {
        positions = List.copyOf(positions);
    }
}