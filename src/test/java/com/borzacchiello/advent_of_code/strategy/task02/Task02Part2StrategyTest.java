package com.borzacchiello.advent_of_code.strategy.task02;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.borzacchiello.advent_of_code.model.task02.ProductIdRange;
import com.borzacchiello.advent_of_code.parser.task02.Task02Parser;
import com.borzacchiello.advent_of_code.strategy.impl.task02.Task02Part2Strategy;
import com.borzacchiello.advent_of_code.util.TestFileUtils;

class Task02Part2StrategyTest {

    private Task02Part2Strategy strategy;
    private Task02Parser parser;

    @BeforeEach
    void setUp() {
        strategy = new Task02Part2Strategy();
        parser = new Task02Parser();
    }

    @Test
    void shouldReturnExpectedResultForOfficialExample() {

        List<String> lines =
                TestFileUtils.readLines("task02/prova2Test.txt");

        List<ProductIdRange> ranges =
                parser.parse(lines);

        BigInteger result =
                strategy.execute(ranges);

        assertEquals(
                new BigInteger("4174379265"),
                result);
    }

    @Test
    void shouldReturnExpectedResultForRealInput() {

        List<String> lines =
                TestFileUtils.readLines("task02/prova2.txt");

        List<ProductIdRange> ranges =
                parser.parse(lines);

        BigInteger result =
                strategy.execute(ranges);

        assertEquals(
                new BigInteger("25663320831"),
                result);
    }

    @Test
    void shouldReturnZeroForEmptyList() {

        BigInteger result =
                strategy.execute(List.of());

        assertEquals(BigInteger.ZERO, result);
    }
}