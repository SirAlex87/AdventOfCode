package com.borzacchiello.advent_of_code.strategy.impl.task07;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import com.borzacchiello.advent_of_code.model.task07.TachyonManifold;

public final class QuantumTimelineCalculator {

    private static final char SPLITTER = '^';

    private QuantumTimelineCalculator() {
        // Classe di utility non istanziabile.
    }

    /**
     * Calcola il numero totale di timeline generate
     * dal manifold quantistico.
     *
     * Per ogni colonna viene mantenuto il numero di timeline
     * che attualmente percorrono quella posizione.
     *
     * Quando una timeline raggiunge uno splitter, il conteggio
     * viene propagato sia a sinistra sia a destra.
     *
     * Se più percorsi raggiungono la stessa colonna, i loro
     * conteggi vengono sommati anziché eliminare i duplicati.
     *
     * @param manifold struttura da analizzare
     * @return numero totale di timeline finali
     */
    public static BigInteger countTimelines(
            TachyonManifold manifold) {

        Map<Integer, BigInteger> timelines =
                new HashMap<>();

        timelines.put(
                manifold.startColumn(),
                BigInteger.ONE);

        for (int row = 1;
             row < manifold.rowCount();
             row++) {

            Map<Integer, BigInteger>
                    nextTimelines =
                    new HashMap<>();

            for (Map.Entry<Integer, BigInteger>
                    entry :
                    timelines.entrySet()) {

                int column =
                        entry.getKey();

                BigInteger timelineCount =
                        entry.getValue();

                char cell =
                        manifold.cellAt(
                                row,
                                column);

                if (cell == SPLITTER) {

                    addTimeline(
                            manifold,
                            nextTimelines,
                            column - 1,
                            timelineCount);

                    addTimeline(
                            manifold,
                            nextTimelines,
                            column + 1,
                            timelineCount);

                } else {

                    addTimeline(
                            manifold,
                            nextTimelines,
                            column,
                            timelineCount);
                }
            }

            timelines = nextTimelines;

            if (timelines.isEmpty()) {
                break;
            }
        }

        return timelines.values()
                .stream()
                .reduce(
                        BigInteger.ZERO,
                        BigInteger::add);
    }

    private static void addTimeline(
            TachyonManifold manifold,
            Map<Integer, BigInteger> timelines,
            int column,
            BigInteger numberOfTimelines) {

        if (!manifold.isInsideColumn(column)) {
            return;
        }

        timelines.merge(
                column,
                numberOfTimelines,
                BigInteger::add);
    }
}