package com.borzacchiello.advent_of_code.strategy.impl.task04;

import java.util.List;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.model.task04.GridPosition;
import com.borzacchiello.advent_of_code.model.task04.PaperRollGrid;
import com.borzacchiello.advent_of_code.strategy.TaskStrategy;

@Component
public class Task04Part1Strategy
        implements TaskStrategy<PaperRollGrid, Integer> {

    @Override
    public TaskKey getKey() {
        return new TaskKey(4, 1);
    }

    /**
     * Conta quanti rotoli sono accessibili nella configurazione iniziale.
     *
     * <p>La Parte 1 non rimuove alcun rotolo: viene effettuata una sola
     * valutazione della griglia ricevuta in input.</p>
     *
     * @param grid disposizione iniziale dei rotoli
     * @return numero di rotoli accessibili
     */
    @Override
    public Integer execute(PaperRollGrid grid) {

        List<GridPosition> accessibleRolls =
                PaperRollCalculator.findAccessibleRolls(
                        grid.cells());

        return accessibleRolls.size();
    }
}