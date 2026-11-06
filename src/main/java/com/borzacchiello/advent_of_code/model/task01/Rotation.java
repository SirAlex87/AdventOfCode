package com.borzacchiello.advent_of_code.model.task01;

/**
 * Rappresenta una singola istruzione di rotazione della manopola.
 *
 * @param direction direzione della rotazione
 * @param distance numero di click della rotazione
 */
public record Rotation(
        Direction direction,
        int distance
) {
}
