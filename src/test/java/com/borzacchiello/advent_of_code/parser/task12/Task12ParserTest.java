package com.borzacchiello.advent_of_code.parser.task12;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.task12.GridPosition;
import com.borzacchiello.advent_of_code.model.task12.PresentData;

class Task12ParserTest {

    private Task12Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Task12Parser();
    }

    @Test
    void shouldReturnCorrectTaskNumber() {

        assertEquals(
                12,
                parser.getTaskNumber());
    }

    @Test
    void shouldParseShapesAndRegions() {

        PresentData data = parser.parse(
                List.of(
                        "0:",
                        "##.",
                        ".##",
                        "1:",
                        "###",
                        "#..",
                        "",
                        "4x4: 2 0",
                        "12x5: 1 3"));

        assertEquals(
                2,
                data.shapes().size());

        assertEquals(
                2,
                data.regions().size());

        assertEquals(
                0,
                data.shapes().get(0).index());

        assertEquals(
                4,
                data.shapes().get(0).area());

        assertEquals(
                true,
                data.shapes().get(0)
                        .cells()
                        .contains(
                                new GridPosition(0, 0)));

        assertEquals(
                4,
                data.regions().get(0).width());

        assertEquals(
                4,
                data.regions().get(0).height());

        assertEquals(
                List.of(2, 0),
                data.regions().get(0)
                        .shapeQuantities());
    }

    @Test
    void shouldRejectNonSequentialShapeIndexes() {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(
                        List.of(
                                "0:",
                                "#",
                                "2:",
                                "#",
                                "2x2: 1 1")));
    }

    @Test
    void shouldRejectNonRectangularShape() {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(
                        List.of(
                                "0:",
                                "###",
                                "##",
                                "3x3: 1")));
    }

    @Test
    void shouldRejectShapeWithoutOccupiedCells() {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(
                        List.of(
                                "0:",
                                "...",
                                "...",
                                "3x3: 1")));
    }

    @Test
    void shouldRejectWrongQuantityCount() {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(
                        List.of(
                                "0:",
                                "#",
                                "1:",
                                "#",
                                "3x3: 1")));
    }

    @Test
    void shouldRejectNegativeQuantity() {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(
                        List.of(
                                "0:",
                                "#",
                                "3x3: -1")));
    }

    @Test
    void shouldRejectEmptyInput() {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(List.of()));
    }
}