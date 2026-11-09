package com.borzacchiello.advent_of_code.model.task10;

import java.util.List;

public record MachineData(
        List<MachineConfiguration> machines) {

    public MachineData {
        machines = List.copyOf(machines);
    }
}