package com.borzacchiello.advent_of_code.strategy.task07;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.borzacchiello.advent_of_code.model.task07.TachyonManifold;
import com.borzacchiello.advent_of_code.parser.task07.Task07Parser;
import com.borzacchiello.advent_of_code.strategy.impl.task07.TachyonBeamCalculator;

class TachyonBeamCalculatorTest {

    @ParameterizedTest
    @MethodSource("provideManifolds")
    void shouldCountSplits(
            List<String> input,
            int expected) {

        Task07Parser parser =
                new Task07Parser();

        TachyonManifold manifold =
                parser.parse(input);

        int result =
                TachyonBeamCalculator.countSplits(
                        manifold);

        assertEquals(expected, result);
    }

    private static Stream<Arguments>
            provideManifolds() {

        return Stream.of(

                // Nessuno splitter.
                Arguments.of(
                        List.of(
                                "..S..",
                                ".....",
                                "....."),
                        0),

                // Il fascio incontra un solo splitter.
                Arguments.of(
                        List.of(
                                "..S..",
                                ".....",
                                "..^..",
                                "....."),
                        1),

                // Il primo splitter produce due fasci.
                // Entrambi incontrano un nuovo splitter.
                //
                // Split totali:
                // 1 + 2 = 3.
                Arguments.of(
                        List.of(
                                "...S...",
                                ".......",
                                "...^...",
                                ".......",
                                "..^.^..",
                                "......."),
                        3),

                // Un splitter non attraversato da alcun
                // fascio non deve essere contato.
                Arguments.of(
                        List.of(
                                "..S..",
                                ".....",
                                ".^...",
                                "....."),
                        0)
        );
    }
}
