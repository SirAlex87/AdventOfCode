package com.borzacchiello.advent_of_code.strategy.task06;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.borzacchiello.advent_of_code.model.task06.MathOperation;
import com.borzacchiello.advent_of_code.model.task06.WorksheetData;
import com.borzacchiello.advent_of_code.model.task06.WorksheetProblem;
import com.borzacchiello.advent_of_code.parser.task06.Task06Parser;
import com.borzacchiello.advent_of_code.strategy.impl.task06.WorksheetProblemExtractor;

class WorksheetProblemExtractorTest {

    private Task06Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Task06Parser();
    }

    @Test
    void shouldExtractSingleProblem() {

        WorksheetData data = parser.parse(
                List.of(
                        "123",
                        "456",
                        "+  "));

        List<WorksheetProblem> problems =
                WorksheetProblemExtractor.extract(data);

        assertEquals(1, problems.size());

        assertEquals(
                new WorksheetProblem(
                        0,
                        2,
                        MathOperation.ADD),
                problems.getFirst());
    }

    @Test
    void shouldExtractMultipleProblems() {

        WorksheetData data = parser.parse(
                List.of(
                        "123  45",
                        "456  67",
                        "+    * "));

        List<WorksheetProblem> problems =
                WorksheetProblemExtractor.extract(data);

        assertEquals(2, problems.size());

        assertEquals(
                new WorksheetProblem(
                        0,
                        2,
                        MathOperation.ADD),
                problems.get(0));

        assertEquals(
                new WorksheetProblem(
                        5,
                        6,
                        MathOperation.MULTIPLY),
                problems.get(1));
    }

    @Test
    void shouldExtractProblemsSeparatedByMultipleBlankColumns() {

        WorksheetData data = parser.parse(
                List.of(
                        "123   45   67",
                        "456   78   89",
                        "+     *    + "));

        List<WorksheetProblem> problems =
                WorksheetProblemExtractor.extract(data);

        assertEquals(3, problems.size());

        assertEquals(
                MathOperation.ADD,
                problems.get(0).operation());

        assertEquals(
                MathOperation.MULTIPLY,
                problems.get(1).operation());

        assertEquals(
                MathOperation.ADD,
                problems.get(2).operation());
    }

    @Test
    void shouldNotTreatColumnAsSeparatorWhenOperatorRowUsesIt() {

        WorksheetData data = parser.parse(
                List.of(
                        "12 ",
                        "34 ",
                        "  +"));

        List<WorksheetProblem> problems =
                WorksheetProblemExtractor.extract(data);

        // La terza colonna è vuota nelle righe numeriche,
        // ma contiene l'operatore nell'ultima riga.
        // Non può quindi essere considerata un separatore.
        assertEquals(1, problems.size());

        assertEquals(
                MathOperation.ADD,
                problems.getFirst().operation());
    }
}