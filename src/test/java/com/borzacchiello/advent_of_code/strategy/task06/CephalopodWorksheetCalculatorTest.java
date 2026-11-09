package com.borzacchiello.advent_of_code.strategy.task06;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.borzacchiello.advent_of_code.model.task06.MathOperation;
import com.borzacchiello.advent_of_code.model.task06.WorksheetData;
import com.borzacchiello.advent_of_code.model.task06.WorksheetProblem;
import com.borzacchiello.advent_of_code.parser.task06.Task06Parser;
import com.borzacchiello.advent_of_code.strategy.impl.task06.CephalopodWorksheetCalculator;
import com.borzacchiello.advent_of_code.strategy.impl.task06.WorksheetProblemExtractor;

class CephalopodWorksheetCalculatorTest {

    private Task06Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Task06Parser();
    }

    @ParameterizedTest
    @MethodSource("provideProblems")
    void shouldCalculateCephalopodProblem(
            List<String> input,
            MathOperation operation,
            String expected) {

        WorksheetData data =
                parser.parse(input);

        WorksheetProblem problem =
                new WorksheetProblem(
                        0,
                        data.columnCount() - 1,
                        operation);

        BigInteger result =
                CephalopodWorksheetCalculator.calculate(
                        data,
                        problem);

        assertEquals(
                new BigInteger(expected),
                result);
    }

    private static Stream<Arguments> provideProblems() {

        return Stream.of(
                // Lettura verticale:
                //
                // 123
                // 456
                //
                // produce 14, 25 e 36.
                //
                // 14 + 25 + 36 = 75.
                Arguments.of(
                        List.of(
                                "123",
                                "456",
                                "+  "),
                        MathOperation.ADD,
                        "75"),

                // 14 * 25 * 36 = 12600.
                Arguments.of(
                        List.of(
                                "123",
                                "456",
                                "*  "),
                        MathOperation.MULTIPLY,
                        "12600"),

                // Gli spazi non diventano cifre.
                //
                // 12_
                // _34
                //
                // Colonne:
                // 1
                // 23
                // 4
                //
                // Totale = 28.
                Arguments.of(
                        List.of(
                                "12 ",
                                " 34",
                                "+  "),
                        MathOperation.ADD,
                        "28")
        );
    }

    @Test
    void shouldPreserveColumnAlignment() {

        WorksheetData data = parser.parse(
                List.of(
                        "12 ",
                        " 34",
                        "+  "));

        WorksheetProblem problem =
                WorksheetProblemExtractor
                        .extract(data)
                        .getFirst();

        BigInteger result =
                CephalopodWorksheetCalculator.calculate(
                        data,
                        problem);

        // Le colonne producono 1, 23 e 4.
        assertEquals(
                new BigInteger("28"),
                result);
    }

    @Test
    void shouldMultiplyColumnNumbers() {

        WorksheetData data = parser.parse(
                List.of(
                        "123",
                        "456",
                        "*  "));

        WorksheetProblem problem =
                WorksheetProblemExtractor
                        .extract(data)
                        .getFirst();

        BigInteger result =
                CephalopodWorksheetCalculator.calculate(
                        data,
                        problem);

        // Le colonne producono 14, 25 e 36.
        //
        // 14 * 25 * 36 = 12600.
        assertEquals(
                new BigInteger("12600"),
                result);
    }
}