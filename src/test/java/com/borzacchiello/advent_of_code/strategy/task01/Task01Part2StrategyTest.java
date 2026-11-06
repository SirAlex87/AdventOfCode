package com.borzacchiello.advent_of_code.strategy.task01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.task01.Direction;
import com.borzacchiello.advent_of_code.model.task01.Rotation;
import com.borzacchiello.advent_of_code.parser.task01.Task01Parser;
import com.borzacchiello.advent_of_code.strategy.impl.task01.Task01Part2Strategy;
import com.borzacchiello.advent_of_code.util.TestFileUtils;

class Task01Part2StrategyTest {

    private Task01Part2Strategy strategy;
    private Task01Parser parser;

    @BeforeEach
    void setUp() {
        strategy = new Task01Part2Strategy();
        parser = new Task01Parser();
    }

    @Test
    void shouldReturnSixForOfficialExample() throws InvalidTaskInputException {

        List<String> lines =
                TestFileUtils.readLines(
                        "task01/prova1Test.txt"
                );

        List<Rotation> rotations =
                parser.parse(lines);

        Integer result =
                strategy.execute(rotations);

        assertEquals(6, result);
    }

    @Test
    void shouldReturnExpectedResultForRealInput() throws InvalidTaskInputException {

        List<String> lines =
                TestFileUtils.readLines(
                        "task01/prova1.txt"
                );

        List<Rotation> rotations =
                parser.parse(lines);

        Integer result =
                strategy.execute(rotations);

        assertEquals(6892, result);
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("rotationScenarios")
    void shouldCalculateCorrectZeroCount(
            String scenario,
            List<Rotation> rotations,
            int expected) {

        Integer result =
                strategy.execute(rotations);

        assertEquals(expected, result);
    }

    private static Stream<Arguments> rotationScenarios() {

        return Stream.of(

                Arguments.of(
                        "Right rotation ending exactly at zero",
                        List.of(
                                new Rotation(Direction.RIGHT, 50)
                        ),
                        1
                ),

                Arguments.of(
                        "Left rotation ending exactly at zero",
                        List.of(
                                new Rotation(Direction.LEFT, 50)
                        ),
                        1
                ),

                Arguments.of(
                        "One complete clockwise rotation crosses zero once",
                        List.of(
                                new Rotation(Direction.RIGHT, 100)
                        ),
                        1
                ),

                Arguments.of(
                        "One complete counter-clockwise rotation crosses zero once",
                        List.of(
                                new Rotation(Direction.LEFT, 100)
                        ),
                        1
                ),

                Arguments.of(
                        "Ten complete clockwise rotations cross zero ten times",
                        List.of(
                                new Rotation(Direction.RIGHT, 1000)
                        ),
                        10
                ),

                Arguments.of(
                        "Ten complete counter-clockwise rotations cross zero ten times",
                        List.of(
                                new Rotation(Direction.LEFT, 1000)
                        ),
                        10
                ),

                Arguments.of(
                        "Rotation crosses zero but does not finish at zero",
                        List.of(
                                new Rotation(Direction.RIGHT, 60)
                        ),
                        1
                ),

                Arguments.of(
                        "Large rotation ending at zero",
                        List.of(
                                new Rotation(Direction.RIGHT, 250)
                        ),
                        3
                ),

                Arguments.of(
                        "Rotation does not reach zero",
                        List.of(
                                new Rotation(Direction.RIGHT, 10)
                        ),
                        0
                )
        );
    }

    @Test
    void shouldReturnZeroWhenNoRotationsAreProvided() {

        Integer result =
                strategy.execute(List.of());

        assertEquals(0, result);
    }

    @Test
    void shouldThrowExceptionWhenRotationsAreNull() {

        assertThrows(
                IllegalArgumentException.class,
                () -> strategy.execute(null)
        );
    }
}
