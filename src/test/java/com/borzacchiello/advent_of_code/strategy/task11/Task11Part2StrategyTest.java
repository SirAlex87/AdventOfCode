package com.borzacchiello.advent_of_code.strategy.task11;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.model.task11.DeviceGraph;
import com.borzacchiello.advent_of_code.parser.task11.Task11Parser;
import com.borzacchiello.advent_of_code.strategy.impl.task11.Task11Part2Strategy;
import com.borzacchiello.advent_of_code.util.TestFileUtils;

class Task11Part2StrategyTest {

    private Task11Part2Strategy strategy;
    private Task11Parser parser;

    @BeforeEach
    void setUp() {
        strategy = new Task11Part2Strategy();
        parser = new Task11Parser();
    }

    @Test
    void shouldReturnCorrectTaskKey() {

        assertEquals(
                new TaskKey(11, 2),
                strategy.getKey());
    }

    @Test
    void shouldReturnExpectedResultForOfficialExample() {

        List<String> lines =
                TestFileUtils.readLines(
                        "task11/prova11TestParte2.txt");

        DeviceGraph graph =
                parser.parse(lines);

        BigInteger result =
                strategy.execute(graph);

        assertEquals(
                new BigInteger("2"),
                result);
    }

    @Test
    void shouldReturnExpectedResultForRealInput() {

        List<String> lines =
                TestFileUtils.readLines(
                        "task11/prova11.txt");

        DeviceGraph graph =
                parser.parse(lines);

        BigInteger result =
                strategy.execute(graph);

        assertEquals(
                new BigInteger(
                        "337433554149492"),
                result);
    }
}