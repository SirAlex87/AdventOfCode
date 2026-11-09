package com.borzacchiello.advent_of_code.strategy.impl.task06;

import java.math.BigInteger;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.task06.WorksheetData;
import com.borzacchiello.advent_of_code.model.task06.WorksheetProblem;

public final class CephalopodWorksheetCalculator {

    private CephalopodWorksheetCalculator() {
        // Classe di utility non istanziabile.
    }

    /**
     * Calcola il risultato secondo la lettura cefalopode.
     *
     * Ogni colonna del problema rappresenta un numero:
     * la cifra più significativa si trova in alto e quella
     * meno significativa si trova in basso.
     *
     * Le colonne vengono lette da destra verso sinistra.
     * L'ordine non modifica addizioni e moltiplicazioni, ma
     * viene mantenuto per aderire esattamente al requisito.
     */
    public static BigInteger calculate(
            WorksheetData worksheet,
            WorksheetProblem problem) {

        BigInteger result =
                problem.operation().identity();

        boolean numberFound = false;

        for (int column = problem.endColumn();
             column >= problem.startColumn();
             column--) {

            String value =
                    extractColumnValue(
                            worksheet,
                            column);

            if (value.isEmpty()) {
                continue;
            }

            try {
                BigInteger number =
                        new BigInteger(value);

                result = problem.operation()
                        .apply(result, number);

                numberFound = true;

            } catch (NumberFormatException exception) {
                throw new InvalidTaskInputException(
                        "Invalid Task 6 column number: "
                                + value);
            }
        }

        if (!numberFound) {
            throw new InvalidTaskInputException(
                    "Task 6 problem contains no numbers");
        }

        return result;
    }

    private static String extractColumnValue(
            WorksheetData worksheet,
            int column) {

        StringBuilder value =
                new StringBuilder();

        for (int row = 0;
             row < worksheet.operatorRow();
             row++) {

            char digit =
                    worksheet.cellAt(row, column);

            if (digit != ' ') {
                value.append(digit);
            }
        }

        return value.toString();
    }
}