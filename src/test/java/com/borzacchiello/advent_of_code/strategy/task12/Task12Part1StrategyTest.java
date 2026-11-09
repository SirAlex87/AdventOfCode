package com.borzacchiello.advent_of_code.strategy.task12;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.model.task12.PresentData;
import com.borzacchiello.advent_of_code.parser.task12.Task12Parser;
import com.borzacchiello.advent_of_code.strategy.impl.task12.Task12Part1Strategy;
import com.borzacchiello.advent_of_code.util.TestFileUtils;

class Task12Part1StrategyTest {

    private Task12Part1Strategy strategy;
    private Task12Parser parser;

    @BeforeEach
    void setUp() {
        strategy = new Task12Part1Strategy();
        parser = new Task12Parser();
    }

    @Test
    void shouldReturnCorrectTaskKey() {

        assertEquals(
                new TaskKey(12, 1),
                strategy.getKey());
    }

    @Test
    void shouldReturnExpectedResultForOfficialExample() {

        List<String> lines =
                TestFileUtils.readLines(
                        "task12/prova12Test.txt");

        PresentData data =
                parser.parse(lines);

        Integer result =
                strategy.execute(data);

        assertEquals(
                2,
                result);
    }

    @Test
    void shouldReturnExpectedResultForRealInput() {

        List<String> lines =
                TestFileUtils.readLines(
                        "task12/prova12.txt");

        PresentData data =
                parser.parse(lines);

        Integer result =
                strategy.execute(data);

        assertEquals(
                512,
                result);
    }
}