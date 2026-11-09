package com.borzacchiello.advent_of_code.parser.task04;

import java.util.List;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.task04.PaperRollGrid;
import com.borzacchiello.advent_of_code.parser.TaskParser;

@Component
public class Task04Parser implements TaskParser<PaperRollGrid> {

    private static final int TASK_NUMBER = 4;

    @Override
    public int getTaskNumber() {
        return TASK_NUMBER;
    }

    @Override
    public PaperRollGrid parse(List<String> lines) {

        List<String> validLines = lines.stream()
                .filter(line -> line != null && !line.isBlank())
                .map(String::strip)
                .toList();

        if (validLines.isEmpty()) {
            throw new InvalidTaskInputException(
                    "Task 4 input grid is empty");
        }

        int expectedColumns = validLines.getFirst().length();

        char[][] cells =
                new char[validLines.size()][expectedColumns];

        for (int row = 0; row < validLines.size(); row++) {

            String line = validLines.get(row);

            validateLine(line, expectedColumns);

            cells[row] = line.toCharArray();
        }

        return new PaperRollGrid(cells);
    }

    /**
     * Verifica che ogni riga abbia la stessa dimensione e contenga
     * esclusivamente celle previste dal requisito.
     *
     * @param line riga da validare
     * @param expectedColumns numero atteso di colonne
     */
    private void validateLine(
            String line,
            int expectedColumns) {

        if (line.length() != expectedColumns) {
            throw new InvalidTaskInputException(
                    "Task 4 grid must be rectangular");
        }

        boolean valid = line.chars()
                .allMatch(value ->
                        value == '@' || value == '.');

        if (!valid) {
            throw new InvalidTaskInputException(
                    "Task 4 grid contains invalid characters: "
                            + line);
        }
    }
}