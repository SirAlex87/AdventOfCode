package com.borzacchiello.advent_of_code.strategy.impl.task11;

import java.math.BigInteger;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.model.task11.DeviceGraph;
import com.borzacchiello.advent_of_code.strategy.TaskStrategy;

@Component
public class Task11Part2Strategy
        implements TaskStrategy<DeviceGraph, BigInteger> {

    private static final String START =
            "svr";

    private static final String DESTINATION =
            "out";

    @Override
    public TaskKey getKey() {
        return new TaskKey(11, 2);
    }

    @Override
    public BigInteger execute(
            DeviceGraph graph) {

        return RequiredDevicePathCounter
                .countPaths(
                        graph,
                        START,
                        DESTINATION);
    }
}