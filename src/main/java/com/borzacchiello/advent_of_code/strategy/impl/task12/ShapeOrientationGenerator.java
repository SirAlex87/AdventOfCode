package com.borzacchiello.advent_of_code.strategy.impl.task12;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.borzacchiello.advent_of_code.model.task12.GridPosition;
import com.borzacchiello.advent_of_code.model.task12.PresentShape;
import com.borzacchiello.advent_of_code.model.task12.ShapeOrientation;

public final class ShapeOrientationGenerator {

    private ShapeOrientationGenerator() {
        // Classe di utility non istanziabile.
    }

    public static List<ShapeOrientation> generate(
            PresentShape shape) {

        Set<String> orientationKeys =
                new HashSet<>();

        List<ShapeOrientation> orientations =
                new ArrayList<>();

        Set<GridPosition> current =
                shape.cells();

        for (int rotation = 0;
             rotation < 4;
             rotation++) {

            addOrientation(
                    current,
                    orientationKeys,
                    orientations);

            addOrientation(
                    reflect(current),
                    orientationKeys,
                    orientations);

            current = rotate(current);
        }

        return List.copyOf(orientations);
    }

    private static void addOrientation(
            Set<GridPosition> cells,
            Set<String> orientationKeys,
            List<ShapeOrientation> orientations) {

        Set<GridPosition> normalized =
                normalize(cells);

        String key =
                createKey(normalized);

        if (!orientationKeys.add(key)) {
            return;
        }

        int height =
                normalized.stream()
                        .mapToInt(GridPosition::row)
                        .max()
                        .orElseThrow()
                        + 1;

        int width =
                normalized.stream()
                        .mapToInt(GridPosition::column)
                        .max()
                        .orElseThrow()
                        + 1;

        orientations.add(
                new ShapeOrientation(
                        normalized,
                        width,
                        height));
    }

    private static Set<GridPosition> rotate(
            Set<GridPosition> cells) {

        Set<GridPosition> rotated =
                new HashSet<>();

        for (GridPosition cell : cells) {

            rotated.add(
                    new GridPosition(
                            cell.column(),
                            -cell.row()));
        }

        return normalize(rotated);
    }

    private static Set<GridPosition> reflect(
            Set<GridPosition> cells) {

        Set<GridPosition> reflected =
                new HashSet<>();

        for (GridPosition cell : cells) {

            reflected.add(
                    new GridPosition(
                            cell.row(),
                            -cell.column()));
        }

        return normalize(reflected);
    }

    private static Set<GridPosition> normalize(
            Set<GridPosition> cells) {

        int minimumRow =
                cells.stream()
                        .mapToInt(GridPosition::row)
                        .min()
                        .orElseThrow();

        int minimumColumn =
                cells.stream()
                        .mapToInt(GridPosition::column)
                        .min()
                        .orElseThrow();

        Set<GridPosition> normalized =
                new HashSet<>();

        for (GridPosition cell : cells) {

            normalized.add(
                    new GridPosition(
                            cell.row() - minimumRow,
                            cell.column()
                                    - minimumColumn));
        }

        return Set.copyOf(normalized);
    }

    private static String createKey(
            Set<GridPosition> cells) {

        return cells.stream()
                .sorted(
                        Comparator
                                .comparingInt(
                                        GridPosition::row)
                                .thenComparingInt(
                                        GridPosition::column))
                .map(cell ->
                        cell.row()
                                + ","
                                + cell.column())
                .reduce(
                        (first, second) ->
                                first + ";"
                                        + second)
                .orElse("");
    }
}