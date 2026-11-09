package com.borzacchiello.advent_of_code.parser.task05;

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
import com.borzacchiello.advent_of_code.model.task05.IngredientData;
import com.borzacchiello.advent_of_code.model.task05.IngredientIdRange;

class Task05ParserTest {

    private Task05Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Task05Parser();
    }

    @Test
    void shouldReturnCorrectTaskNumber() {

        assertEquals(
                5,
                parser.getTaskNumber());
    }

    @Test
    void shouldParseOfficialExampleStructure() {

        List<String> lines = List.of(
                "3-5",
                "10-14",
                "16-20",
                "12-18",
                "",
                "1",
                "5",
                "8",
                "11",
                "17",
                "32");

        IngredientData data =
                parser.parse(lines);

        assertEquals(
                4,
                data.freshRanges().size());

        assertEquals(
                6,
                data.availableIngredientIds().size());

        assertEquals(
                range(3, 5),
                data.freshRanges().get(0));

        assertEquals(
                range(12, 18),
                data.freshRanges().get(3));

        assertEquals(
                BigInteger.ONE,
                data.availableIngredientIds().get(0));

        assertEquals(
                BigInteger.valueOf(32),
                data.availableIngredientIds().get(5));
    }

    @ParameterizedTest
    @MethodSource("provideSingleRangeCases")
    void shouldParseFreshRange(
            String input,
            String expectedStart,
            String expectedEnd) {

        IngredientData data =
                parser.parse(
                        List.of(
                                input,
                                "",
                                "100"));

        assertEquals(
                1,
                data.freshRanges().size());

        IngredientIdRange range =
                data.freshRanges().getFirst();

        assertEquals(
                new BigInteger(expectedStart),
                range.start());

        assertEquals(
                new BigInteger(expectedEnd),
                range.end());
    }

    private static Stream<Arguments>
            provideSingleRangeCases() {

        return Stream.of(
                Arguments.of(
                        "3-5",
                        "3",
                        "5"),

                Arguments.of(
                        "10-14",
                        "10",
                        "14"),

                Arguments.of(
                        "100-100",
                        "100",
                        "100"),

                Arguments.of(
                        "100000000000000000000"
                                + "-"
                                + "200000000000000000000",
                        "100000000000000000000",
                        "200000000000000000000")
        );
    }

    @ParameterizedTest
    @MethodSource("provideRangesWithSpaces")
    void shouldStripRangeValues(
            String input,
            String expectedStart,
            String expectedEnd) {

        IngredientData data =
                parser.parse(
                        List.of(
                                input,
                                "",
                                "100"));

        IngredientIdRange range =
                data.freshRanges().getFirst();

        assertEquals(
                new BigInteger(expectedStart),
                range.start());

        assertEquals(
                new BigInteger(expectedEnd),
                range.end());
    }

    private static Stream<Arguments>
            provideRangesWithSpaces() {

        return Stream.of(
                Arguments.of(
                        " 3-5 ",
                        "3",
                        "5"),

                Arguments.of(
                        "10 - 14",
                        "10",
                        "14"),

                Arguments.of(
                        " 16 - 20 ",
                        "16",
                        "20")
        );
    }

    @Test
    void shouldParseAvailableIngredientIds() {

        IngredientData data =
                parser.parse(
                        List.of(
                                "10-20",
                                "",
                                "1",
                                "5",
                                "8",
                                "11",
                                "17",
                                "32"));

        assertEquals(
                List.of(
                        BigInteger.valueOf(1),
                        BigInteger.valueOf(5),
                        BigInteger.valueOf(8),
                        BigInteger.valueOf(11),
                        BigInteger.valueOf(17),
                        BigInteger.valueOf(32)),
                data.availableIngredientIds());
    }

    @Test
    void shouldAllowNoAvailableIds() {

        IngredientData data =
                parser.parse(
                        List.of(
                                "10-20",
                                ""));

        assertEquals(
                List.of(
                        range(10, 20)),
                data.freshRanges());

        assertEquals(
                List.of(),
                data.availableIngredientIds());
    }

    @Test
    void shouldAllowOverlappingRanges() {

        IngredientData data =
                parser.parse(
                        List.of(
                                "10-20",
                                "15-25",
                                "18-30",
                                "",
                                "20"));

        assertEquals(
                List.of(
                        range(10, 20),
                        range(15, 25),
                        range(18, 30)),
                data.freshRanges());

        // Il parser non deve unire gli intervalli.
        // Questa responsabilità appartiene a IngredientRangeMerger.
        assertEquals(
                3,
                data.freshRanges().size());
    }

    @Test
    void shouldAllowAdjacentRanges() {

        IngredientData data =
                parser.parse(
                        List.of(
                                "10-15",
                                "16-20",
                                "",
                                "15"));

        assertEquals(
                List.of(
                        range(10, 15),
                        range(16, 20)),
                data.freshRanges());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "ABC-20",
            "10-ABC",
            "ABC-XYZ",
            "10",
            "10-20-30",
            "10/",
            "10:20"
    })
    void shouldRejectMalformedFreshRange(
            String input) {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(
                        List.of(
                                input,
                                "",
                                "15")));
    }

    @Test
    void shouldRejectRangeWithoutStart() {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(
                        List.of(
                                "-20",
                                "",
                                "15")));
    }

    @Test
    void shouldRejectRangeWithoutEnd() {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(
                        List.of(
                                "10-",
                                "",
                                "15")));
    }

    @Test
    void shouldRejectRangeWhenStartIsGreaterThanEnd() {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(
                        List.of(
                                "20-10",
                                "",
                                "15")));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "ABC",
            "12A",
            "1.5",
            "10-20"
    })
    void shouldRejectInvalidAvailableIngredientId(
            String input) {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(
                        List.of(
                                "10-20",
                                "",
                                input)));
    }

    @Test
    void shouldRejectMissingSectionSeparator() {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(
                        List.of(
                                "10-20",
                                "30-40")));
    }

    @Test
    void shouldRejectInputWithoutFreshRanges() {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(
                        List.of(
                                "",
                                "10",
                                "20")));
    }

    @Test
    void shouldRejectEmptyInput() {

        assertThrows(
                InvalidTaskInputException.class,
                () -> parser.parse(List.of()));
    }

    private static IngredientIdRange range(
            long start,
            long end) {

        return new IngredientIdRange(
                BigInteger.valueOf(start),
                BigInteger.valueOf(end));
    }
}