package com.borzacchiello.advent_of_code.strategy.task05;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
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
import com.borzacchiello.advent_of_code.strategy.impl.task05.Task05Part2Strategy;
import com.borzacchiello.advent_of_code.util.TestFileUtils;

class Task05Part2StrategyTest {

    private Task05Part2Strategy strategy;
    private Task05Parser parser;

    @BeforeEach
    void setUp() {
        strategy = new Task05Part2Strategy();
        parser = new Task05Parser();
    }

    @Test
    void shouldReturnCorrectTaskKey() {

        assertEquals(
                new TaskKey(5, 2),
                strategy.getKey());
    }

    @Test
    void shouldReturnExpectedResultForOfficialExample() {

        List<String> lines =
                TestFileUtils.readLines(
                        "task05/prova5Test.txt");

        IngredientData data =
                parser.parse(lines);

        BigInteger result =
                strategy.execute(data);

        assertEquals(
                new BigInteger("14"),
                result);
    }

    @Test
    void shouldReturnExpectedResultForRealInput() {

        List<String> lines =
                TestFileUtils.readLines(
                        "task05/prova5.txt");

        IngredientData data =
                parser.parse(lines);

        BigInteger result =
                strategy.execute(data);

        assertEquals(
                new BigInteger("352509891817881"),
                result);
    }

    @ParameterizedTest
    @MethodSource("provideOverlappingRangeCases")
    void shouldCountFreshIdsWithOverlappingRanges(
            List<IngredientIdRange> ranges,
            String expected) {

        IngredientData data =
                new IngredientData(
                        ranges,
                        List.of());

        BigInteger result =
                strategy.execute(data);

        assertEquals(
                new BigInteger(expected),
                result);
    }

    private static Stream<Arguments>
            provideOverlappingRangeCases() {

        return Stream.of(

                // Sovrapposizione parziale:
                //
                // 10-20
                // 15-25
                //
                // Unione: 10-25.
                // Numero ID: 25 - 10 + 1 = 16.
                Arguments.of(
                        List.of(
                                range(10, 20),
                                range(15, 25)),
                        "16"),

                // Intervallo completamente contenuto:
                //
                // 10-30
                // 15-20
                //
                // Unione: 10-30.
                // Numero ID: 21.
                Arguments.of(
                        List.of(
                                range(10, 30),
                                range(15, 20)),
                        "21"),

                // Intervalli adiacenti:
                //
                // 10-15
                // 16-20
                //
                // Non esiste alcun ID scoperto.
                // Unione: 10-20.
                // Numero ID: 11.
                Arguments.of(
                        List.of(
                                range(10, 15),
                                range(16, 20)),
                        "11"),

                // Catena di sovrapposizioni:
                //
                // 10-15
                // 14-20
                // 18-30
                //
                // Unione: 10-30.
                // Numero ID: 21.
                Arguments.of(
                        List.of(
                                range(10, 15),
                                range(14, 20),
                                range(18, 30)),
                        "21"),

                // Sovrapposizione seguita da adiacenza:
                //
                // 10-15
                // 14-20
                // 21-25
                //
                // Unione: 10-25.
                // Numero ID: 16.
                Arguments.of(
                        List.of(
                                range(10, 15),
                                range(14, 20),
                                range(21, 25)),
                        "16"),

                // Due gruppi indipendenti:
                //
                // 10-20 U 15-25 = 10-25 = 16 ID.
                // 50-60 U 55-70 = 50-70 = 21 ID.
                //
                // Totale: 37.
                Arguments.of(
                        List.of(
                                range(10, 20),
                                range(15, 25),
                                range(50, 60),
                                range(55, 70)),
                        "37"),

                // Stesso caso precedente, ma con gli
                // intervalli forniti in ordine differente.
                //
                // Il risultato deve essere indipendente
                // dall'ordine dell'input.
                Arguments.of(
                        List.of(
                                range(55, 70),
                                range(15, 25),
                                range(50, 60),
                                range(10, 20)),
                        "37"),

                // Intervalli identici.
                //
                // Lo stesso insieme di ID deve essere
                // contato una sola volta.
                Arguments.of(
                        List.of(
                                range(10, 20),
                                range(10, 20),
                                range(10, 20)),
                        "11"),

                // Singolo intervallo composto da un solo ID.
                Arguments.of(
                        List.of(
                                range(100, 100)),
                        "1")
        );
    }

    @Test
    void shouldIgnoreAvailableIngredientIds() {

        IngredientData data =
                new IngredientData(
                        List.of(
                                range(10, 20)),
                        List.of(
                                BigInteger.valueOf(1),
                                BigInteger.valueOf(15),
                                BigInteger.valueOf(999)));

        BigInteger result =
                strategy.execute(data);

        // La Parte 2 considera esclusivamente gli
        // intervalli freschi. La seconda sezione
        // dell'input non influenza il risultato.
        assertEquals(
                new BigInteger("11"),
                result);
    }

    @Test
    void shouldCountInclusiveRangeBoundaries() {

        IngredientData data =
                new IngredientData(
                        List.of(
                                range(10, 15)),
                        List.of());

        BigInteger result =
                strategy.execute(data);

        // 10, 11, 12, 13, 14, 15.
        assertEquals(
                new BigInteger("6"),
                result);
    }

    private static IngredientIdRange range(
            long start,
            long end) {

        return new IngredientIdRange(
                BigInteger.valueOf(start),
                BigInteger.valueOf(end));
    }
}