package com.borzacchiello.advent_of_code.strategy.task09;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.model.task09.TileData;
import com.borzacchiello.advent_of_code.parser.task09.Task09Parser;
import com.borzacchiello.advent_of_code.strategy.impl.task09.Task09Part2Strategy;
import com.borzacchiello.advent_of_code.util.TestFileUtils;

class Task09Part2StrategyTest {

    private Task09Part2Strategy strategy;
    private Task09Parser parser;

    @BeforeEach
    void setUp() {
        strategy = new Task09Part2Strategy();
        parser = new Task09Parser();
    }

    @Test
    void shouldReturnCorrectTaskKey() {

        assertEquals(
                new TaskKey(9, 2),
                strategy.getKey());
    }

    @Test
    void shouldReturnExpectedResultForOfficialExample() {

        List<String> lines =
                TestFileUtils.readLines(
                        "task09/prova9Test.txt");

        TileData data =
                parser.parse(lines);

        BigInteger result =
                strategy.execute(data);

        assertEquals(
                new BigInteger("24"),
                result);
    }

    @Test
    void shouldReturnExpectedResultForRealInput() {

        List<String> lines =
                TestFileUtils.readLines(
                        "task09/prova9.txt");

        TileData data =
                parser.parse(lines);

        BigInteger result =
                strategy.execute(data);

        assertEquals(
                new BigInteger("1474699155"),
                result);
    }
}
