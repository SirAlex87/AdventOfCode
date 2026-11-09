package com.borzacchiello.advent_of_code.parser.task12;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.task12.GridPosition;
import com.borzacchiello.advent_of_code.model.task12.PresentData;
import com.borzacchiello.advent_of_code.model.task12.PresentRegion;
import com.borzacchiello.advent_of_code.model.task12.PresentShape;
import com.borzacchiello.advent_of_code.parser.TaskParser;

@Component
public class Task12Parser
        implements TaskParser<PresentData> {

    private static final int TASK_NUMBER = 12;

    private static final Pattern SHAPE_HEADER =
            Pattern.compile("^(\\d+):$");

    private static final Pattern REGION =
            Pattern.compile(
                    "^(\\d+)x(\\d+):\\s*(.*)$");

    @Override
    public int getTaskNumber() {
        return TASK_NUMBER;
    }

    @Override
    public PresentData parse(
            List<String> lines) {

        if (lines == null || lines.isEmpty()) {
            throw new InvalidTaskInputException(
                    "Task 12 input is empty");
        }

        List<PresentShape> shapes =
                new ArrayList<>();

        List<PresentRegion> regions =
                new ArrayList<>();

        Integer currentShapeIndex = null;

        List<String> currentShapeRows =
                new ArrayList<>();

        boolean regionSectionStarted = false;

        for (String rawLine : lines) {

            if (rawLine == null) {
                continue;
            }

            String line = rawLine.strip();

            if (line.isBlank()) {
                continue;
            }

            Matcher regionMatcher =
                    REGION.matcher(line);

            if (regionMatcher.matches()) {

                if (currentShapeIndex != null) {

                    shapes.add(
                            createShape(
                                    currentShapeIndex,
                                    currentShapeRows));

                    currentShapeIndex = null;
                    currentShapeRows.clear();
                }

                regionSectionStarted = true;

                regions.add(
                        parseRegion(regionMatcher));

                continue;
            }

            if (regionSectionStarted) {
                throw new InvalidTaskInputException(
                        "Invalid Task 12 region: "
                                + line);
            }

            Matcher shapeMatcher =
                    SHAPE_HEADER.matcher(line);

            if (shapeMatcher.matches()) {

                if (currentShapeIndex != null) {

                    shapes.add(
                            createShape(
                                    currentShapeIndex,
                                    currentShapeRows));

                    currentShapeRows.clear();
                }

                currentShapeIndex =
                        parseShapeIndex(
                                shapeMatcher.group(1));

                continue;
            }

            if (currentShapeIndex == null) {
                throw new InvalidTaskInputException(
                        "Task 12 shape row without header: "
                                + line);
            }

            validateShapeRow(line);

            currentShapeRows.add(line);
        }

        if (currentShapeIndex != null) {

            shapes.add(
                    createShape(
                            currentShapeIndex,
                            currentShapeRows));
        }

        validateInput(
                shapes,
                regions);

        return new PresentData(
                shapes,
                regions);
    }

    private int parseShapeIndex(
            String value) {

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            throw new InvalidTaskInputException(
                    "Invalid Task 12 shape index: "
                            + value);
        }
    }

    private PresentShape createShape(
            int index,
            List<String> rows) {

        if (rows.isEmpty()) {
            throw new InvalidTaskInputException(
                    "Task 12 shape contains no rows: "
                            + index);
        }

        int width =
                rows.getFirst().length();

        Set<GridPosition> cells =
                new HashSet<>();

        for (int row = 0;
             row < rows.size();
             row++) {

            String value =
                    rows.get(row);

            if (value.length() != width) {
                throw new InvalidTaskInputException(
                        "Task 12 shape must be rectangular: "
                                + index);
            }

            for (int column = 0;
                 column < value.length();
                 column++) {

                if (value.charAt(column) == '#') {

                    cells.add(
                            new GridPosition(
                                    row,
                                    column));
                }
            }
        }

        if (cells.isEmpty()) {
            throw new InvalidTaskInputException(
                    "Task 12 shape contains no occupied cells: "
                            + index);
        }

        return new PresentShape(
                index,
                cells);
    }

    private PresentRegion parseRegion(
            Matcher matcher) {

        try {
            int width =
                    Integer.parseInt(
                            matcher.group(1));

            int height =
                    Integer.parseInt(
                            matcher.group(2));

            if (width <= 0 || height <= 0) {
                throw new InvalidTaskInputException(
                        "Task 12 region dimensions "
                                + "must be positive");
            }

            String quantitySection =
                    matcher.group(3).strip();

            if (quantitySection.isBlank()) {
                throw new InvalidTaskInputException(
                        "Task 12 region contains "
                                + "no shape quantities");
            }

            List<Integer> quantities =
                    parseQuantities(
                            quantitySection);

            return new PresentRegion(
                    width,
                    height,
                    quantities);

        } catch (NumberFormatException exception) {

            throw new InvalidTaskInputException(
                    "Invalid Task 12 region");
        }
    }

    private List<Integer> parseQuantities(
            String value) {

        String[] parts =
                value.split("\\s+");

        List<Integer> quantities =
                new ArrayList<>();

        for (String part : parts) {

            try {
                int quantity =
                        Integer.parseInt(part);

                if (quantity < 0) {
                    throw new InvalidTaskInputException(
                            "Task 12 shape quantity "
                                    + "cannot be negative");
                }

                quantities.add(quantity);

            } catch (NumberFormatException exception) {

                throw new InvalidTaskInputException(
                        "Invalid Task 12 shape quantity: "
                                + part);
            }
        }

        return List.copyOf(quantities);
    }

    private void validateShapeRow(
            String line) {

        boolean valid =
                line.chars()
                        .allMatch(value ->
                                value == '#'
                                        || value == '.');

        if (!valid) {
            throw new InvalidTaskInputException(
                    "Invalid Task 12 shape row: "
                            + line);
        }
    }

    private void validateInput(
            List<PresentShape> shapes,
            List<PresentRegion> regions) {

        if (shapes.isEmpty()) {
            throw new InvalidTaskInputException(
                    "Task 12 contains no shapes");
        }

        if (regions.isEmpty()) {
            throw new InvalidTaskInputException(
                    "Task 12 contains no regions");
        }

        for (int index = 0;
             index < shapes.size();
             index++) {

            if (shapes.get(index).index()
                    != index) {

                throw new InvalidTaskInputException(
                        "Task 12 shape indexes "
                                + "must be sequential");
            }
        }

        for (PresentRegion region : regions) {

            if (region.shapeQuantities().size()
                    != shapes.size()) {

                throw new InvalidTaskInputException(
                        "Task 12 region quantity count "
                                + "does not match shape count");
            }
        }
    }
}