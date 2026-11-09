package com.borzacchiello.advent_of_code.strategy.impl.task10;

import java.math.BigInteger;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.model.task10.MachineConfiguration;
import com.borzacchiello.advent_of_code.model.task10.MachineData;
import com.borzacchiello.advent_of_code.strategy.TaskStrategy;

@Component
public class Task10Part1Strategy
        implements TaskStrategy<MachineData, BigInteger> {

    @Override
    public TaskKey getKey() {
        return new TaskKey(10, 1);
    }

    @Override
    public BigInteger execute(
            MachineData data) {

        BigInteger total =
                BigInteger.ZERO;

        for (MachineConfiguration machine :
                data.machines()) {

            int minimumPresses =
                    IndicatorLightCalculator
                            .calculateMinimumPresses(
                                    machine);

            total = total.add(
                    BigInteger.valueOf(
                            minimumPresses));
        }

        return total;
    }
}