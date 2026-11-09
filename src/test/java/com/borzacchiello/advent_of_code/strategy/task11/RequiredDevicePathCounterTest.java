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

                // Caso 1:
                // il percorso attraversa prima dac e poi fft.
                //
                // svr -> aaa -> dac -> bbb -> fft -> out
                //
                // Il percorso è valido.
                Arguments.of(
                        List.of(
                                "svr: aaa",
                                "aaa: dac",
                                "dac: bbb",
                                "bbb: fft",
                                "fft: out"),
                        "1"),

                // Caso 2:
                // il percorso attraversa prima fft e poi dac.
                //
                // svr -> aaa -> fft -> bbb -> dac -> out
                //
                // Anche questo percorso è valido,
                // perché l'ordine dei due dispositivi
                // non è importante.
                Arguments.of(
                        List.of(
                                "svr: aaa",
                                "aaa: fft",
                                "fft: bbb",
                                "bbb: dac",
                                "dac: out"),
                        "1"),

                // Caso 3:
                // esistono due percorsi verso out,
                // ma nessuno attraversa entrambi
                // i dispositivi richiesti.
                //
                // svr -> aaa -> dac -> out
                //
                // svr -> fft -> out
                //
                // Il primo visita solo dac.
                // Il secondo visita solo fft.
                //
                // Nessuno dei due deve essere contato.
                Arguments.of(
                        List.of(
                                "svr: aaa fft",
                                "aaa: dac",
                                "dac: out",
                                "fft: out"),
                        "0")
        );
    }
}