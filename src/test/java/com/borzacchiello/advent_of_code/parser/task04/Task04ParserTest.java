package com.borzacchiello.advent_of_code.parser.task04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.task04.PaperRollGrid;

class Task04ParserTest {

    private Task04Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Task04Parser();
    }

    @Test
    void shouldReturnCorrectTaskNumber() {
        assertEquals(4, parser.getTaskNumber());
    }

    @ParameterizedTest
    @MethodSource("provideValidGrids")
    void shouldParseValidGrid(
            List<String> lines,
            int expectedRows,
            int expectedColumns) {

        PaperRollGrid grid = parser.parse(lines);

        assertEquals(expectedRows, grid.cells().length);
        assertEquals(
                expectedColumns,
                grid.cells()[0].length);
    }

    private static Stream<Arguments> provideValidGrids() {
        return Stream.of(
                Arguments.of(
                        List.of("@"),
                        1,
                        1),
                Arguments.of(
                        List.of(
                                "@.",
                                ".@"),
                        2,
                        2),
                Arguments.of(
                        List.of(
                                "..@@",
                                "@.@.",
                                "@@@@"),
                        3,
                        4)
        );
    }

    @Test
    void shouldPreserveGridContent() {

        PaperRollGrid grid = parser.parse(
                List.of(
                        "..@@",
                        "@.@.",
                        "@@@@"));

        assertEquals('.', grid.cells()[0][0]);
        assertEquals('@', grid.cells()[0][2]);
        assertEquals('@', grid.cells()[1][0]);
        assertEquals('.', grid.cells()[1][1]);
        assertEquals('@', grid.cells()[2][3]);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "..A@",
            "..#@",
            "..x@",
            "12@@",
            "@ @"
    })
    void shouldThrowExceptionForInvalidCharacters(
            String input) {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(List.of(input)));
    }

    @Test
    void shouldThrowExceptionForNonRectangularGrid() {

        List<String> lines = List.of(
                "@@@@",
                "@@@",
                "@@@@");

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(lines));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            " ",
            "   ",
            "\t"
    })
    void shouldThrowExceptionWhenGridIsEmpty(
            String input) {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(List.of(input)));
    }

    @Test
    void shouldIgnoreBlankLinesWhenOtherLinesExist() {

        PaperRollGrid grid = parser.parse(
                List.of(
                        "@@@",
                        "",
                        "   ",
                        "..."));

        assertEquals(2, grid.cells().length);
        assertEquals(3, grid.cells()[0].length);
    }

    @Test
    void shouldIgnoreNullLines() {

        List<String> input = new ArrayList<>();

        input.add("@@@");
        input.add(null);
        input.add("...");

        PaperRollGrid grid = parser.parse(input);

        assertEquals(2, grid.cells().length);
        assertEquals(3, grid.cells()[0].length);
    }
}