package com.borzacchiello.advent_of_code.parser.task11;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.task11.DeviceGraph;
import com.borzacchiello.advent_of_code.parser.TaskParser;

@Component
public class Task11Parser
        implements TaskParser<DeviceGraph> {

    private static final int TASK_NUMBER = 11;

    @Override
    public int getTaskNumber() {
        return TASK_NUMBER;
    }

    @Override
    public DeviceGraph parse(
            List<String> lines) {

        if (lines == null || lines.isEmpty()) {
            throw new InvalidTaskInputException(
                    "Task 11 input is empty");
        }

        Map<String, List<String>> connections =
                new LinkedHashMap<>();

        for (String line : lines) {

            if (line == null || line.isBlank()) {
                continue;
            }

            parseConnection(
                    line.strip(),
                    connections);
        }

        if (connections.isEmpty()) {
            throw new InvalidTaskInputException(
                    "Task 11 contains no devices");
        }

        return new DeviceGraph(connections);
    }

    private void parseConnection(
            String line,
            Map<String, List<String>> connections) {

        String[] parts =
                line.split(":", -1);

        if (parts.length != 2) {
            throw new InvalidTaskInputException(
                    "Invalid Task 11 connection: "
                            + line);
        }

        String source =
                parts[0].strip();

        String destinationsPart =
                parts[1].strip();

        if (source.isBlank()
                || destinationsPart.isBlank()) {

            throw new InvalidTaskInputException(
                    "Invalid Task 11 connection: "
                            + line);
        }

        if (connections.containsKey(source)) {
            throw new InvalidTaskInputException(
                    "Duplicate Task 11 source: "
                            + source);
        }

        String[] destinationValues =
                destinationsPart.split("\\s+");

        List<String> destinations =
                new ArrayList<>();

        for (String destination :
                destinationValues) {

            String value =
                    destination.strip();

            if (!value.isBlank()) {
                destinations.add(value);
            }
        }

        if (destinations.isEmpty()) {
            throw new InvalidTaskInputException(
                    "Task 11 source contains "
                            + "no destinations: "
                            + source);
        }

        connections.put(
                source,
                List.copyOf(destinations));
    }
}