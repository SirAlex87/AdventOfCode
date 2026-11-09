package com.borzacchiello.advent_of_code.strategy.task10;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.borzacchiello.advent_of_code.model.task10.MachineButton;
import com.borzacchiello.advent_of_code.model.task10.MachineConfiguration;
import com.borzacchiello.advent_of_code.strategy.impl.task10.JoltageOptimizationCalculator;

class JoltageOptimizationCalculatorTest {

    @ParameterizedTest
    @MethodSource("provideMachines")
    void shouldCalculateMinimumPresses(
            MachineConfiguration machine,
            int expected) {

        int result =
                JoltageOptimizationCalculator
                        .calculateMinimumPresses(
                                machine);

        assertEquals(
                expected,
                result);
    }

    private static Stream<Arguments>
            provideMachines() {

        return Stream.of(

                // Un contatore incrementato da un solo
                // pulsante fino al valore 3.
                Arguments.of(
                        machine(
                                ".",
                                List.of(
                                        button(0)),
                                List.of(3)),
                        3),

                // Due contatori indipendenti.
                //
                // primo pulsante  -> contatore 0
                // secondo pulsante -> contatore 1
                //
                // 2 + 3 = 5 pressioni.
                Arguments.of(
                        machine(
                                "..",
                                List.of(
                                        button(0),
                                        button(1)),
                                List.of(2, 3)),
                        5),

                // Un pulsante incrementa entrambi
                // i contatori.
                //
                // Con target 4,4 bastano 4 pressioni.
                Arguments.of(
                        machine(
                                "..",
                                List.of(
                                        button(0, 1)),
                                List.of(4, 4)),
                        4),

                // Il solver deve scegliere la combinazione
                // che minimizza il numero totale di pressioni.
                //
                // button(0)
                // button(1)
                // button(0,1)
                //
                // Target 3,3.
                // La soluzione minima è premere
                // button(0,1) tre volte.
                Arguments.of(
                        machine(
                                "..",
                                List.of(
                                        button(0),
                                        button(1),
                                        button(0, 1)),
                                List.of(3, 3)),
                        3)
        );
    }

    private static MachineConfiguration machine(
            String indicatorTarget,
            List<MachineButton> buttons,
            List<Integer> joltageTarget) {

        return new MachineConfiguration(
                indicatorTarget,
                buttons,
                joltageTarget);
    }

    private static MachineButton button(
            Integer... positions) {

        return new MachineButton(
                List.of(positions));
    }
}