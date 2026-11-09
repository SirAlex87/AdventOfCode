package com.borzacchiello.advent_of_code.strategy.task12;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.borzacchiello.advent_of_code.model.task12.GridPosition;
import com.borzacchiello.advent_of_code.model.task12.PresentShape;
import com.borzacchiello.advent_of_code.model.task12.ShapeOrientation;
import com.borzacchiello.advent_of_code.strategy.impl.task12.ShapeOrientationGenerator;

class ShapeOrientationGeneratorTest {

    @Test
    void shouldGenerateSingleOrientationForSingleCell() {

        PresentShape shape =
                new PresentShape(
                        0,
                        Set.of(
                                position(0, 0)));

        List<ShapeOrientation> orientations =
                ShapeOrientationGenerator.generate(
                        shape);

        assertEquals(
                1,
                orientations.size());
    }

    @Test
    void shouldRemoveEquivalentOrientationsForSquare() {

        PresentShape shape =
                new PresentShape(
                        0,
                        Set.of(
                                position(0, 0),
                                position(0, 1),
                                position(1, 0),
                                position(1, 1)));

        List<ShapeOrientation> orientations =
                ShapeOrientationGenerator.generate(
                        shape);

        // Un quadrato 2x2 rimane identico
        // dopo rotazioni e riflessioni.
        assertEquals(
                1,
                orientations.size());
    }

    @Test
    void shouldGenerateHorizontalAndVerticalOrientations() {

        PresentShape shape =
                new PresentShape(
                        0,
                        Set.of(
                                position(0, 0),
                                position(0, 1),
                                position(0, 2)));

        List<ShapeOrientation> orientations =
                ShapeOrientationGenerator.generate(
                        shape);

        assertEquals(
                2,
                orientations.size());

        assertTrue(
                orientations.stream()
                        .anyMatch(orientation ->
                                orientation.width() == 3
                                        && orientation.height() == 1));

        assertTrue(
                orientations.stream()
                        .anyMatch(orientation ->
                                orientation.width() == 1
                                        && orientation.height() == 3));
    }

    @Test
    void shouldGenerateRotationsAndReflectionsForAsymmetricShape() {

        PresentShape shape =
                new PresentShape(
                        0,
                        Set.of(
                                position(0, 0),
                                position(1, 0),
                                position(1, 1)));

        List<ShapeOrientation> orientations =
                ShapeOrientationGenerator.generate(
                        shape);

        // La forma a L composta da tre celle
        // possiede quattro orientamenti distinti.
        assertEquals(
                4,
                orientations.size());
    }

    private static GridPosition position(
            int row,
            int column) {

        return new GridPosition(
                row,
                column);
    }
}
