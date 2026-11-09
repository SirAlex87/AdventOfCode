package com.borzacchiello.advent_of_code.strategy.task03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.borzacchiello.advent_of_code.model.task03.BatteryBank;
import com.borzacchiello.advent_of_code.strategy.impl.task03.MaximumJoltageCalculator;

class MaximumJoltageCalculatorTest {

    @ParameterizedTest
    @MethodSource("providePart1Banks")
    void shouldCalculateMaximumJoltageSelectingTwoBatteries(
            String ratings,
            String expected) {

        BatteryBank bank = new BatteryBank(ratings);

        BigInteger result =
                MaximumJoltageCalculator.calculate(bank, 2);

        assertEquals(
                new BigInteger(expected),
                result);
    }

    private static Stream<Arguments> providePart1Banks() {

        return Stream.of(
                Arguments.of("987654321111111", "98"),
                Arguments.of("811111111111119", "89"),
                Arguments.of("234234234234278", "78"),
                Arguments.of("818181911112111", "92"),

                // Casi aggiuntivi
                Arguments.of("12345", "45"),
                Arguments.of("98765", "98"),
                Arguments.of("191", "91"),
                Arguments.of("119", "19"),
                Arguments.of("99", "99")
        );
    }

    @ParameterizedTest
    @MethodSource("providePart2Banks")
    void shouldCalculateMaximumJoltageSelectingTwelveBatteries(
            String ratings,
            String expected) {

        BatteryBank bank = new BatteryBank(ratings);

        BigInteger result =
                MaximumJoltageCalculator.calculate(bank, 12);

        assertEquals(
                new BigInteger(expected),
                result);
    }

    private static Stream<Arguments> providePart2Banks() {

        return Stream.of(
                Arguments.of(
                        "987654321111111",
                        "987654321111"),
                Arguments.of(
                        "811111111111119",
                        "811111111119"),
                Arguments.of(
                        "234234234234278",
                        "434234234278"),
                Arguments.of(
                        "818181911112111",
                        "888911112111"),

                // Esattamente 12 batterie:
                // devono essere selezionate tutte
                Arguments.of(
                        "123456789123",
                        "123456789123")
        );
    }

    @ParameterizedTest
    @MethodSource("provideInsufficientBanks")
    void shouldThrowExceptionWhenBankHasNotEnoughBatteries(
            String ratings,
            int batteriesToSelect) {

        BatteryBank bank = new BatteryBank(ratings);

        assertThrows(
                IllegalArgumentException.class,
                () -> MaximumJoltageCalculator.calculate(
                        bank,
                        batteriesToSelect));
    }

    private static Stream<Arguments> provideInsufficientBanks() {

        return Stream.of(
                Arguments.of("1", 2),
                Arguments.of("12345678912", 12)
        );
    }
}