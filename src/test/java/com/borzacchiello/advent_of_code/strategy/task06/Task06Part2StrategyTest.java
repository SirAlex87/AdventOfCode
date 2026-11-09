package com.borzacchiello.advent_of_code.strategy.task06;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.model.task06.WorksheetData;
import com.borzacchiello.advent_of_code.parser.task06.Task06Parser;
import com.borzacchiello.advent_of_code.strategy.impl.task06.Task06Part1Strategy;
import com.borzacchiello.advent_of_code.strategy.impl.task06.Task06Part2Strategy;
import com.borzacchiello.advent_of_code.util.TestFileUtils;

class Task06Part2StrategyTest {

    private Task06Part2Strategy strategy;
    private Task06Parser parser;

    @BeforeEach
    void setUp() {
        strategy = new Task06Part2Strategy();
        parser = new Task06Parser();
    }

    @Test
    void shouldReturnCorrectTaskKey() {

        assertEquals(
                new TaskKey(6, 2),
                strategy.getKey());
    }

    @Test
    void shouldReturnExpectedResultForOfficialExample() {

        List<String> lines =
                TestFileUtils.readLines(
                        "task06/prova6Test.txt");

        WorksheetData data =
                parser.parse(lines);

        BigInteger result =
                strategy.execute(data);

        assertEquals(
                new BigInteger("3263827"),
                result);
    }

    @Test
    void shouldReturnExpectedResultForRealInput() {

        List<String> lines =
                TestFileUtils.readLines(
                        "task06/prova6.txt");

        WorksheetData data =
                parser.parse(lines);

        BigInteger result =
                strategy.execute(data);

        assertEquals(
                new BigInteger("9876636978528"),
                result);
    }
    
    @Test
    void shouldProduceDifferentResultsForStandardAndCephalopodReading() {

        Task06Parser parser = new Task06Parser();

        WorksheetData data = parser.parse(
                List.of(
                        "123",
                        "456",
                        "+  "));

        BigInteger part1 =
                new Task06Part1Strategy()
                        .execute(data);

        BigInteger part2 =
                new Task06Part2Strategy()
                        .execute(data);

        // Parte 1:
        // 123 + 456 = 579.
        assertEquals(
                new BigInteger("579"),
                part1);

        // Parte 2:
        // 14 + 25 + 36 = 75.
        assertEquals(
                new BigInteger("75"),
                part2);
    }
}