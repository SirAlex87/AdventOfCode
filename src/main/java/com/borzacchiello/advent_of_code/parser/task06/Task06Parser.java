package com.borzacchiello.advent_of_code.parser.task06;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.task06.WorksheetData;
import com.borzacchiello.advent_of_code.parser.TaskParser;

@Component
public class Task06Parser
        implements TaskParser<WorksheetData> {

    private static final int TASK_NUMBER = 6;

    @Override
    public int getTaskNumber() {
        return TASK_NUMBER;
    }

    @Override
    public WorksheetData parse(
            List<String> lines) {

        if (lines == null || lines.isEmpty()) {
            throw new InvalidTaskInputException(
                    "Task 6 input is empty");
        }

        List<String> worksheetLines =
                removeExternalBlankLines(lines);

        if (worksheetLines.size() < 2) {
            throw new InvalidTaskInputException(
                    "Task 6 worksheet must contain "
                            + "numbers and an operator row");
        }

        int maximumWidth = worksheetLines.stream()
                .mapToInt(String::length)
                .max()
                .orElseThrow();

        char[][] cells =
                new char[worksheetLines.size()]
                        [maximumWidth];

        for (int row = 0;
             row < worksheetLines.size();
             row++) {

            String line = worksheetLines.get(row);

            validateCharacters(line, row,
                    worksheetLines.size() - 1);

            String paddedLine =
                    padRight(line, maximumWidth);

            cells[row] =
                    paddedLine.toCharArray();
        }

        validateOperatorRow(
                cells[cells.length - 1]);

        return new WorksheetData(cells);
    }

    private List<String> removeExternalBlankLines(
            List<String> lines) {

        int first = 0;
        int last = lines.size() - 1;

        while (first <= last
                && isEmptyLine(lines.get(first))) {

            first++;
        }

        while (last >= first
                && isEmptyLine(lines.get(last))) {

            last--;
        }

        if (first > last) {
            throw new InvalidTaskInputException(
                    "Task 6 input is empty");
        }

        List<String> result =
                new ArrayList<>();

        for (int index = first;
             index <= last;
             index++) {

            String line = lines.get(index);

            if (line == null) {
                throw new InvalidTaskInputException(
                        "Task 6 worksheet contains a null line");
            }

            result.add(line);
        }

        return result;
    }

    private boolean isEmptyLine(String line) {
        return line == null || line.isEmpty();
    }

    private void validateCharacters(
            String line,
            int row,
            int operatorRow) {

        for (int column = 0;
             column < line.length();
             column++) {

            char value = line.charAt(column);

            boolean valid =
                    Character.isDigit(value)
                    || value == ' '
                    || value == '+'
                    || value == '*';

            if (!valid) {
                throw new InvalidTaskInputException(
                        "Task 6 worksheet contains "
                                + "an invalid character: "
                                + value);
            }

            if (row != operatorRow
                    && (value == '+'
                    || value == '*')) {

                throw new InvalidTaskInputException(
                        "Task 6 operators are allowed "
                                + "only in the last row");
            }

            if (row == operatorRow
                    && Character.isDigit(value)) {

                throw new InvalidTaskInputException(
                        "Task 6 operator row cannot "
                                + "contain numbers");
            }
        }
    }

    private void validateOperatorRow(
            char[] operatorRow) {

        boolean operatorFound = false;

        for (char value : operatorRow) {
            if (value == '+' || value == '*') {
                operatorFound = true;
                break;
            }
        }

        if (!operatorFound) {
            throw new InvalidTaskInputException(
                    "Task 6 operator row contains "
                            + "no operations");
        }
    }

    private String padRight(
            String value,
            int width) {

        return value
                + " ".repeat(width - value.length());
    }
}