package com.borzacchiello.advent_of_code.strategy.task09;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.borzacchiello.advent_of_code.model.task09.TilePosition;
import com.borzacchiello.advent_of_code.strategy.impl.task09.RectangleAreaCalculator;

class RectangleAreaCalculatorTest {

    @ParameterizedTest
    @MethodSource("provideTileCases")
    void shouldFindLargestArea(
            List<TilePosition> tiles,
            String expected) {

        BigInteger result =
                RectangleAreaCalculator
                        .findLargestArea(tiles);

        assertEquals(
                new BigInteger(expected),
                result);
    }

    private static Stream<Arguments>
            provideTileCases() {

        return Stream.of(

                // Da 2,5 a 11,1:
                //
                // larghezza = 11 - 2 + 1 = 10
                // altezza   = 5 - 1 + 1 = 5
                //
                // area = 50.
                Arguments.of(
                        List.of(
                                tile(2, 5),
                                tile(11, 1)),
                        "50"),

                // Rettangolo 5 x 7.
                Arguments.of(
                        List.of(
                                tile(7, 1),
                                tile(11, 7)),
                        "35"),

                // Rettangolo sottile orizzontale:
                // 7,3 -> 2,3
                //
                // area = 6 x 1 = 6.
                Arguments.of(
                        List.of(
                                tile(7, 3),
                                tile(2, 3)),
                        "6"),

                // Rettangolo formato da due tile adiacenti.
                Arguments.of(
                        List.of(
                                tile(10, 10),
                                tile(11, 10)),
                        "2"),

                // Fra più coppie deve selezionare
                // quella con area maggiore.
                Arguments.of(
                        List.of(
                                tile(0, 0),
                                tile(1, 1),
                                tile(9, 9)),
                        "100")
        );
    }

    private static TilePosition tile(
            long x,
            long y) {

        return new TilePosition(x, y);
    }
}
