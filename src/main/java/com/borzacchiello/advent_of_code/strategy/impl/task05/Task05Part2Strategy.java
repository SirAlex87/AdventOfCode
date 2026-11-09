package com.borzacchiello.advent_of_code.strategy.impl.task05;

import java.math.BigInteger;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.model.task05.IngredientData;
import com.borzacchiello.advent_of_code.strategy.TaskStrategy;

@Component
public class Task05Part2Strategy
        implements TaskStrategy<IngredientData, BigInteger> {

    @Override
    public TaskKey getKey() {
        return new TaskKey(5, 2);
    }

    /**
     * Calcola il numero complessivo di ID freschi coperti
     * dagli intervalli.
     *
     * Prima di contare gli ID, gli intervalli vengono ordinati
     * e uniti. In questo modo gli ID presenti in più intervalli
     * sovrapposti vengono contati una sola volta.
     *
     * @param data dati del Task 05
     * @return numero complessivo di ID considerati freschi
     */
    @Override
    public BigInteger execute(
            IngredientData data) {

        return IngredientRangeMerger.countCoveredIds(
                data.freshRanges());
    }
}