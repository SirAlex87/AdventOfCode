package com.borzacchiello.advent_of_code.strategy.task07;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.borzacchiello.advent_of_code.model.task07.TachyonManifold;
import com.borzacchiello.advent_of_code.parser.task07.Task07Parser;
import com.borzacchiello.advent_of_code.strategy.impl.task07.QuantumTimelineCalculator;

class QuantumTimelineCalculatorTest {

    @ParameterizedTest
    @MethodSource("provideManifolds")
    void shouldCountTimelines(
            List<String> input,
            String expected) {

        Task07Parser parser =
                new Task07Parser();

        TachyonManifold manifold =
                parser.parse(input);

        BigInteger result =
                QuantumTimelineCalculator.countTimelines(
                        manifold);

        assertEquals(
                new BigInteger(expected),
                result);
    }

    private static Stream<Arguments>
            provideManifolds() {

        return Stream.of(

                // Nessuno splitter:
                // esiste una sola timeline.
                Arguments.of(
                        List.of(
                                "..S..",
                                ".....",
                                "....."),
                        "1"),

                // Un singolo splitter genera
                // due timeline.
                Arguments.of(
                        List.of(
                                "..S..",
                                ".....",
                                "..^..",
                                "....."),
                        "2"),

                // Il primo splitter genera due timeline.
                //
                // Entrambe incontrano un secondo splitter:
                //
                // 1 -> 2 -> 4 timeline.
                Arguments.of(
                        List.of(
                                "...S...",
                                ".......",
                                "...^...",
                                ".......",
                                "..^.^..",
                                "......."),
                        "4"),

                // Uno splitter non raggiunto non modifica
                // il numero delle timeline.
                Arguments.of(
                        List.of(
                                "..S..",
                                ".....",
                                ".^...",
                                "....."),
                        "1")
        );
    }
}
