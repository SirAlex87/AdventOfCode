package com.borzacchiello.advent_of_code.strategy.impl.task08;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.borzacchiello.advent_of_code.model.task08.JunctionBox;
import com.borzacchiello.advent_of_code.model.task08.JunctionConnection;

public final class JunctionConnectionCalculator {

    private JunctionConnectionCalculator() {
        // Classe di utility non istanziabile.
    }

    public static List<JunctionConnection> calculateConnections(
            List<JunctionBox> boxes) {

        List<JunctionConnection> connections =
                new ArrayList<>();

        for (int firstIndex = 0;
             firstIndex < boxes.size();
             firstIndex++) {

            for (int secondIndex =
                    firstIndex + 1;
                 secondIndex < boxes.size();
                 secondIndex++) {

                BigInteger distance =
                        calculateSquaredDistance(
                                boxes.get(firstIndex),
                                boxes.get(secondIndex));

                connections.add(
                        new JunctionConnection(
                                firstIndex,
                                secondIndex,
                                distance));
            }
        }

        // La radice quadrata non è necessaria.
        // L'ordinamento delle distanze al quadrato
        // è uguale all'ordinamento delle distanze reali.
        Collections.sort(connections);

        return List.copyOf(connections);
    }

    private static BigInteger calculateSquaredDistance(
            JunctionBox first,
            JunctionBox second) {

        BigInteger deltaX =
                BigInteger.valueOf(first.x())
                        .subtract(
                                BigInteger.valueOf(
                                        second.x()));

        BigInteger deltaY =
                BigInteger.valueOf(first.y())
                        .subtract(
                                BigInteger.valueOf(
                                        second.y()));

        BigInteger deltaZ =
                BigInteger.valueOf(first.z())
                        .subtract(
                                BigInteger.valueOf(
                                        second.z()));

        BigInteger squaredX =
                deltaX.multiply(deltaX);

        BigInteger squaredY =
                deltaY.multiply(deltaY);

        BigInteger squaredZ =
                deltaZ.multiply(deltaZ);

        return squaredX
                .add(squaredY)
                .add(squaredZ);
    }
}