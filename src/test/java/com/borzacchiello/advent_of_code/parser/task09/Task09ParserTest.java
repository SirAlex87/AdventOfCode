package com.borzacchiello.advent_of_code.parser.task09;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.task09.TileData;
import com.borzacchiello.advent_of_code.model.task09.TilePosition;

class Task09ParserTest {

    private Task09Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Task09Parser();
    }

    @Test
    void shouldReturnCorrectTaskNumber() {

        assertEquals(
                9,
                parser.getTaskNumber());
    }

    @Test
    void shouldParseTilePositions() {

        TileData data = parser.parse(
                List.of(
                        "7,1",
                        "11,1",
                        "11,7",
                        "9,7"));

        assertEquals(
                4,
                data.redTiles().size());

        assertEquals(
                new TilePosition(7, 1),
                data.redTiles().get(0));

        assertEquals(
                new TilePosition(11, 7),
                data.redTiles().get(2));
    }

    @Test
    void shouldStripCoordinateSpaces() {

        TileData data = parser.parse(
                List.of(
                        " 7 , 1 ",
                        " 11 , 7 "));

        assertEquals(
                new TilePosition(7, 1),
                data.redTiles().get(0));

        assertEquals(
                new TilePosition(11, 7),
                data.redTiles().get(1));
    }

    @Test
    void shouldIgnoreBlankLines() {

        TileData data = parser.parse(
                List.of(
                        "7,1",
                        "",
                        "   ",
                        "11,7"));

        assertEquals(
                2,
                data.redTiles().size());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "7",
            "7,1,2",
            "7,",
            ",1",
            "A,1",
            "7,A"
    })
    void shouldRejectInvalidCoordinates(
            String input) {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(
                        List.of(
                                input,
                                "11,7")));
    }

    @Test
    void shouldRejectLessThanTwoTiles() {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(
                        List.of("7,1")));
    }

    @Test
    void shouldRejectEmptyInput() {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(List.of()));
    }
}