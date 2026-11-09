package com.borzacchiello.advent_of_code.strategy.impl.task09;

import java.math.BigInteger;
import java.util.List;

import com.borzacchiello.advent_of_code.model.task09.TilePosition;
import com.borzacchiello.advent_of_code.model.task09.TileRectangle;

public final class RectangleAreaCalculator {

    private RectangleAreaCalculator() {
        // Classe di utility non istanziabile.
    }

    public static BigInteger findLargestArea(
            List<TilePosition> tiles) {

        BigInteger largestArea =
                BigInteger.ZERO;

        for (int first = 0;
             first < tiles.size();
             first++) {

            for (int second = first + 1;
                 second < tiles.size();
                 second++) {

                TileRectangle rectangle =
                        new TileRectangle(
                                tiles.get(first),
                                tiles.get(second));

                BigInteger area =
                        rectangle.area();

                if (area.compareTo(
                        largestArea) > 0) {

                    largestArea = area;
                }
            }
        }

        return largestArea;
    }
}
