package com.borzacchiello.advent_of_code.strategy.task04;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.borzacchiello.advent_of_code.model.task04.GridPosition;
import com.borzacchiello.advent_of_code.strategy.impl.task04.PaperRollCalculator;

class PaperRollCalculatorTest {

    @ParameterizedTest
    @MethodSource("provideGrids")
    void shouldFindAccessibleRolls(
            String[] rows,
            int expectedAccessibleRolls) {

        char[][] grid = toGrid(rows);

        List<GridPosition> result =
                PaperRollCalculator.findAccessibleRolls(grid);

        assertEquals(expectedAccessibleRolls, result.size());
    }

    private static Stream<Arguments> provideGrids() {

        return Stream.of(
                // Un solo rotolo non possiede vicini.
                Arguments.of(
                        new String[]{"@"},
                        1),

                // Nessun rotolo nella griglia.
                Arguments.of(
                        new String[]{"..."},
                        0),

                // Due rotoli adiacenti hanno un solo vicino.
                Arguments.of(
                        new String[]{"@@"},
                        2),

                // In una matrice 2x2 ogni rotolo ha tre vicini.
                Arguments.of(
                        new String[]{
                                "@@",
                                "@@"
                        },
                        4),

                // In una matrice 3x3 piena sono accessibili
                // solamente i quattro angoli.
                Arguments.of(
                        new String[]{
                                "@@@",
                                "@@@",
                                "@@@"
                        },
                        4),

                // I quattro rotoli non sono adiacenti.
                Arguments.of(
                        new String[]{
                                "@.@",
                                "...",
                                "@.@"
                        },
                        4),

                // Il centro ha esattamente quattro vicini.
                // La regola richiede meno di quattro,
                // quindi sono accessibili solo i quattro angoli.
                Arguments.of(
                        new String[]{
                                "@.@",
                                ".@.",
                                "@.@"
                        },
                        4)
        );
    }

    @ParameterizedTest
    @MethodSource("provideRemovalCases")
    void shouldRemoveSelectedRolls(
            String[] rows,
            List<GridPosition> positions,
            String[] expectedRows) {

        char[][] grid = toGrid(rows);

        PaperRollCalculator.removeRolls(
                grid,
                positions);

        assertEquals(
                List.of(expectedRows),
                toRows(grid));
    }

    private static Stream<Arguments> provideRemovalCases() {

        return Stream.of(
                Arguments.of(
                        new String[]{"@@@"},
                        List.of(
                                new GridPosition(0, 1)),
                        new String[]{"@.@"}),

                Arguments.of(
                        new String[]{
                                "@@",
                                "@@"
                        },
                        List.of(
                                new GridPosition(0, 0),
                                new GridPosition(1, 1)),
                        new String[]{
                                ".@",
                                "@."
                        })
        );
    }

    @Test
    void shouldNotModifyGridWhileFindingAccessibleRolls() {

        char[][] grid = toGrid(
                new String[]{
                        "@@@",
                        "@@@",
                        "@@@"
                });

        char[][] original = copyGrid(grid);

        List<GridPosition> accessibleRolls =
                PaperRollCalculator.findAccessibleRolls(grid);

        assertEquals(4, accessibleRolls.size());

        // La ricerca deve essere solamente di lettura.
        assertGridEquals(original, grid);
    }

    @Test
    void shouldRemoveAllAccessibleRollsSimultaneously() {

        char[][] grid = toGrid(
                new String[]{
                        "@@@",
                        "@@@",
                        "@@@"
                });

        // Prima vengono individuate tutte le posizioni
        // rispetto alla stessa configurazione iniziale.
        List<GridPosition> accessibleRolls =
                PaperRollCalculator.findAccessibleRolls(grid);

        assertEquals(4, accessibleRolls.size());

        // Solo dopo la scansione completa vengono applicate
        // contemporaneamente tutte le rimozioni.
        PaperRollCalculator.removeRolls(
                grid,
                accessibleRolls);

        char[][] expected = toGrid(
                new String[]{
                        ".@.",
                        "@@@",
                        ".@."
                });

        assertGridEquals(expected, grid);
    }

