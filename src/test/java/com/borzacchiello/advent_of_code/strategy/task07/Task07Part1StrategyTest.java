package com.borzacchiello.advent_of_code.strategy.task07;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.model.task07.TachyonManifold;
import com.borzacchiello.advent_of_code.parser.task07.Task07Parser;
import com.borzacchiello.advent_of_code.strategy.impl.task07.Task07Part1Strategy;
import com.borzacchiello.advent_of_code.util.TestFileUtils;

class Task07Part1StrategyTest {

    private Task07Part1Strategy strategy;
    private Task07Parser parser;

    @BeforeEach
    void setUp() {
        strategy = new Task07Part1Strategy();
        parser = new Task07Parser();
    }

    @Test
    void shouldReturnCorrectTaskKey() {

        assertEquals(
                new TaskKey(7, 1),
                strategy.getKey());
    }

    @Test
    void shouldReturnExpectedResultForOfficialExample() {

        List<String> lines =
                TestFileUtils.readLines(
                        "task07/prova7Test.txt");

        TachyonManifold manifold =
                parser.parse(lines);

        Integer result =
                strategy.execute(manifold);

        assertEquals(
                21,
                result);
    }

    @Test
    void shouldReturnExpectedResultForRealInput() {

        List<String> lines =
                TestFileUtils.readLines(
                        "task07/prova7.txt");

        TachyonManifold manifold =
                parser.parse(lines);

        Integer result =
                strategy.execute(manifold);

        assertEquals(
                1555,
                result);
    }
}
