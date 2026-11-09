package com.borzacchiello.advent_of_code.strategy.task03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.model.task03.BatteryBank;
import com.borzacchiello.advent_of_code.parser.task03.Task03Parser;
import com.borzacchiello.advent_of_code.strategy.impl.task03.Task03Part1Strategy;
import com.borzacchiello.advent_of_code.util.TestFileUtils;

class Task03Part1StrategyTest {

    private Task03Part1Strategy strategy;
    private Task03Parser parser;

    @BeforeEach
    void setUp() {
        strategy = new Task03Part1Strategy();
        parser = new Task03Parser();
    }

    @Test
    void shouldReturnCorrectTaskKey() {

        assertEquals(
                new TaskKey(3, 1),
                strategy.getKey());
    }

    @Test
    void shouldReturnExpectedResultForOfficialExample() {

        List<String> lines =
                TestFileUtils.readLines("task03/prova3Test.txt");

        List<BatteryBank> banks =
                parser.parse(lines);

        BigInteger result =
                strategy.execute(banks);

        assertEquals(
                new BigInteger("357"),
                result);
    }

    @Test
    void shouldReturnExpectedResultForRealInput() {

        List<String> lines =
                TestFileUtils.readLines("task03/prova3.txt");

        List<BatteryBank> banks =
                parser.parse(lines);

        BigInteger result =
                strategy.execute(banks);

        assertEquals(
                new BigInteger("17445"),
                result);
    }

    @Test
    void shouldReturnZeroForEmptyList() {

        BigInteger result =
                strategy.execute(List.of());

        assertEquals(
                BigInteger.ZERO,
                result);
    }
}