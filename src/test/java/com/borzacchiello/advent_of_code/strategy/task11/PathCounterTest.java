package com.borzacchiello.advent_of_code.strategy.task11;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.borzacchiello.advent_of_code.model.task11.DeviceGraph;
import com.borzacchiello.advent_of_code.parser.task11.Task11Parser;
import com.borzacchiello.advent_of_code.strategy.impl.task11.PathCounter;

class PathCounterTest {

    @ParameterizedTest
    @MethodSource("provideGraphs")
    void shouldCountPaths(
            List<String> input,
            String start,
            String destination,
            String expected) {

        Task11Parser parser =
                new Task11Parser();

        DeviceGraph graph =
                parser.parse(input);

        BigInteger result =
                PathCounter.countPaths(
                        graph,
                        start,
                        destination);

        assertEquals(
                new BigInteger(expected),
                result);
    }

    private static Stream<Arguments> provideGraphs() {

        return Stream.of(

                // Un solo percorso.
                Arguments.of(
                        List.of(
                                "you: aaa",
                                "aaa: out"),
                        "you",
                        "out",
                        "1"),

                // Due percorsi indipendenti.
                //
                // you -> aaa -> out
                // you -> bbb -> out
                Arguments.of(
                        List.of(
                                "you: aaa bbb",
                                "aaa: out",
                                "bbb: out"),
                        "you",
                        "out",
                        "2"),

                // Due rami convergono sullo stesso dispositivo.
                //
                // you -> aaa -> ccc -> out
                // you -> bbb -> ccc -> out
                //
                // Il percorso ccc -> out viene calcolato una
                // sola volta dalla memoization, ma contribuisce
                // correttamente a entrambi i percorsi.
                Arguments.of(
                        List.of(
                                "you: aaa bbb",
                                "aaa: ccc",
                                "bbb: ccc",
                                "ccc: out"),
                        "you",
                        "out",
                        "2"),

                // Un ramo non raggiunge out.
                Arguments.of(
                        List.of(
                                "you: aaa bbb",
                                "aaa: out",
                                "bbb: ccc",
                                "ccc: ddd"),
                        "you",
                        "out",
                        "1")
        );
    }
}