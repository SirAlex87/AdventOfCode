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
import com.borzacchiello.advent_of_code.util.TestFileUtils;

class Task06Part1StrategyTest {

    private Task06Part1Strategy strategy;
    private Task06Parser parser;

    @BeforeEach
    void setUp() {
        strategy = new Task06Part1Strategy();
        parser = new Task06Parser();
    }

    @Test
    void shouldReturnCorrectTaskKey() {

        assertEquals(
                new TaskKey(6, 1),
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
                new BigInteger("4277556"),
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
                new BigInteger("5322004718681"),
                result);
    }
}