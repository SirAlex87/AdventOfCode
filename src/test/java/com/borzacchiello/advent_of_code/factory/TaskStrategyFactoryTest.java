package com.borzacchiello.advent_of_code.factory;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.borzacchiello.advent_of_code.exception.TaskNotFoundException;
import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.strategy.TaskStrategy;
import com.borzacchiello.advent_of_code.strategy.TaskStrategyFactory;
import com.borzacchiello.advent_of_code.strategy.impl.task01.Task01Part1Strategy;
import com.borzacchiello.advent_of_code.strategy.impl.task01.Task01Part2Strategy;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaskStrategyFactoryTest {

    private Task01Part1Strategy part1Strategy;
    private Task01Part2Strategy part2Strategy;
    private TaskStrategyFactory factory;

    @BeforeAll
    void setUp() {

        part1Strategy = new Task01Part1Strategy();
        part2Strategy = new Task01Part2Strategy();

        factory = new TaskStrategyFactory(
                List.of(
                        part1Strategy,
                        part2Strategy
                )
        );
    }

    @ParameterizedTest(name = "{index} - task {0}, part {1}")
    @MethodSource("strategyScenarios")
    void shouldReturnExpectedStrategy(
            int task,
            int part,
            TaskStrategy<?, ?> expected)
            throws TaskNotFoundException {

        TaskStrategy<?, ?> result =
                factory.getStrategy(
                        new TaskKey(task, part)
                );

        assertSame(expected, result);
    }

    Stream<Arguments> strategyScenarios() {

        return Stream.of(
                Arguments.of(
                        1,
                        1,
                        part1Strategy
                ),
                Arguments.of(
                        1,
                        2,
                        part2Strategy
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenStrategyDoesNotExist() {

        TaskKey unknownKey =
                new TaskKey(99, 99);

        assertThrows(
                TaskNotFoundException.class,
                () -> factory.getStrategy(unknownKey)
        );
    }

    @Test
    void shouldThrowExceptionWhenKeyIsNull() {

        assertThrows(
                IllegalArgumentException.class,
                () -> factory.getStrategy(null)
        );
    }
}