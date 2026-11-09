package com.borzacchiello.advent_of_code.strategy.impl.task05;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.borzacchiello.advent_of_code.model.task05.IngredientIdRange;

public final class IngredientRangeMerger {

    private static final BigInteger ONE =
            BigInteger.ONE;

    private IngredientRangeMerger() {
        // Classe di utility non istanziabile.
    }

    /**
     * Unisce gli intervalli sovrapposti o adiacenti.
     *
     * L'algoritmo ordina prima gli intervalli per estremo iniziale.
     * Successivamente mantiene un intervallo corrente e prova a
     * estenderlo con ogni intervallo successivo.
     *
     * Due intervalli vengono uniti quando il secondo inizia prima
     * o immediatamente dopo la fine del primo.
     *
     * Esempi:
     *
     * 10-15 e 14-20 diventano 10-20.
     * 10-15 e 16-20 diventano 10-20.
     * 10-15 e 17-20 rimangono separati.
     *
     * @param ranges intervalli da normalizzare
     * @return intervalli ordinati, non sovrapposti e non adiacenti
     */
    public static List<IngredientIdRange> merge(
            List<IngredientIdRange> ranges) {

        if (ranges.isEmpty()) {
            return List.of();
        }

        List<IngredientIdRange> sortedRanges =
                ranges.stream()
                        .sorted(
                                Comparator.comparing(
                                        IngredientIdRange::start))
                        .toList();

        List<IngredientIdRange> mergedRanges =
                new ArrayList<>();

        IngredientIdRange current =
                sortedRanges.getFirst();

        for (int index = 1;
             index < sortedRanges.size();
             index++) {

            IngredientIdRange next =
                    sortedRanges.get(index);

            if (overlapsOrIsAdjacent(
                    current,
                    next)) {

                current = mergeRanges(
                        current,
                        next);
            } else {
                mergedRanges.add(current);
                current = next;
            }
        }

        mergedRanges.add(current);

        return List.copyOf(mergedRanges);
    }

    /**
     * Calcola il numero totale di ID coperti dagli intervalli.
     *
     * Gli estremi sono inclusivi, quindi la dimensione di un
     * intervallo è end - start + 1.
     */
    public static BigInteger countCoveredIds(
            List<IngredientIdRange> ranges) {

        return merge(ranges).stream()
                .map(IngredientRangeMerger::size)
                .reduce(
                        BigInteger.ZERO,
                        BigInteger::add);
    }

    /**
     * Verifica se due intervalli devono essere uniti.
     */
    private static boolean overlapsOrIsAdjacent(
            IngredientIdRange current,
            IngredientIdRange next) {

        BigInteger firstValueAfterCurrent =
                current.end().add(ONE);

        return next.start().compareTo(
                firstValueAfterCurrent) <= 0;
    }

    /**
     * Crea un intervallo che copre entrambi gli intervalli ricevuti.
     */
    private static IngredientIdRange mergeRanges(
            IngredientIdRange current,
            IngredientIdRange next) {

        BigInteger maximumEnd =
                current.end().max(next.end());

        return new IngredientIdRange(
                current.start(),
                maximumEnd);
    }

    /**
     * Calcola la dimensione inclusiva di un intervallo.
     */
    private static BigInteger size(
            IngredientIdRange range) {

        return range.end()
                .subtract(range.start())
                .add(ONE);
    }
}