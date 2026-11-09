package com.borzacchiello.advent_of_code.parser.task06;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.task06.WorksheetData;

class Task06ParserTest {

    private Task06Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Task06Parser();
    }

    @Test
    void shouldReturnCorrectTaskNumber() {

        assertEquals(
                6,
                parser.getTaskNumber());
    }

    @Test
    void shouldPreserveSpaces() {

        WorksheetData data =
                parser.parse(
                        List.of(
                                "123  45",
                                " 67 890",
                                "+     *"));

        assertEquals(3, data.rowCount());
        assertEquals(7, data.columnCount());

        assertEquals(' ', data.cellAt(0, 3));
        assertEquals(' ', data.cellAt(0, 4));

        assertEquals(' ', data.cellAt(1, 0));
        assertEquals(' ', data.cellAt(1, 3));
    }

    @Test
    void shouldPadShorterLinesOnTheRight() {

        WorksheetData data =
                parser.parse(
                        List.of(
                                "123  45",
                                "67",
                                "+     *"));

        assertEquals(7, data.columnCount());

        assertEquals('6', data.cellAt(1, 0));
        assertEquals('7', data.cellAt(1, 1));

        // Il parser completa la riga più corta con spazi
        // invece di cambiarne l'allineamento.
        assertEquals(' ', data.cellAt(1, 2));
        assertEquals(' ', data.cellAt(1, 6));
    }

    @Test
    void shouldIdentifyLastRowAsOperatorRow() {

        WorksheetData data =
                parser.parse(
                        List.of(
                                "123",
                                "456",
                                "+  "));

        assertEquals(
                2,
                data.operatorRow());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "12A",
            "12#",
            "12-",
            "12/"
    })
    void shouldRejectInvalidCharacters(
            String invalidRow) {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(
                        List.of(
                                invalidRow,
                                "+  ")));
    }

    @Test
    void shouldRejectOperatorOutsideLastRow() {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(
                        List.of(
                                "12+",
                                "345",
                                "+  ")));
    }

    @Test
    void shouldRejectNumbersInOperatorRow() {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(
                        List.of(
                                "123",
                                "456",
                                "+ 1")));
    }

    @Test
    void shouldRejectWorksheetWithoutOperators() {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(
                        List.of(
                                "123",
                                "456",
                                "   ")));
    }

    @Test
    void shouldRejectEmptyInput() {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(List.of()));
    }
}