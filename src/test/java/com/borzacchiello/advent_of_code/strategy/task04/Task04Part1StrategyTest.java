package com.borzacchiello.advent_of_code.strategy.task04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.model.task04.PaperRollGrid;
import com.borzacchiello.advent_of_code.parser.task04.Task04Parser;
import com.borzacchiello.advent_of_code.strategy.impl.task04.Task04Part1Strategy;
import com.borzacchiello.advent_of_code.util.TestFileUtils;

class Task04Part1StrategyTest {

    private Task04Part1Strategy strategy;
    private Task04Parser parser;

    @BeforeEach
    void setUp() {
        strategy = new Task04Part1Strategy();
        parser = new Task04Parser();
    }

    @Test
    void shouldReturnCorrectTaskKey() {

        assertEquals(
                new TaskKey(4, 1),
                strategy.getKey());
    }

    @Test
    void shouldReturnExpectedResultForOfficialExample() {

        List<String> lines =
                TestFileUtils.readLines(
                        "task04/prova4Test.txt");

        PaperRollGrid grid =
                parser.parse(lines);

        Integer result =
                strategy.execute(grid);

        assertEquals(13, result);
    }

    @Test
    void shouldReturnExpectedResultForRealInput() {

        List<String> lines =
                TestFileUtils.readLines(
                        "task04/prova4.txt");

        PaperRollGrid grid =
                parser.parse(lines);

        Integer result =
                strategy.execute(grid);

        assertEquals(1543, result);
    }

    @Test
    void shouldReturnZeroWhenGridContainsNoPaperRolls() {

        PaperRollGrid grid = parser.parse(
                List.of(
                        "...",
                        "...",
                        "..."));

        Integer result =
                strategy.execute(grid);

        assertEquals(0, result);
    }

    @Test
    void shouldReturnOneForSinglePaperRoll() {

        PaperRollGrid grid =
                parser.parse(List.of("@"));

        Integer result =
                strategy.execute(grid);

        assertEquals(1, result);
    }
}