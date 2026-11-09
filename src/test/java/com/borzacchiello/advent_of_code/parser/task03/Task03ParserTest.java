package com.borzacchiello.advent_of_code.parser.task03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.borzacchiello.advent_of_code.model.task03.BatteryBank;

class Task03ParserTest {

    private Task03Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Task03Parser();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "987654321111111",
            "811111111111119",
            "234234234234278",
            "818181911112111",
            "1",
            "999999999999"
    })
    void shouldParseValidBatteryBank(String input) {

        List<BatteryBank> result =
                parser.parse(List.of(input));

        assertEquals(1, result.size());
        assertEquals(input, result.getFirst().ratings());
    }

    @ParameterizedTest
    @MethodSource("provideValuesWithSpaces")
    void shouldStripSpacesFromBatteryBank(
            String input,
            String expected) {

        List<BatteryBank> result =
                parser.parse(List.of(input));

        assertEquals(1, result.size());
        assertEquals(expected, result.getFirst().ratings());
    }

    private static Stream<Arguments> provideValuesWithSpaces() {
        return Stream.of(
                Arguments.of(
                        " 987654321111111",
                        "987654321111111"),
                Arguments.of(
                        "811111111111119 ",
                        "811111111111119"),
                Arguments.of(
                        "  234234234234278  ",
                        "234234234234278")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            " ",
            "   ",
            "\t"
    })
    void shouldIgnoreBlankLines(String input) {

        List<BatteryBank> result =
                parser.parse(List.of(input));

        assertEquals(0, result.size());
    }

    @Test
    void shouldIgnoreNullLines() {

        List<String> lines =
                new java.util.ArrayList<>();

        lines.add(null);

        List<BatteryBank> result =
                parser.parse(lines);

        assertEquals(0, result.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "123A456",
            "123-456",
            "123 456",
            "123.456",
            "123_456",
            "012345"
    })
    void shouldThrowExceptionForInvalidBatteryBank(
            String input) {

        assertThrows(
                IllegalArgumentException.class,
                () -> parser.parse(List.of(input)));
    }

    @ParameterizedTest
    @MethodSource("provideMultipleBanks")
    void shouldParseMultipleBatteryBanks(
            List<String> input,
            List<String> expected) {

        List<BatteryBank> result =
                parser.parse(input);

        List<String> ratings = result.stream()
                .map(BatteryBank::ratings)
                .toList();

        assertEquals(expected, ratings);
    }

    private static Stream<Arguments> provideMultipleBanks() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                "987654321111111",
                                "811111111111119"),
                        List.of(
                                "987654321111111",
                                "811111111111119")),

                Arguments.of(
                        List.of(
                                "987654321111111",
                                "",
                                "234234234234278"),
                        List.of(
                                "987654321111111",
                                "234234234234278")),

                Arguments.of(
                        List.of(
                                " 987654321111111 ",
                                " 811111111111119 "),
                        List.of(
                                "987654321111111",
                                "811111111111119"))
        );
    }
}