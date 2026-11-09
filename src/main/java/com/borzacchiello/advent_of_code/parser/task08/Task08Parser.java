package com.borzacchiello.advent_of_code.parser.task08;

import java.util.List;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.task08.JunctionBox;
import com.borzacchiello.advent_of_code.model.task08.JunctionBoxData;
import com.borzacchiello.advent_of_code.parser.TaskParser;

@Component
public class Task08Parser
        implements TaskParser<JunctionBoxData> {

    private static final int TASK_NUMBER = 8;

    @Override
    public int getTaskNumber() {
        return TASK_NUMBER;
    }

    @Override
    public JunctionBoxData parse(
            List<String> lines) {

        if (lines == null || lines.isEmpty()) {
            throw new InvalidTaskInputException(
                    "Task 8 input is empty");
        }

        List<JunctionBox> boxes =
                lines.stream()
                        .filter(line ->
                                line != null
                                        && !line.isBlank())
                        .map(String::strip)
                        .map(this::parseJunctionBox)
                        .toList();

        if (boxes.isEmpty()) {
            throw new InvalidTaskInputException(
                    "Task 8 contains no junction boxes");
        }

        return new JunctionBoxData(boxes);
    }

    private JunctionBox parseJunctionBox(
            String line) {

        String[] coordinates =
                line.split(",", -1);

        if (coordinates.length != 3) {
            throw new InvalidTaskInputException(
                    "Invalid junction box coordinates: "
                            + line);
        }

        try {
            long x = Long.parseLong(
                    coordinates[0].strip());

            long y = Long.parseLong(
                    coordinates[1].strip());

            long z = Long.parseLong(
                    coordinates[2].strip());

            return new JunctionBox(x, y, z);

        } catch (NumberFormatException exception) {

            throw new InvalidTaskInputException(
                    "Invalid junction box coordinates: "
                            + line);
        }
    }
}
