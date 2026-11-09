package com.borzacchiello.advent_of_code.strategy.impl.task04;

import java.util.List;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.model.task04.GridPosition;
import com.borzacchiello.advent_of_code.model.task04.PaperRollGrid;
import com.borzacchiello.advent_of_code.strategy.TaskStrategy;

@Component
public class Task04Part2Strategy
        implements TaskStrategy<PaperRollGrid, Integer> {

    @Override
    public TaskKey getKey() {
        return new TaskKey(4, 2);
    }

    /**
     * Calcola il numero totale di rotoli rimovibili applicando
     * ripetutamente la regola di accessibilità.
     *
     * <p>Ogni iterazione è composta da due fasi distinte:</p>
     *
     * <ol>
     *   <li>individuazione di tutti i rotoli accessibili;</li>
     *   <li>rimozione simultanea dei rotoli individuati.</li>
     * </ol>
     *
     * <p>La procedura termina quando un'intera iterazione non trova
     * più alcun rotolo accessibile.</p>
     *
     * @param grid disposizione iniziale dei rotoli
     * @return numero complessivo di rotoli rimossi
     */
    @Override
    public Integer execute(PaperRollGrid grid) {

        char[][] workingGrid =
                grid.copyCells();

        int totalRemoved = 0;

        while (true) {

            List<GridPosition> accessibleRolls =
                    PaperRollCalculator.findAccessibleRolls(
                            workingGrid);

            if (accessibleRolls.isEmpty()) {
                break;
            }

            totalRemoved += accessibleRolls.size();

            // Tutte le rimozioni vengono applicate solo dopo aver
            // completato la scansione dell'iterazione corrente.
            PaperRollCalculator.removeRolls(
                    workingGrid,
                    accessibleRolls);
        }

        return totalRemoved;
    }
}