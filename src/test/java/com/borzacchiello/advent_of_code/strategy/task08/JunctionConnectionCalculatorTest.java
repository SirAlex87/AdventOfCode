package com.borzacchiello.advent_of_code.strategy.task08;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigInteger;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.borzacchiello.advent_of_code.model.task08.JunctionBox;
import com.borzacchiello.advent_of_code.model.task08.JunctionConnection;
import com.borzacchiello.advent_of_code.strategy.impl.task08.JunctionConnectionCalculator;

class JunctionConnectionCalculatorTest {

    @Test
    void shouldGenerateAllPossibleConnections() {

        List<JunctionBox> boxes =
                List.of(
                        new JunctionBox(0, 0, 0),
                        new JunctionBox(1, 0, 0),
                        new JunctionBox(2, 0, 0),
                        new JunctionBox(3, 0, 0));

        List<JunctionConnection> connections =
                JunctionConnectionCalculator
                        .calculateConnections(boxes);

        // Con quattro elementi:
        // 4 x 3 / 2 = 6 coppie.
        assertEquals(
                6,
                connections.size());
    }

    @Test
    void shouldCalculateSquaredDistance() {

        List<JunctionBox> boxes =
                List.of(
                        new JunctionBox(0, 0, 0),
                        new JunctionBox(3, 4, 0));

        List<JunctionConnection> connections =
                JunctionConnectionCalculator
                        .calculateConnections(boxes);

        assertEquals(
                1,
                connections.size());

        // 3 al quadrato + 4 al quadrato = 25.
        assertEquals(
                new BigInteger("25"),
                connections.getFirst()
                        .squaredDistance());
    }

    @Test
    void shouldSortConnectionsByDistance() {

        List<JunctionBox> boxes =
                List.of(
                        new JunctionBox(0, 0, 0),
                        new JunctionBox(10, 0, 0),
                        new JunctionBox(2, 0, 0));

        List<JunctionConnection> connections =
                JunctionConnectionCalculator
                        .calculateConnections(boxes);

        for (int index = 1;
             index < connections.size();
             index++) {

            BigInteger previous =
                    connections.get(index - 1)
                            .squaredDistance();

            BigInteger current =
                    connections.get(index)
                            .squaredDistance();

            assertTrue(
                    previous.compareTo(current) <= 0);
        }

        // La coppia più vicina è 0 -> 2:
        // distanza = 2.
        assertEquals(
                0,
                connections.getFirst()
                        .firstIndex());

        assertEquals(
                2,
                connections.getFirst()
                        .secondIndex());

        assertEquals(
                BigInteger.valueOf(4),
                connections.getFirst()
                        .squaredDistance());
    }
}
