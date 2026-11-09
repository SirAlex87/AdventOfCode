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
import com.borzacchiello.advent_of_code.strategy.impl.task06.StandardWorksheetCalculator;
import com.borzacchiello.advent_of_code.strategy.impl.task06.WorksheetProblemExtractor;

class StandardWorksheetCalculatorTest {

    private Task06Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Task06Parser();
    }

    @ParameterizedTest
    @MethodSource("provideProblems")
    void shouldCalculateStandardProblem(
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
                StandardWorksheetCalculator.calculate(
                        data,
                        problem);

        assertEquals(
                new BigInteger(expected),
                result);
    }

    private static Stream<Arguments> provideProblems() {

        return Stream.of(
                // 123 + 456.
                Arguments.of(
                        List.of(
                                "123",
                                "456",
                                "+  "),
                        MathOperation.ADD,
                        "579"),

                // 123 * 456.
                Arguments.of(
                        List.of(
                                "123",
                                "456",
                                "*  "),
                        MathOperation.MULTIPLY,
                        "56088"),

                // Gli spazi servono solamente
                // per l'allineamento.
                Arguments.of(
                        List.of(
                                "  10",
                                "   5",
                                "+   "),
                        MathOperation.ADD,
                        "15"),

                Arguments.of(
                        List.of(
                                "  12",
                                "   3",
                                "*   "),
                        MathOperation.MULTIPLY,
                        "36")
        );
    }

    @ParameterizedTest
    @MethodSource("provideStandardSpacingCases")
    void shouldIgnoreAlignmentSpaces(
            List<String> input,
            String expected) {

        WorksheetData data =
                parser.parse(input);

        WorksheetProblem problem =
                WorksheetProblemExtractor
                        .extract(data)
                        .getFirst();

        BigInteger result =
                StandardWorksheetCalculator.calculate(
                        data,
                        problem);

        assertEquals(
                new BigInteger(expected),
                result);
    }

    private static Stream<Arguments>
            provideStandardSpacingCases() {

        return Stream.of(
                Arguments.of(
                        List.of(
                                "123",
                                "456",
                                "+  "),
                        "579"),

                Arguments.of(
                        List.of(
                                " 123",
                                " 456",
                                "+   "),
                        "579"),

                Arguments.of(
                        List.of(
                                "123 ",
                                "456 ",
                                "+   "),
                        "579")
        );
    }

    @Test
    void shouldSupportVeryLargeNumbers() {

        WorksheetData data = parser.parse(
                List.of(
                        "999999999999999999999999999999",
                        "999999999999999999999999999999",
                        "+                             "));

        WorksheetProblem problem =
                WorksheetProblemExtractor
                        .extract(data)
                        .getFirst();

        BigInteger result =
                StandardWorksheetCalculator.calculate(
                        data,
                        problem);

        assertEquals(
                new BigInteger(
                        "1999999999999999999999999999998"),
                result);
    }
}