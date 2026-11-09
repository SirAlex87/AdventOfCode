package com.borzacchiello.advent_of_code.strategy.task10;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.model.task10.MachineData;
import com.borzacchiello.advent_of_code.parser.task10.Task10Parser;
import com.borzacchiello.advent_of_code.strategy.impl.task10.Task10Part2Strategy;
import com.borzacchiello.advent_of_code.util.TestFileUtils;

class Task10Part2StrategyTest {

    private Task10Part2Strategy strategy;
    private Task10Parser parser;

    @BeforeEach
    void setUp() {
        strategy = new Task10Part2Strategy();
        parser = new Task10Parser();
    }

    @Test
    void shouldReturnCorrectTaskKey() {

        assertEquals(
                new TaskKey(10, 2),
                strategy.getKey());
    }

    @Test
    void shouldReturnExpectedResultForOfficialExample() {

        List<String> lines =
                TestFileUtils.readLines(
                        "task10/prova10Test.txt");

        MachineData data =
                parser.parse(lines);

        BigInteger result =
                strategy.execute(data);

        assertEquals(
                new BigInteger("33"),
                result);
    }

    @Test
    void shouldReturnExpectedResultForRealInput() {

        List<String> lines =
                TestFileUtils.readLines(
                        "task10/prova10.txt");

        MachineData data =
                parser.parse(lines);

        BigInteger result =
                strategy.execute(data);

        assertEquals(
                new BigInteger("16613"),
                result);
    }
}
