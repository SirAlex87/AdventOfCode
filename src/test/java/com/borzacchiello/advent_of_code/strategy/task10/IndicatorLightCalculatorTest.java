package com.borzacchiello.advent_of_code.strategy.task10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.task10.MachineButton;
import com.borzacchiello.advent_of_code.model.task10.MachineConfiguration;
import com.borzacchiello.advent_of_code.strategy.impl.task10.IndicatorLightCalculator;

class IndicatorLightCalculatorTest {

    @ParameterizedTest
    @MethodSource("provideReachableMachines")
    void shouldCalculateMinimumPresses(
            MachineConfiguration machine,
            int expected) {

        int result =
                IndicatorLightCalculator
                        .calculateMinimumPresses(
                                machine);

        assertEquals(
                expected,
                result);
    }

    private static Stream<Arguments>
            provideReachableMachines() {

        return Stream.of(

                // Target già raggiunto.
                Arguments.of(
                        machine(
                                "...",
                                List.of(
                                        button(0),
                                        button(1),
                                        button(2))),
                        0),

                // Una sola pressione.
                Arguments.of(
                        machine(
                                "#..",
                                List.of(
                                        button(0))),
                        1),

                // Servono due pulsanti distinti.
                Arguments.of(
                        machine(
                                "##.",
                                List.of(
                                        button(0),
                                        button(1))),
                        2),

                // Un singolo pulsante modifica
                // contemporaneamente due luci.
                Arguments.of(
                        machine(
                                "#.#",
                                List.of(
                                        button(0, 2))),
                        1),

                // La BFS deve scegliere il percorso minimo.
                //
                // È possibile raggiungere il target con
                // button(0,1), senza usare i due pulsanti
                // singoli separatamente.
                Arguments.of(
                        machine(
                                "##",
                                List.of(
                                        button(0),
                                        button(1),
                                        button(0, 1))),
                        1)
        );
    }

    @ParameterizedTest
    @MethodSource("provideUnreachableMachines")
    void shouldRejectUnreachableTarget(
            MachineConfiguration machine) {

        assertThrows(
                InvalidTaskInputException.class,
                () -> IndicatorLightCalculator
                        .calculateMinimumPresses(
                                machine));
    }

    private static Stream<Arguments>
            provideUnreachableMachines() {

        return Stream.of(
                Arguments.of(
                        machine(
                                ".#",
                                List.of(
                                        button(0)))),

                Arguments.of(
                        machine(
                                "#.#",
                                List.of(
                                        button(0))))
        );
    }

    private static MachineConfiguration machine(
            String target,
            List<MachineButton> buttons) {

        return new MachineConfiguration(
                target,
                buttons,
                java.util.Collections.nCopies(
                        target.length(),
                        0));
    }

    private static MachineButton button(
            Integer... positions) {

        return new MachineButton(
                List.of(positions));
    }
}
