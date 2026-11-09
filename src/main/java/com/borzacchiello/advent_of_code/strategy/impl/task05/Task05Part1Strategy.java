package com.borzacchiello.advent_of_code.strategy.impl.task05;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.model.task05.IngredientData;
import com.borzacchiello.advent_of_code.model.task05.IngredientIdRange;
import com.borzacchiello.advent_of_code.strategy.TaskStrategy;

@Component
public class Task05Part1Strategy
        implements TaskStrategy<IngredientData, Integer> {

    @Override
    public TaskKey getKey() {
        return new TaskKey(5, 1);
    }

    /**
     * Conta gli ID disponibili che appartengono ad almeno
     * uno degli intervalli freschi.
     *
     * Gli ID duplicati vengono considerati una sola volta,
     * mantenendo il comportamento del codice originale.
     *
     * @param data dati del Task 05
     * @return numero di ID disponibili considerati freschi
     */
    @Override
    public Integer execute(
            IngredientData data) {

        List<IngredientIdRange> mergedRanges =
                IngredientRangeMerger.merge(
                		data.freshRanges());

        long freshCount =
        		data.availableIngredientIds()
                        .stream()
                        .distinct()
                        .filter(id ->
                                isFresh(
                                        id,
                                        mergedRanges))
                        .count();

        return Math.toIntExact(freshCount);
    }

    /**
     * Verifica se l'ID appartiene ad almeno un intervallo.
     *
     * Gli intervalli sono già ordinati e uniti. Se l'ID è
     * minore dell'inizio dell'intervallo corrente, non potrà
     * appartenere nemmeno agli intervalli successivi.
     */
    private boolean isFresh(
            BigInteger ingredientId,
            List<IngredientIdRange> ranges) {

        for (IngredientIdRange range : ranges) {

            if (ingredientId.compareTo(
                    range.start()) < 0) {

                return false;
            }

            if (ingredientId.compareTo(
                    range.end()) <= 0) {

                return true;
            }
        }

        return false;
    }
}