package com.borzacchiello.advent_of_code.strategy.task12;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.borzacchiello.advent_of_code.model.task12.GridPosition;
import com.borzacchiello.advent_of_code.model.task12.PresentRegion;
import com.borzacchiello.advent_of_code.model.task12.PresentShape;
import com.borzacchiello.advent_of_code.strategy.impl.task12.PresentPlacementSolver;

class PresentPlacementSolverTest {

    @Test
    void shouldRejectWhenRequiredAreaExceedsRegionArea() {

        PresentShape shape =
                new PresentShape(
                        0,
                        Set.of(
                                position(0, 0),
                                position(0, 1),
                                position(1, 0)));

        PresentRegion region =
                new PresentRegion(
                        2,
                        2,
                        List.of(2));

        // Due regali da 3 celle richiedono
        // 6 celle, ma la regione ne contiene 4.
        //
        // Il solver deve terminare già
        // durante il controllo dell'area.
        assertFalse(
                PresentPlacementSolver.canFit(
                        List.of(shape),
                        region));
    }

    @Test
    void shouldAcceptWhenPresentFitsDirectly() {

        PresentShape shape =
                new PresentShape(
                        0,
                        Set.of(
                                position(0, 0),
                                position(0, 1),
                                position(1, 0)));

        PresentRegion region =
                new PresentRegion(
                        2,
                        2,
                        List.of(1));

        assertTrue(
                PresentPlacementSolver.canFit(
                        List.of(shape),
                        region));
    }

    @Test
    void shouldUseRotationToFitPresent() {

        PresentShape shape =
                new PresentShape(
                        0,
                        Set.of(
                                position(0, 0),
                                position(0, 1),
                                position(0, 2)));

        PresentRegion region =
                new PresentRegion(
                        1,
                        3,
                        List.of(1));

        // La forma originale è larga 3,
        // mentre la regione è larga 1.
        //
        // Il fit è possibile soltanto
        // ruotando la forma.
        assertTrue(
                PresentPlacementSolver.canFit(
                        List.of(shape),
                        region));
    }

    @Test
    void shouldAllowShapesToInterlockUsingEmptyCells() {

        PresentShape shape =
                new PresentShape(
                        0,
                        Set.of(
                                position(0, 0),
                                position(0, 1),
                                position(1, 0)));

        PresentRegion region =
                new PresentRegion(
                        2,
                        2,
                        List.of(1));

        assertTrue(
                PresentPlacementSolver.canFit(
                        List.of(shape),
                        region));
    }

    @Test
    void shouldRejectWhenAreaFitsButGeometryDoesNot() {

        PresentShape shape =
                new PresentShape(
                        0,
                        Set.of(
                                position(0, 0),
                                position(0, 1),
                                position(0, 2)));

        PresentRegion region =
                new PresentRegion(
                        2,
                        2,
                        List.of(1));

        // Area regalo = 3.
        // Area regione = 4.
        //
        // Il controllo dell'area passa,
        // ma una barra lunga 3 non può entrare
        // in una griglia 2x2 neppure ruotandola.
        assertFalse(
                PresentPlacementSolver.canFit(
                        List.of(shape),
                        region));
    }

    @Test
    void shouldFitMultiplePresentsWithoutOverlap() {

        PresentShape shape =
                new PresentShape(
                        0,
                        Set.of(
                                position(0, 0)));

        PresentRegion region =
                new PresentRegion(
                        2,
                        2,
                        List.of(4));

        assertTrue(
                PresentPlacementSolver.canFit(
                        List.of(shape),
                        region));
    }

    @Test
    void shouldReturnTrueWhenNoPresentsAreRequired() {

        PresentShape shape =
                new PresentShape(
                        0,
                        Set.of(
                                position(0, 0)));

        PresentRegion region =
                new PresentRegion(
                        2,
                        2,
                        List.of(0));

        assertTrue(
                PresentPlacementSolver.canFit(
                        List.of(shape),
                        region));
    }

    private static GridPosition position(
            int row,
            int column) {

        return new GridPosition(
                row,
                column);
    }
}