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
import com.borzacchiello.advent_of_code.strategy.impl.task01.Task01Part1Strategy;
import com.borzacchiello.advent_of_code.util.TestFileUtils;

class Task01Part1StrategyTest {

    private Task01Part1Strategy strategy;
    private Task01Parser parser;

    @BeforeEach
    void setUp() {
        strategy = new Task01Part1Strategy();
        parser = new Task01Parser();
    }

    @Test
    void shouldReturnThreeForOfficialExample() throws InvalidTaskInputException {

        List<String> lines =
                TestFileUtils.readLines("task01/prova1Test.txt");

        List<Rotation> rotations =
                parser.parse(lines);

        Integer result =
                strategy.execute(rotations);

        assertEquals(3, result);
    }

    @Test
    void shouldReturnExpectedResultForRealInput() throws InvalidTaskInputException {

        List<String> lines =
                TestFileUtils.readLines("task01/prova1.txt");

        List<Rotation> rotations =
                parser.parse(lines);

        Integer result =
                strategy.execute(rotations);

        assertEquals(1180, result);
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
                        "Rotation ending exactly at zero",
                        List.of(
                                new Rotation(
                                        Direction.RIGHT,
                                        50
                                )
                        ),
                        1
                ),

                Arguments.of(
                        "Wrap around rotating right and finish at zero",
                        List.of(
                                new Rotation(
                                        Direction.RIGHT,
                                        55
                                ),
                                new Rotation(
                                        Direction.RIGHT,
                                        95
                                )
                        ),
                        1
                ),

                Arguments.of(
                        "Wrap around rotating left and finish at zero",
                        List.of(
                                new Rotation(
                                        Direction.LEFT,
                                        55
                                ),
                                new Rotation(
                                        Direction.LEFT,
                                        95
                                )
                        ),
                        1
                ),

                Arguments.of(
                        "Cross zero without ending at zero",
                        List.of(
                                new Rotation(
                                        Direction.RIGHT,
                                        100
                                )
                        ),
                        0
                ),

                Arguments.of(
                        "Distance greater than dial size ending at zero",
                        List.of(
                                new Rotation(
                                        Direction.RIGHT,
                                        250
                                )
                        ),
                        1
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
