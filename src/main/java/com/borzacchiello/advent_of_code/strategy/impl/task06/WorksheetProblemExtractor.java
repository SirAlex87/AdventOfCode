package com.borzacchiello.advent_of_code.strategy.impl.task06;

import java.util.ArrayList;
import java.util.List;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.task06.MathOperation;
import com.borzacchiello.advent_of_code.model.task06.WorksheetData;
import com.borzacchiello.advent_of_code.model.task06.WorksheetProblem;

public final class WorksheetProblemExtractor {

    private WorksheetProblemExtractor() {
        // Classe di utility non istanziabile.
    }

    /**
     * Individua gli intervalli di colonne che rappresentano
     * i singoli problemi.
     *
     * Una colonna è un separatore solamente quando contiene
     * spazi in tutte le righe, compresa quella degli operatori.
     */
    public static List<WorksheetProblem> extract(
            WorksheetData worksheet) {

        List<WorksheetProblem> problems =
                new ArrayList<>();

        int column = 0;

        while (column < worksheet.columnCount()) {

            while (column < worksheet.columnCount()
                    && isBlankColumn(
                            worksheet,
                            column)) {

                column++;
            }

            if (column >= worksheet.columnCount()) {
                break;
            }

            int startColumn = column;

            while (column < worksheet.columnCount()
                    && !isBlankColumn(
                            worksheet,
                            column)) {

                column++;
            }

            int endColumn = column - 1;

            problems.add(
                    createProblem(
                            worksheet,
                            startColumn,
                            endColumn));
        }

        if (problems.isEmpty()) {
            throw new InvalidTaskInputException(
                    "Task 6 worksheet contains "
                            + "no math problems");
        }

        return List.copyOf(problems);
    }

    private static WorksheetProblem createProblem(
            WorksheetData worksheet,
            int startColumn,
            int endColumn) {

        Character operator = null;

        for (int column = startColumn;
             column <= endColumn;
             column++) {

            char value = worksheet.cellAt(
                    worksheet.operatorRow(),
                    column);

            if (value == '+' || value == '*') {

                if (operator != null) {
                    throw new InvalidTaskInputException(
                            "Task 6 problem contains "
                                    + "multiple operators");
                }

                operator = value;
            }
        }

        if (operator == null) {
            throw new InvalidTaskInputException(
                    "Task 6 problem contains no operator");
        }

        return new WorksheetProblem(
                startColumn,
                endColumn,
                MathOperation.fromSymbol(operator));
    }

    private static boolean isBlankColumn(
            WorksheetData worksheet,
            int column) {

        for (int row = 0;
             row < worksheet.rowCount();
             row++) {

            if (worksheet.cellAt(row, column) != ' ') {
                return false;
            }
        }

        return true;
    }
}
