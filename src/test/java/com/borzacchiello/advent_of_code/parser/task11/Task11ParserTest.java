package com.borzacchiello.advent_of_code.parser.task11;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.task11.DeviceGraph;

class Task11ParserTest {

    private Task11Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Task11Parser();
    }

    @Test
    void shouldReturnCorrectTaskNumber() {

        assertEquals(
                11,
                parser.getTaskNumber());
    }

    @Test
    void shouldParseDeviceConnections() {

        DeviceGraph graph = parser.parse(
                List.of(
                        "you: bbb ccc",
                        "bbb: ddd eee",
                        "ccc: ddd eee fff",
                        "eee: out"));

        assertEquals(
                List.of("bbb", "ccc"),
                graph.destinations("you"));

        assertEquals(
                List.of("ddd", "eee"),
                graph.destinations("bbb"));

        assertEquals(
                List.of("ddd", "eee", "fff"),
                graph.destinations("ccc"));

        assertEquals(
                List.of("out"),
                graph.destinations("eee"));
    }

    @Test
    void shouldReturnEmptyDestinationsForTerminalDevice() {

        DeviceGraph graph = parser.parse(
                List.of(
                        "you: aaa",
                        "aaa: out"));

        assertEquals(
                List.of(),
                graph.destinations("out"));
    }

    @Test
    void shouldIgnoreBlankLines() {

        DeviceGraph graph = parser.parse(
                List.of(
                        "you: aaa",
                        "",
                        "   ",
                        "aaa: out"));

        assertEquals(
                List.of("aaa"),
                graph.destinations("you"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "you",
            "you:",
            ": aaa",
            "you: aaa: bbb"
    })
    void shouldRejectInvalidConnection(
            String input) {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(
                        List.of(input)));
    }

    @Test
    void shouldRejectDuplicateSource() {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(
                        List.of(
                                "you: aaa",
                                "you: bbb")));
    }

    @Test
    void shouldRejectEmptyInput() {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(List.of()));
    }
}