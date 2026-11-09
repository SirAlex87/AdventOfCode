package com.borzacchiello.advent_of_code.strategy.impl.task04;

import java.util.ArrayList;
import java.util.List;

import com.borzacchiello.advent_of_code.model.task04.GridPosition;

public final class PaperRollCalculator {

    private static final char PAPER_ROLL = '@';
    private static final char EMPTY = '.';

    private static final int MAX_ADJACENT_ROLLS = 4;

    private static final int[][] DIRECTIONS = {
            {-1, -1}, {-1, 0}, {-1, 1},
            { 0, -1},          { 0, 1},
            { 1, -1}, { 1, 0}, { 1, 1}
    };

    private PaperRollCalculator() {
        // Classe di utility non istanziabile.
    }

    /**
     * Individua tutti i rotoli accessibili nella configurazione corrente.
     *
     * <p>Un rotolo è accessibile quando meno di quattro delle otto
     * celle adiacenti contengono un altro rotolo.</p>
     *
     * <p>Il metodo non modifica la griglia durante la scansione.
     * Questo è fondamentale nella Parte 2: tutti i rotoli rimovibili
     * durante una singola iterazione devono essere determinati rispetto
     * alla stessa configurazione della griglia.</p>
     *
     * @param cells configurazione corrente della griglia
     * @return posizioni dei rotoli accessibili
     */
    public static List<GridPosition> findAccessibleRolls(
            char[][] cells) {

        List<GridPosition> result =
                new ArrayList<>();

        for (int row = 0; row < cells.length; row++) {

            for (int column = 0;
                 column < cells[row].length;
                 column++) {

                if (cells[row][column] != PAPER_ROLL) {
                    continue;
                }

                int adjacentRolls =
                        countAdjacentRolls(
                                cells,
                                row,
                                column);

                if (adjacentRolls
                        < MAX_ADJACENT_ROLLS) {

                    result.add(
                            new GridPosition(
                                    row,
                                    column));
                }
            }
        }

        return result;
    }

    /**
     * Rimuove simultaneamente dalla griglia tutti i rotoli
     * precedentemente individuati.
     *
     * @param cells griglia da modificare
     * @param positions posizioni da rimuovere
     */
    public static void removeRolls(
            char[][] cells,
            List<GridPosition> positions) {

        positions.forEach(position ->
                cells[position.row()]
                     [position.column()] = EMPTY);
    }

    /**
     * Conta quanti rotoli sono presenti nelle otto celle adiacenti.
     *
     * @param cells griglia corrente
     * @param row riga del rotolo
     * @param column colonna del rotolo
     * @return numero di rotoli adiacenti
     */
    private static int countAdjacentRolls(
            char[][] cells,
            int row,
            int column) {

        int count = 0;

        for (int[] direction : DIRECTIONS) {

            int adjacentRow =
                    row + direction[0];

            int adjacentColumn =
                    column + direction[1];

            if (isPaperRoll(
                    cells,
                    adjacentRow,
                    adjacentColumn)) {

                count++;
            }
        }

        return count;
    }

    /**
     * Verifica che la posizione appartenga alla griglia e contenga
     * un rotolo di carta.
     */
    private static boolean isPaperRoll(
            char[][] cells,
            int row,
            int column) {

        return row >= 0
                && row < cells.length
                && column >= 0
                && column < cells[row].length
                && cells[row][column] == PAPER_ROLL;
    }
}