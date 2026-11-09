package com.borzacchiello.advent_of_code.strategy.impl.task09;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.borzacchiello.advent_of_code.model.task09.OrthogonalPolygon;
import com.borzacchiello.advent_of_code.model.task09.TilePosition;
import com.borzacchiello.advent_of_code.model.task09.TileRectangle;

public final class ValidRectangleCalculator {

    private ValidRectangleCalculator() {
        // Classe di utility non istanziabile.
    }

    static BigInteger findLargestArea(
            List<TilePosition> redTiles) {

        OrthogonalPolygon polygon =
                new OrthogonalPolygon(
                        redTiles);

        List<TileRectangle> candidates =
                createCandidates(redTiles);

        // Valutiamo prima i rettangoli più grandi.
        // Appena ne troviamo uno completamente interno
        // possiamo restituirlo senza controllare
        // rettangoli di area inferiore.
        candidates.sort(
                Comparator.comparing(
                        TileRectangle::area)
                        .reversed());

        for (TileRectangle rectangle :
                candidates) {

            if (PolygonRectangleValidator.isInside(
                    polygon,
                    rectangle)) {

                return rectangle.area();
            }
        }

        return BigInteger.ZERO;
    }

    private static List<TileRectangle> createCandidates(
            List<TilePosition> redTiles) {

        List<TileRectangle> rectangles =
                new ArrayList<>();

        for (int first = 0;
             first < redTiles.size();
             first++) {

            for (int second = first + 1;
                 second < redTiles.size();
                 second++) {

                rectangles.add(
                        new TileRectangle(
                                redTiles.get(first),
                                redTiles.get(second)));
            }
        }

        return rectangles;
    }
}