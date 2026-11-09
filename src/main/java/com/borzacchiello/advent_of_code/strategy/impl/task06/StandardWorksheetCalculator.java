package com.borzacchiello.advent_of_code.strategy.impl.task06;

import java.math.BigInteger;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.task06.WorksheetData;
import com.borzacchiello.advent_of_code.model.task06.WorksheetProblem;

public final class StandardWorksheetCalculator {

    private StandardWorksheetCalculator() {
        // Classe di utility non istanziabile.
    }

    /**
     * Calcola il risultato di un problema secondo la lettura
     * standard della Parte 1.
     *
     * Ogni riga, esclusa quella degli operatori, contiene un
     * numero. Gli spazi servono solamente per l'allineamento
     * e vengono eliminati prima della conversione.
     */
    public static BigInteger calculate(
            WorksheetData worksheet,
            WorksheetProblem problem) {

        BigInteger result =
                problem.operation().identity();

        boolean numberFound = false;

        for (int row = 0;
             row < worksheet.operatorRow();
             row++) {

            String value = extractRowValue(
                    worksheet,
                    problem,
                    row);

            if (value.isBlank()) {
                continue;
            }

            String normalizedValue =
                    value.replace(" ", "");

            try {
                BigInteger number =
                        new BigInteger(normalizedValue);

                result = problem.operation()
                        .apply(result, number);

                numberFound = true;

            } catch (NumberFormatException exception) {
                throw new InvalidTaskInputException(
                        "Invalid Task 6 number: "
                                + value);
            }
        }

        if (!numberFound) {
            throw new InvalidTaskInputException(
                    "Task 6 problem contains no numbers");
        }

        return result;
    }

    private static String extractRowValue(
            WorksheetData worksheet,
            WorksheetProblem problem,
            int row) {

        StringBuilder value =
                new StringBuilder(problem.width());

        for (int column = problem.startColumn();
             column <= problem.endColumn();
             column++) {

            value.append(
                    worksheet.cellAt(row, column));
        }

        return value.toString();
    }
}
