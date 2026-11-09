package com.borzacchiello.advent_of_code.strategy.impl.task07;

import java.math.BigInteger;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.model.task07.TachyonManifold;
import com.borzacchiello.advent_of_code.strategy.TaskStrategy;

@Component
public class Task07Part2Strategy
        implements TaskStrategy<TachyonManifold, BigInteger> {

    @Override
    public TaskKey getKey() {
        return new TaskKey(7, 2);
    }

    /**
     * Calcola il numero totale di timeline prodotte
     * dall'interpretazione quantistica del manifold.
     *
     * @param manifold struttura da analizzare
     * @return numero totale di timeline finali
     */
    @Override
    public BigInteger execute(
            TachyonManifold manifold) {

        return QuantumTimelineCalculator.countTimelines(
                manifold);
    }
}