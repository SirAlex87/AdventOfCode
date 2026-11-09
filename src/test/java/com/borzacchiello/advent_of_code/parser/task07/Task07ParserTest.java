package com.borzacchiello.advent_of_code.parser.task07;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.task07.TachyonManifold;

class Task07ParserTest {

    private Task07Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Task07Parser();
    }

    @Test
    void shouldReturnCorrectTaskNumber() {

        assertEquals(
                7,
                parser.getTaskNumber());
    }

    @Test
    void shouldParseValidManifold() {

        TachyonManifold manifold =
                parser.parse(
                        List.of(
                                "...S...",
                                ".......",
                                "..^.^..",
                                "......."));

        assertEquals(
                4,
                manifold.rowCount());

        assertEquals(
                7,
                manifold.columnCount());

        assertEquals(
                3,
                manifold.startColumn());

        assertEquals(
                'S',
                manifold.cellAt(0, 3));

        assertEquals(
                '^',
                manifold.cellAt(2, 2));
    }

    @Test
    void shouldRejectNonRectangularManifold() {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(
                        List.of(
                                "...S...",
                                ".....",
                                "..^.^..")));
    }

    @Test
    void shouldRejectInvalidCharacter() {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(
                        List.of(
                                "...S...",
                                "...#...",
                                "..^.^..")));
    }

    @Test
    void shouldRejectMissingStartPosition() {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(
                        List.of(
                                ".......",
                                ".......",
                                "..^.^..")));
    }

    @Test
    void shouldRejectMultipleStartPositions() {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(
                        List.of(
                                "..SS...",
                                ".......",
                                "..^.^..")));
    }

    @Test
    void shouldRejectStartOutsideFirstRow() {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(
                        List.of(
                                ".......",
                                "...S...",
                                "..^.^..")));
    }

    @Test
    void shouldRejectEmptyInput() {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(List.of()));
    }
}