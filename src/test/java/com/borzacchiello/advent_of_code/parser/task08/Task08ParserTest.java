package com.borzacchiello.advent_of_code.parser.task08;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.task08.JunctionBox;
import com.borzacchiello.advent_of_code.model.task08.JunctionBoxData;

class Task08ParserTest {

    private Task08Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Task08Parser();
    }

    @Test
    void shouldReturnCorrectTaskNumber() {

        assertEquals(
                8,
                parser.getTaskNumber());
    }

    @Test
    void shouldParseJunctionBoxes() {

        JunctionBoxData data =
                parser.parse(
                        List.of(
                                "162,817,812",
                                "57,618,57",
                                "906,360,560"));

        assertEquals(
                3,
                data.boxes().size());

        assertEquals(
                new JunctionBox(
                        162,
                        817,
                        812),
                data.boxes().get(0));

        assertEquals(
                new JunctionBox(
                        57,
                        618,
                        57),
                data.boxes().get(1));

        assertEquals(
                new JunctionBox(
                        906,
                        360,
                        560),
                data.boxes().get(2));
    }

    @Test
    void shouldStripCoordinateSpaces() {

        JunctionBoxData data =
                parser.parse(
                        List.of(
                                " 162 , 817 , 812 "));

        assertEquals(
                new JunctionBox(
                        162,
                        817,
                        812),
                data.boxes().getFirst());
    }

    @Test
    void shouldIgnoreBlankLines() {

        JunctionBoxData data =
                parser.parse(
                        List.of(
                                "162,817,812",
                                "",
                                "   ",
                                "57,618,57"));

        assertEquals(
                2,
                data.boxes().size());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "162,817",
            "162,817,812,100",
            "162,,812",
            ",817,812",
            "162,817,",
            "ABC,817,812",
            "162,ABC,812",
            "162,817,ABC"
    })
    void shouldRejectInvalidCoordinates(
            String input) {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(
                        List.of(input)));
    }

    @Test
    void shouldRejectEmptyInput() {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(List.of()));
    }

    @Test
    void shouldSupportNegativeCoordinates() {

        JunctionBoxData data =
                parser.parse(
                        List.of(
                                "-10,20,-30"));

        assertEquals(
                new JunctionBox(
                        -10,
                        20,
                        -30),
                data.boxes().getFirst());
    }
}