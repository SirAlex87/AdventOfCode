package com.borzacchiello.advent_of_code.parser.task10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.task10.MachineButton;
import com.borzacchiello.advent_of_code.model.task10.MachineConfiguration;
import com.borzacchiello.advent_of_code.model.task10.MachineData;

class Task10ParserTest {

    private Task10Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Task10Parser();
    }

    @Test
    void shouldReturnCorrectTaskNumber() {

        assertEquals(
                10,
                parser.getTaskNumber());
    }

    @Test
    void shouldParseMachineConfiguration() {

        MachineData data = parser.parse(
                List.of(
                        "[.##.] "
                        + "(3) "
                        + "(1,3) "
                        + "(2) "
                        + "(2,3) "
                        + "(0,2) "
                        + "(0,1) "
                        + "{3,5,4,7}"));

        assertEquals(
                1,
                data.machines().size());

        MachineConfiguration machine =
                data.machines().getFirst();

        assertEquals(
                ".##.",
                machine.indicatorTarget());

        assertEquals(
                6,
                machine.buttons().size());

        assertEquals(
                new MachineButton(
                        List.of(3)),
                machine.buttons().getFirst());

        assertEquals(
                new MachineButton(
                        List.of(1, 3)),
                machine.buttons().get(1));

        assertEquals(
                List.of(3, 5, 4, 7),
                machine.joltageTarget());
    }

    @Test
    void shouldParseMultipleMachines() {

        MachineData data = parser.parse(
                List.of(
                        "[.#] (0) (1) {1,1}",
                        "[##] (0,1) {2,2}"));

        assertEquals(
                2,
                data.machines().size());
    }

    @Test
    void shouldIgnoreBlankLines() {

        MachineData data = parser.parse(
                List.of(
                        "[.#] (0) (1) {1,1}",
                        "",
                        "   ",
                        "[##] (0,1) {2,2}"));

        assertEquals(
                2,
                data.machines().size());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            ".##. (0) {1,1,1,1}",
            "[.##.] {1,1,1,1}",
            "[.##.] () {1,1,1,1}",
            "[.##.] (0) {1,1}",
            "[.##.] (4) {1,1,1,1}",
            "[.##.] (A) {1,1,1,1}",
            "[.##.] (0) {1,A,1,1}"
    })
    void shouldRejectInvalidMachine(
            String input) {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(
                        List.of(input)));
    }

    @Test
    void shouldRejectDuplicatedButtonPositions() {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(
                        List.of(
                                "[.##.] "
                                + "(0,0) "
                                + "{1,1,1,1}")));
    }

    @Test
    void shouldRejectNegativeJoltage() {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(
                        List.of(
                                "[.#] "
                                + "(0) "
                                + "(1) "
                                + "{1,-1}")));
    }

    @Test
    void shouldRejectEmptyInput() {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(List.of()));
    }
}