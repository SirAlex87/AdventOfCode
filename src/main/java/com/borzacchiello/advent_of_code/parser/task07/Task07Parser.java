package com.borzacchiello.advent_of_code.parser.task07;

import java.util.List;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.task07.TachyonManifold;
import com.borzacchiello.advent_of_code.parser.TaskParser;

@Component
public class Task07Parser
        implements TaskParser<TachyonManifold> {

    private static final int TASK_NUMBER = 7;

    private static final char START = 'S';
    private static final char EMPTY = '.';
    private static final char SPLITTER = '^';

    @Override
    public int getTaskNumber() {
        return TASK_NUMBER;
    }

    @Override
    public TachyonManifold parse(
            List<String> lines) {

        if (lines == null || lines.isEmpty()) {
            throw new InvalidTaskInputException(
                    "Task 7 input is empty");
        }

        List<String> validLines =
                lines.stream()
                        .filter(line ->
                                line != null
                                        && !line.isBlank())
                        .toList();

        if (validLines.isEmpty()) {
            throw new InvalidTaskInputException(
                    "Task 7 manifold is empty");
        }

        int expectedColumns =
                validLines.getFirst().length();

        char[][] cells =
                new char[validLines.size()]
                        [expectedColumns];

        int startColumn = -1;
        int startCount = 0;

        for (int row = 0;
             row < validLines.size();
             row++) {

            String line =
                    validLines.get(row);

            if (line.length()
                    != expectedColumns) {

                throw new InvalidTaskInputException(
                        "Task 7 manifold must "
                                + "be rectangular");
            }

            for (int column = 0;
                 column < line.length();
                 column++) {

                char value =
                        line.charAt(column);

                validateCharacter(value);

                if (value == START) {

                    startCount++;
                    startColumn = column;

                    if (row != 0) {
                        throw new InvalidTaskInputException(
                                "Task 7 start position "
                                        + "must be in the first row");
                    }
                }

                cells[row][column] = value;
            }
        }

        if (startCount != 1) {
            throw new InvalidTaskInputException(
                    "Task 7 manifold must contain "
                            + "exactly one start position");
        }

        return new TachyonManifold(
                cells,
                startColumn);
    }

    private void validateCharacter(
            char value) {

        if (value != START
                && value != EMPTY
                && value != SPLITTER) {

            throw new InvalidTaskInputException(
                    "Task 7 manifold contains "
                            + "an invalid character: "
                            + value);
        }
    }
}