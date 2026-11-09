package com.borzacchiello.advent_of_code.model.task08;

import java.math.BigInteger;

/**
 * Rappresenta una possibile connessione tra due junction box.
 *
 * @param firstIndex indice della prima junction box
 * @param secondIndex indice della seconda junction box
 * @param squaredDistance distanza euclidea al quadrato
 */
public record JunctionConnection(
        int firstIndex,
        int secondIndex,
        BigInteger squaredDistance)
        implements Comparable<JunctionConnection> {

    @Override
    public int compareTo(
            JunctionConnection other) {

        return squaredDistance.compareTo(
                other.squaredDistance());
    }
}