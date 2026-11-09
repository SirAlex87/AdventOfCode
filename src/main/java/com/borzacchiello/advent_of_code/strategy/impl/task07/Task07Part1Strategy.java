package com.borzacchiello.advent_of_code.strategy.impl.task07;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.model.task07.TachyonManifold;
import com.borzacchiello.advent_of_code.strategy.TaskStrategy;

@Component
public class Task07Part1Strategy
        implements TaskStrategy<TachyonManifold, Integer> {

    @Override
    public TaskKey getKey() {
        return new TaskKey(7, 1);
    }

    /**
     * Conta quante volte il fascio viene diviso
     * dagli splitter del manifold.
     *
     * @param manifold struttura da analizzare
     * @return numero totale di split
     */
    @Override
    public Integer execute(
            TachyonManifold manifold) {

        return TachyonBeamCalculator.countSplits(
                manifold);
    }
}