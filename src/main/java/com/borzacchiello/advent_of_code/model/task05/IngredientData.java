package com.borzacchiello.advent_of_code.model.task05;

import java.math.BigInteger;
import java.util.List;

/**
 * Contiene i dati necessari per elaborare il Task 05.
 *
 * @param freshRanges intervalli di ID considerati freschi
 * @param availableIngredientIds ID disponibili da controllare
 */
public record IngredientData(
        List<IngredientIdRange> freshRanges,
        List<BigInteger> availableIngredientIds) {

    /**
     * Crea copie non modificabili delle liste ricevute.
     */
    public IngredientData {
        freshRanges = List.copyOf(freshRanges);
        availableIngredientIds =
                List.copyOf(availableIngredientIds);
    }
}