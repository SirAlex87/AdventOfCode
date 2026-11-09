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
import com.borzacchiello.advent_of_code.strategy.impl.task11.RequiredDevicePathCounter;

class RequiredDevicePathCounterTest {

    @ParameterizedTest
    @MethodSource("provideGraphs")
    void shouldCountOnlyPathsVisitingDacAndFft(
            List<String> input,
            String expected) {

        Task11Parser parser =
                new Task11Parser();

        DeviceGraph graph =
                parser.parse(input);

        BigInteger result =
                RequiredDevicePathCounter.countPaths(
                        graph,
                        "svr",
                        "out");

        assertEquals(
                new BigInteger(expected),
                result);
    }

    private static Stream<Arguments> provideGraphs() {

        return Stream.of(

                // Ordine dac -> fft.
                Arguments.of(
                        List.of(
                                "svr: aaa",
                                "aaa: dac",
                                "dac: bbb",
                                "bbb: fft",
                                "fft: out"),
                        "1"),

                // Ordine fft -> dac.
                Arguments.of(
                        List.of(
                                "svr: aaa",
                                "aaa: fft",
                                "fft: bbb",
                                "bbb: dac",
                                "dac: out"),
                        "1"),

                // Il percorso attraversa solo dac.
                Arguments.of(
                        List.of(
                                "svr: aaa fft",
                                "aaa: dac",
                                "dac: out",
                                "fft: out"),
                        "0"),

                // Due percorsi validi:
                //
                // primo: dac -> fft
                // secondo: fft -> dac
                Arguments.of(
                        List.of(
                                "svr: aaa bbb",
                                "aaa: dac",
                                "dac: ccc",
                                "ccc: fft",
                                "fft: out",
                                "bbb: fff",
                                "fff: fft",
                                "fft2: out",
                                "outHelper: out",
                                "fff2: out",
                                "bbb2: out",
                                "ccc2: out",
                                "dac2: out",
                                "unused: out",
                                "fftHelper: dac"),
                        "1")
        );
    }
}