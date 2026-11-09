package com.borzacchiello.advent_of_code.parser.task10;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.task10.MachineButton;
import com.borzacchiello.advent_of_code.model.task10.MachineConfiguration;
import com.borzacchiello.advent_of_code.model.task10.MachineData;
import com.borzacchiello.advent_of_code.parser.TaskParser;

@Component
public class Task10Parser
        implements TaskParser<MachineData> {

    private static final int TASK_NUMBER = 10;

    private static final Pattern MACHINE_PATTERN =
            Pattern.compile(
                    "^\\[([.#]+)]\\s+"
                    + "((?:\\([^)]*\\)\\s*)+)"
                    + "\\{([^}]*)}$");

    private static final Pattern BUTTON_PATTERN =
            Pattern.compile("\\(([^)]*)\\)");

    @Override
    public int getTaskNumber() {
        return TASK_NUMBER;
    }

    @Override
    public MachineData parse(
            List<String> lines) {

        if (lines == null || lines.isEmpty()) {
            throw new InvalidTaskInputException(
                    "Task 10 input is empty");
        }

        List<MachineConfiguration> machines =
                lines.stream()
                        .filter(line ->
                                line != null
                                        && !line.isBlank())
                        .map(String::strip)
                        .map(this::parseMachine)
                        .toList();

        if (machines.isEmpty()) {
            throw new InvalidTaskInputException(
                    "Task 10 contains no machines");
        }

        return new MachineData(machines);
    }

    private MachineConfiguration parseMachine(
            String line) {

        Matcher matcher =
                MACHINE_PATTERN.matcher(line);

        if (!matcher.matches()) {
            throw new InvalidTaskInputException(
                    "Invalid Task 10 machine: "
                            + line);
        }

        String indicatorTarget =
                matcher.group(1);

        List<MachineButton> buttons =
                parseButtons(
                        matcher.group(2),
                        indicatorTarget.length());

        List<Integer> joltageTarget =
                parseIntegerList(
                        matcher.group(3),
                        "joltage target");

        validateJoltageTarget(
                indicatorTarget,
                joltageTarget,
                line);

        return new MachineConfiguration(
                indicatorTarget,
                buttons,
                joltageTarget);
    }

    private List<MachineButton> parseButtons(
            String buttonSection,
            int maximumPosition) {

        List<MachineButton> buttons =
                new ArrayList<>();

        Matcher matcher =
                BUTTON_PATTERN.matcher(
                        buttonSection);

        while (matcher.find()) {

            List<Integer> positions =
                    parseIntegerList(
                            matcher.group(1),
                            "button");

            if (positions.isEmpty()) {
                throw new InvalidTaskInputException(
                        "Task 10 button cannot be empty");
            }

            validateButtonPositions(
                    positions,
                    maximumPosition);

            buttons.add(
                    new MachineButton(positions));
        }

        if (buttons.isEmpty()) {
            throw new InvalidTaskInputException(
                    "Task 10 machine contains no buttons");
        }

        return List.copyOf(buttons);
    }

    private List<Integer> parseIntegerList(
            String value,
            String elementName) {

        if (value == null
                || value.isBlank()) {

            return List.of();
        }

        String[] parts =
                value.split(",", -1);

        List<Integer> values =
                new ArrayList<>();

        for (String part : parts) {

            if (part.isBlank()) {
                throw new InvalidTaskInputException(
                        "Invalid Task 10 "
                                + elementName
                                + ": "
                                + value);
            }

            try {
                values.add(
                        Integer.parseInt(
                                part.strip()));
            } catch (NumberFormatException exception) {
                throw new InvalidTaskInputException(
                        "Invalid Task 10 "
                                + elementName
                                + ": "
                                + value);
            }
        }

        return List.copyOf(values);
    }

    private void validateButtonPositions(
            List<Integer> positions,
            int maximumPosition) {

        for (int position : positions) {

            if (position < 0
                    || position >= maximumPosition) {

                throw new InvalidTaskInputException(
                        "Task 10 button position "
                                + "is outside the machine: "
                                + position);
            }
        }

        long uniquePositions =
                positions.stream()
                        .distinct()
                        .count();

        if (uniquePositions != positions.size()) {
            throw new InvalidTaskInputException(
                    "Task 10 button contains "
                            + "duplicated positions");
        }
    }

    private void validateJoltageTarget(
            String indicatorTarget,
            List<Integer> joltageTarget,
            String line) {

        if (joltageTarget.size()
                != indicatorTarget.length()) {

            throw new InvalidTaskInputException(
                    "Task 10 joltage target size "
                            + "does not match the machine: "
                            + line);
        }

        boolean negativeValue =
                joltageTarget.stream()
                        .anyMatch(value ->
                                value < 0);

        if (negativeValue) {
            throw new InvalidTaskInputException(
                    "Task 10 joltage values "
                            + "cannot be negative");
        }
    }
}