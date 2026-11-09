package com.borzacchiello.advent_of_code.model.task05;

import java.math.BigInteger;

/**
 * Rappresenta un intervallo inclusivo di ID di ingredienti freschi.
 *
 * @param start primo ID dell'intervallo, incluso
 * @param end ultimo ID dell'intervallo, incluso
 */
public record IngredientIdRange(
        BigInteger start,
        BigInteger end) {
}