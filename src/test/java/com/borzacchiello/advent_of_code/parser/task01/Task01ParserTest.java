package com.borzacchiello.advent_of_code.parser.task01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.task01.Direction;
import com.borzacchiello.advent_of_code.model.task01.Rotation;

class Task01ParserTest {

    private Task01Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Task01Parser();
    }

    @Test
    void shouldParseValidRotations() throws InvalidTaskInputException {

        List<String> lines = List.of(
                "L68",
                "R48",
                "L5"
        );

        List<Rotation> result = parser.parse(lines);

        assertEquals(
                List.of(
                        new Rotation(Direction.LEFT, 68),
                        new Rotation(Direction.RIGHT, 48),
                        new Rotation(Direction.LEFT, 5)
                ),
                result
        );
    }
}

