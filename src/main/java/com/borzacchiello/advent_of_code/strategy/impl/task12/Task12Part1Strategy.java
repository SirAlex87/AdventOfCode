package com.borzacchiello.advent_of_code.strategy.impl.task12;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.model.task12.PresentData;
import com.borzacchiello.advent_of_code.model.task12.PresentRegion;
import com.borzacchiello.advent_of_code.strategy.TaskStrategy;

@Component
public class Task12Part1Strategy
        implements TaskStrategy<PresentData, Integer> {

    @Override
    public TaskKey getKey() {
        return new TaskKey(12, 1);
    }

    @Override
    public Integer execute(
            PresentData data) {

        int fittedRegions = 0;

        for (PresentRegion region :
                data.regions()) {

            boolean canFit =
                    PresentPlacementSolver.canFit(
                            data.shapes(),
                            region);

            if (canFit) {
                fittedRegions++;
            }
        }

        return fittedRegions;
    }
}