package com.borzacchiello.advent_of_code.parser.task09;

import java.util.List;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.task09.TileData;
import com.borzacchiello.advent_of_code.model.task09.TilePosition;
import com.borzacchiello.advent_of_code.parser.TaskParser;

@Component
public class Task09Parser
        implements TaskParser<TileData> {

    private static final int TASK_NUMBER = 9;

    @Override
    public int getTaskNumber() {
        return TASK_NUMBER;
    }

    @Override
    public TileData parse(
            List<String> lines) {

        if (lines == null || lines.isEmpty()) {
            throw new InvalidTaskInputException(
                    "Task 9 input is empty");
        }

        List<TilePosition> redTiles =
                lines.stream()
                        .filter(line ->
                                line != null
                                        && !line.isBlank())
                        .map(String::strip)
                        .map(this::parsePosition)
                        .toList();

        if (redTiles.size() < 2) {
            throw new InvalidTaskInputException(
                    "Task 9 needs at least two red tiles");
        }

        return new TileData(redTiles);
    }

    private TilePosition parsePosition(
            String line) {

        String[] coordinates =
                line.split(",", -1);

        if (coordinates.length != 2
                || coordinates[0].isBlank()
                || coordinates[1].isBlank()) {

            throw new InvalidTaskInputException(
                    "Invalid tile coordinates: "
                            + line);
        }

        try {

            long x =
                    Long.parseLong(
                            coordinates[0].strip());

            long y =
                    Long.parseLong(
                            coordinates[1].strip());

            return new TilePosition(
                    x,
                    y);

        } catch (NumberFormatException exception) {

            throw new InvalidTaskInputException(
                    "Invalid tile coordinates: "
                            + line);
        }
    }
}