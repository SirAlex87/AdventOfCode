package com.borzacchiello.advent_of_code.strategy.impl.task07;

import java.util.HashSet;
import java.util.Set;

import com.borzacchiello.advent_of_code.model.task07.TachyonManifold;

public final class TachyonBeamCalculator {

    private static final char SPLITTER = '^';

    private TachyonBeamCalculator() {
        // Classe di utility non istanziabile.
    }

    /**
     * Conta quante volte i fasci vengono divisi
     * attraversando il manifold.
     *
     * L'algoritmo mantiene solamente le colonne nelle quali
     * è presente un fascio. Quando un fascio raggiunge uno
     * splitter, il fascio verticale termina e vengono generati
     * due nuovi fasci nelle colonne adiacenti.
     *
     * L'uso di un Set garantisce che due fasci che convergono
     * sulla stessa posizione vengano rappresentati come un
     * singolo fascio nella simulazione classica.
     *
     * @param manifold struttura da analizzare
     * @return numero complessivo di split
     */
    public static int countSplits(
            TachyonManifold manifold) {

        Set<Integer> activeColumns =
                new HashSet<>();

        activeColumns.add(
                manifold.startColumn());

        int splitCount = 0;

        for (int row = 1;
             row < manifold.rowCount();
             row++) {

            Set<Integer> nextColumns =
                    new HashSet<>();

            for (int column :
                    activeColumns) {

                char cell =
                        manifold.cellAt(
                                row,
                                column);

                if (cell == SPLITTER) {

                    splitCount++;

                    addIfValid(
                            manifold,
                            nextColumns,
                            column - 1);

                    addIfValid(
                            manifold,
                            nextColumns,
                            column + 1);

                } else {

                    nextColumns.add(column);
                }
            }

            activeColumns = nextColumns;

            if (activeColumns.isEmpty()) {
                break;
            }
        }

        return splitCount;
    }

    private static void addIfValid(
            TachyonManifold manifold,
            Set<Integer> columns,
            int column) {

        if (manifold.isInsideColumn(column)) {
            columns.add(column);
        }
    }
}