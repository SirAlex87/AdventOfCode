package com.borzacchiello.advent_of_code.strategy.task05;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.model.task05.IngredientData;
import com.borzacchiello.advent_of_code.model.task05.IngredientIdRange;
import com.borzacchiello.advent_of_code.parser.task05.Task05Parser;
import com.borzacchiello.advent_of_code.strategy.impl.task05.Task05Part1Strategy;
import com.borzacchiello.advent_of_code.util.TestFileUtils;

class Task05Part1StrategyTest {

    private Task05Part1Strategy strategy;
    private Task05Parser parser;

    @BeforeEach
    void setUp() {
        strategy = new Task05Part1Strategy();
        parser = new Task05Parser();
    }

    @Test
    void shouldReturnCorrectTaskKey() {

        assertEquals(
                new TaskKey(5, 1),
                strategy.getKey());
    }

    @Test
    void shouldReturnExpectedResultForOfficialExample() {

        List<String> lines =
                TestFileUtils.readLines(
                        "task05/prova5Test.txt");

        IngredientData data =
                parser.parse(lines);

        Integer result =
                strategy.execute(data);

        assertEquals(3, result);
    }

    @Test
    void shouldReturnExpectedResultForRealInput() {

        List<String> lines =
                TestFileUtils.readLines(
                        "task05/prova5.txt");

        IngredientData data =
                parser.parse(lines);

        Integer result =
                strategy.execute(data);

        assertEquals(674, result);
    }

    @ParameterizedTest
    @MethodSource("provideOverlappingRangeCases")
    void shouldCountFreshAvailableIdsWithOverlappingRanges(
            List<IngredientIdRange> ranges,
            List<BigInteger> availableIds,
            int expected) {

        IngredientData data =
                new IngredientData(
                        ranges,
                        availableIds);

        Integer result =
                strategy.execute(data);

        assertEquals(expected, result);
    }

    private static Stream<Arguments>
            provideOverlappingRangeCases() {

        return Stream.of(

                // Sovrapposizione parziale:
                //
                // 10-20 e 15-25 producono una copertura
                // complessiva equivalente a 10-25.
                //
                // ID freschi:
                // 10, 16, 20, 25.
                Arguments.of(
                        List.of(
                                range(10, 20),
                                range(15, 25)),
                        ids(
                                9,
                                10,
                                16,
                                20,
                                25,
                                26),
                        4),

                // Il secondo intervallo è completamente
                // contenuto nel primo.
                //
                // Copertura complessiva: 10-30.
                //
                // ID freschi:
                // 10, 15, 20, 30.
                Arguments.of(
                        List.of(
                                range(10, 30),
                                range(15, 20)),
                        ids(
                                9,
                                10,
                                15,
                                20,
                                30,
                                31),
                        4),

                // Intervalli adiacenti:
                //
                // 10-15 e 16-20 non lasciano alcun ID
                // scoperto tra i due intervalli.
                //
                // ID freschi:
                // 10, 15, 16, 20.
                Arguments.of(
                        List.of(
                                range(10, 15),
                                range(16, 20)),
                        ids(
                                9,
                                10,
                                15,
                                16,
                                20,
                                21),
                        4),

                // Sovrapposizione seguita da adiacenza:
                //
                // 10-15
                // 14-20
                // 21-25
                //
                // Copertura complessiva: 10-25.
                //
                // ID freschi:
                // 10, 14, 20, 21, 25.
                Arguments.of(
                        List.of(
                                range(10, 15),
                                range(14, 20),
                                range(21, 25)),
                        ids(
                                9,
                                10,
                                14,
                                20,
                                21,
                                25,
                                26),
                        5),

                // Due gruppi distinti:
                //
                // Primo gruppo: 10-25.
                // Secondo gruppo: 50-70.
                //
                // ID freschi:
                // 16, 25, 55, 70.
                Arguments.of(
                        List.of(
                                range(10, 20),
                                range(15, 25),
                                range(50, 60),
                                range(55, 70)),
                        ids(
                                9,
                                16,
                                25,
                                30,
                                55,
                                70,
                                71),
                        4)
        );
    }

    @Test
    void shouldCountAvailableIdOnlyOnceWhenRangesOverlap() {

        IngredientData data =
                new IngredientData(
                        List.of(
                                range(10, 20),
                                range(15, 25)),
                        List.of(
                                BigInteger.valueOf(16)));

        Integer result =
                strategy.execute(data);

        // L'ID 16 appartiene a entrambi gli intervalli,
        // ma deve essere contato una sola volta.
        assertEquals(1, result);
    }

    @Test
    void shouldCountDuplicatedAvailableIdOnlyOnce() {

        IngredientData data =
                new IngredientData(
                        List.of(
                                range(10, 20)),
                        List.of(
                                BigInteger.valueOf(15),
                                BigInteger.valueOf(15),
                                BigInteger.valueOf(15)));

        Integer result =
                strategy.execute(data);

        // Manteniamo il comportamento del codice originale
        // basato su distinct(): un ID disponibile duplicato
        // viene considerato una sola volta.
        assertEquals(1, result);
    }

    @Test
    void shouldReturnZeroWhenNoAvailableIdIsFresh() {

        IngredientData data =
                new IngredientData(
                        List.of(
                                range(10, 20)),
                        ids(
                                1,
                                5,
                                9,
                                21,
                                30));

        Integer result =
                strategy.execute(data);

        assertEquals(0, result);
    }

    @Test
    void shouldIncludeRangeBoundaries() {

        IngredientData data =
                new IngredientData(
                        List.of(
                                range(10, 20)),
                        ids(
                                9,
                                10,
                                20,
                                21));

        Integer result =
                strategy.execute(data);

        // Gli estremi start ed end sono inclusivi.
        // Sono quindi freschi sia 10 che 20.
        assertEquals(2, result);
    }

    @Test
    void shouldHandleMultipleSeparatedRanges() {

        IngredientData data =
                new IngredientData(
                        List.of(
                                range(10, 20),
                                range(30, 40),
                                range(50, 60)),
                        ids(
                                5,
                                10,
                                15,
                                25,
                                30,
                                40,
                                45,
                                60,
                                70));

        Integer result =
                strategy.execute(data);

        // Sono freschi:
        // 10, 15, 30, 40, 60.
        assertEquals(5, result);
    }

    @Test
    void shouldBeIndependentFromRangeOrder() {

        IngredientData data =
                new IngredientData(
                        List.of(
                                range(50, 60),
                                range(15, 25),
                                range(10, 20)),
                        ids(
                                9,
                                10,
                                16,
                                25,
                                50,
                                60,
                                61));

        Integer result =
                strategy.execute(data);

        // IngredientRangeMerger ordina gli intervalli
        // prima dell'elaborazione.
        //
        // Coperture:
        // 10-25
        // 50-60
        //
        // Sono freschi:
        // 10, 16, 25, 50, 60.
        assertEquals(5, result);
    }

    private static IngredientIdRange range(
            long start,
            long end) {

        return new IngredientIdRange(
                BigInteger.valueOf(start),
                BigInteger.valueOf(end));
    }

    private static List<BigInteger> ids(
            long... values) {

        return Arrays.stream(values)
                .mapToObj(BigInteger::valueOf)
                .toList();
    }
}