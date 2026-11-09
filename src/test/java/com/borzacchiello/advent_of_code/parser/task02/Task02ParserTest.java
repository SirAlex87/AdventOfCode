package com.borzacchiello.advent_of_code.parser.task02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.task02.ProductIdRange;

class Task02ParserTest {

    private Task02Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Task02Parser();
    }

    @Test
    void shouldReturnCorrectTaskNumber() {
        assertEquals(2, parser.getTaskNumber());
    }

    @ParameterizedTest
    @MethodSource("provideValidSingleRanges")
    void shouldParseSingleRange(
            String input,
            String expectedStart,
            String expectedEnd) {

        List<ProductIdRange> result =
                parser.parse(List.of(input));

        assertEquals(1, result.size());

        ProductIdRange range = result.getFirst();

        assertEquals(
                new BigInteger(expectedStart),
                range.start());

        assertEquals(
                new BigInteger(expectedEnd),
                range.end());
    }

    private static Stream<Arguments> provideValidSingleRanges() {
        return Stream.of(
                Arguments.of("11-22", "11", "22"),
                Arguments.of("95-115", "95", "115"),
                Arguments.of("998-1012", "998", "1012"),
                Arguments.of(
                        "1188511880-1188511890",
                        "1188511880",
                        "1188511890"),
                Arguments.of(
                        "2121212118-2121212124",
                        "2121212118",
                        "2121212124")
        );
    }

    @ParameterizedTest
    @MethodSource("provideRangesWithSpaces")
    void shouldStripSpacesFromRange(
            String input,
            String expectedStart,
            String expectedEnd) {

        List<ProductIdRange> result =
                parser.parse(List.of(input));

        ProductIdRange range = result.getFirst();

        assertEquals(
                new BigInteger(expectedStart),
                range.start());

        assertEquals(
                new BigInteger(expectedEnd),
                range.end());
    }

    private static Stream<Arguments> provideRangesWithSpaces() {
        return Stream.of(
                Arguments.of(
                        " 11-22 ",
                        "11",
                        "22"),
                Arguments.of(
                        "95 - 115",
                        "95",
                        "115"),
                Arguments.of(
                        " 998 - 1012 ",
                        "998",
                        "1012")
        );
    }

    @Test
    void shouldParseMultipleRanges() {

        List<ProductIdRange> result =
                parser.parse(
                        List.of(
                                "11-22,95-115,998-1012"));

        assertEquals(3, result.size());

        assertEquals(
                new ProductIdRange(
                        new BigInteger("11"),
                        new BigInteger("22")),
                result.get(0));

        assertEquals(
                new ProductIdRange(
                        new BigInteger("95"),
                        new BigInteger("115")),
                result.get(1));

        assertEquals(
                new ProductIdRange(
                        new BigInteger("998"),
                        new BigInteger("1012")),
                result.get(2));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            " ",
            "   ",
            "\t"
    })
    void shouldIgnoreBlankInput(String input) {

        List<ProductIdRange> result =
                parser.parse(List.of(input));

        assertEquals(0, result.size());
    }

    @Test
    void shouldIgnoreEmptyRangesBetweenCommas() {

        List<ProductIdRange> result =
                parser.parse(
                        List.of(
                                "11-22,,95-115, ,998-1012"));

        assertEquals(3, result.size());

        assertEquals(
                new ProductIdRange(
                        new BigInteger("11"),
                        new BigInteger("22")),
                result.get(0));

        assertEquals(
                new ProductIdRange(
                        new BigInteger("95"),
                        new BigInteger("115")),
                result.get(1));

        assertEquals(
                new ProductIdRange(
                        new BigInteger("998"),
                        new BigInteger("1012")),
                result.get(2));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "ABC-115",
            "95-ABC",
            "ABC-XYZ",
            "95",
            "95-115-120"
    })
    void shouldThrowExceptionForMalformedRange(String input) {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(List.of(input)));
    }

    @ParameterizedTest
    @MethodSource("provideReversedRanges")
    void shouldThrowExceptionWhenStartIsGreaterThanEnd(
            String input) {

        InvalidTaskInputException exception =
                assertThrows(
                        InvalidTaskInputException.class,
                        () -> parser.parse(List.of(input)));

        assertEquals(
                "Invalid product ID range: start is greater than end",
                exception.getMessage());
    }

    private static Stream<Arguments> provideReversedRanges() {
        return Stream.of(
                Arguments.of("22-11"),
                Arguments.of("115-95"),
                Arguments.of("1012-998"),
                Arguments.of(
                        "1188511890-1188511880")
        );
    }

    @Test
    void shouldAllowRangeWithSameStartAndEnd() {

        List<ProductIdRange> result =
                parser.parse(List.of("99-99"));

        assertEquals(1, result.size());

        assertEquals(
                new ProductIdRange(
                        new BigInteger("99"),
                        new BigInteger("99")),
                result.getFirst());
    }
}