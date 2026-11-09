package com.borzacchiello.advent_of_code.model.task10;

import java.util.List;

public record MachineConfiguration(
        String indicatorTarget,
        List<MachineButton> buttons,
        List<Integer> joltageTarget) {

    public MachineConfiguration {

        if (indicatorTarget == null) {
            throw new IllegalArgumentException(
                    "Indicator target cannot be null");
        }

        buttons = List.copyOf(buttons);
        joltageTarget = List.copyOf(joltageTarget);
    }

    public int indicatorCount() {
        return indicatorTarget.length();
    }

    public int joltageCounterCount() {
        return joltageTarget.size();
    }
}