    @Test
    void shouldFindNewAccessibleRollsAfterSimultaneousRemoval() {

        char[][] grid = toGrid(
                new String[]{
                        "@@@",
                        "@@@",
                        "@@@"
                });

        List<GridPosition> firstIteration =
                PaperRollCalculator.findAccessibleRolls(grid);

        assertEquals(4, firstIteration.size());

        PaperRollCalculator.removeRolls(
                grid,
                firstIteration);

        List<GridPosition> secondIteration =
                PaperRollCalculator.findAccessibleRolls(grid);

        // Dopo la prima rimozione diventano accessibili
        // i quattro rotoli laterali.
        assertEquals(4, secondIteration.size());

        // Il centro ha ancora esattamente quattro vicini,
        // quindi non deve essere rimosso in questa iterazione.
        assertTrue(
                secondIteration.stream()
                        .noneMatch(position ->
                                position.equals(
                                        new GridPosition(1, 1))));
    }

    @Test
    void shouldRemoveRollsInSeparateSimultaneousIterations() {

        char[][] grid = toGrid(
                new String[]{
                        "@@@",
                        "@@@",
                        "@@@"
                });

        // ITERAZIONE 1
        // Sono accessibili i quattro angoli.

        List<GridPosition> firstIteration =
                PaperRollCalculator.findAccessibleRolls(grid);

        assertEquals(4, firstIteration.size());

        PaperRollCalculator.removeRolls(
                grid,
                firstIteration);

        assertGridEquals(
                toGrid(
                        new String[]{
                                ".@.",
                                "@@@",
                                ".@."
                        }),
                grid);

        // ITERAZIONE 2
        // Diventano accessibili i quattro rotoli laterali.
        // Il centro deve ancora rimanere.

        List<GridPosition> secondIteration =
                PaperRollCalculator.findAccessibleRolls(grid);

        assertEquals(4, secondIteration.size());

        assertTrue(
                secondIteration.stream()
                        .noneMatch(position ->
                                position.equals(
                                        new GridPosition(1, 1))));

        PaperRollCalculator.removeRolls(
                grid,
                secondIteration);

        assertGridEquals(
                toGrid(
                        new String[]{
                                "...",
                                ".@.",
                                "..."
                        }),
                grid);

        // ITERAZIONE 3
        // Resta solo il centro, che non ha più vicini.

        List<GridPosition> thirdIteration =
                PaperRollCalculator.findAccessibleRolls(grid);

        assertEquals(1, thirdIteration.size());

        assertEquals(
                new GridPosition(1, 1),
                thirdIteration.getFirst());

        PaperRollCalculator.removeRolls(
                grid,
                thirdIteration);

        assertGridEquals(
                toGrid(
                        new String[]{
                                "...",
                                "...",
                                "..."
                        }),
                grid);

        // ITERAZIONE 4
        // Non rimane alcun rotolo.

        List<GridPosition> fourthIteration =
                PaperRollCalculator.findAccessibleRolls(grid);

        assertTrue(fourthIteration.isEmpty());

        // Il processo completo ha rimosso 4 + 4 + 1 rotoli.
        int totalRemoved =
                firstIteration.size()
                + secondIteration.size()
                + thirdIteration.size();

        assertEquals(9, totalRemoved);
    }

    private static char[][] toGrid(String[] rows) {

        char[][] result =
                new char[rows.length][];

        for (int row = 0;
             row < rows.length;
             row++) {

            result[row] =
                    rows[row].toCharArray();
        }

        return result;
    }

    private static List<String> toRows(
            char[][] grid) {

        return Stream.of(grid)
                .map(String::new)
                .toList();
    }

    private static char[][] copyGrid(
            char[][] grid) {

        char[][] copy =
                new char[grid.length][];

        for (int row = 0;
             row < grid.length;
             row++) {

            copy[row] =
                    grid[row].clone();
        }

        return copy;
    }

    private static void assertGridEquals(
            char[][] expected,
            char[][] actual) {

        assertEquals(
                expected.length,
                actual.length);

        for (int row = 0;
             row < expected.length;
             row++) {

            assertArrayEquals(
                    expected[row],
                    actual[row]);
        }
    }
}