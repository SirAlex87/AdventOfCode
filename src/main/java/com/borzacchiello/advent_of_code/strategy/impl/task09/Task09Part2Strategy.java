package com.borzacchiello.advent_of_code.strategy.impl.task09;

import java.math.BigInteger;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.model.task09.TileData;
import com.borzacchiello.advent_of_code.strategy.TaskStrategy;

@Component
public class Task09Part2Strategy
        implements TaskStrategy<TileData, BigInteger> {

    @Override
    public TaskKey getKey() {
        return new TaskKey(9, 2);
    }

    @Override
    public BigInteger execute(
            TileData data) {

        return ValidRectangleCalculator
                .findLargestArea(
                        data.redTiles());
    }
}