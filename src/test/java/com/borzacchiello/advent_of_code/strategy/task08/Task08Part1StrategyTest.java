package com.borzacchiello.advent_of_code.strategy.task08;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.model.task08.JunctionBoxData;
import com.borzacchiello.advent_of_code.parser.task08.Task08Parser;
import com.borzacchiello.advent_of_code.strategy.impl.task08.Task08Part1Strategy;
import com.borzacchiello.advent_of_code.util.TestFileUtils;

class Task08Part1StrategyTest {

    private Task08Part1Strategy strategy;
    private Task08Parser parser;

    @BeforeEach
    void setUp() {

        strategy =
                new Task08Part1Strategy();

        parser =
                new Task08Parser();
    }

    @Test
    void shouldReturnCorrectTaskKey() {

        assertEquals(
                new TaskKey(8, 1),
                strategy.getKey());
    }

    @Test
    void shouldReturnExpectedResultForOfficialExample() {

        List<String> lines =
                TestFileUtils.readLines(
                        "task08/prova8Test.txt");

        JunctionBoxData data =
                parser.parse(lines);

        BigInteger result =
                strategy.calculate(
                        data,
                        10);

        assertEquals(
                new BigInteger("40"),
                result);
    }

    @Test
    void shouldReturnExpectedResultForRealInput() {

        List<String> lines =
                TestFileUtils.readLines(
                        "task08/prova8.txt");

        JunctionBoxData data =
                parser.parse(lines);

        BigInteger result =
                strategy.execute(data);

        assertEquals(
                new BigInteger("69192"),
                result);
    }
}
