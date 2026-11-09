package com.borzacchiello.advent_of_code.strategy.impl.task03;

import java.math.BigInteger;

import com.borzacchiello.advent_of_code.model.task03.BatteryBank;

public final class MaximumJoltageCalculator {

    private MaximumJoltageCalculator() {
        // Classe di utility non istanziabile.
    }

    /**
     * Calcola il valore massimo ottenibile selezionando il numero richiesto
     * di batterie e mantenendo il loro ordine originale.
     *
     * @param bank banca di batterie da elaborare
     * @param batteriesToSelect numero esatto di batterie da selezionare
     * @return massimo joltage ottenibile
     * @throws IllegalArgumentException se la banca non contiene abbastanza
     *                                  batterie
     */
    public static BigInteger calculate(
            BatteryBank bank,
            int batteriesToSelect) {

        String ratings = bank.ratings();

        validateBankLength(ratings, batteriesToSelect);

        StringBuilder maximumJoltage =
                new StringBuilder(batteriesToSelect);

        int searchStart = 0;

        for (int selected = 0;
             selected < batteriesToSelect;
             selected++) {

            int remainingToSelect =
                    batteriesToSelect - selected;

            int searchEnd =
                    ratings.length() - remainingToSelect;

            int maximumPosition = findMaximumPosition(
                    ratings,
                    searchStart,
                    searchEnd);

            maximumJoltage.append(
                    ratings.charAt(maximumPosition));

            searchStart = maximumPosition + 1;
        }

        return new BigInteger(maximumJoltage.toString());
    }

    /**
     * Cerca la prima posizione contenente la cifra massima all'interno
     * dell'intervallo indicato.
     *
     * @param ratings valori delle batterie
     * @param start posizione iniziale inclusa
     * @param end posizione finale inclusa
     * @return posizione della prima cifra massima
     */
    private static int findMaximumPosition(
            String ratings,
            int start,
            int end) {

        int maximumPosition = start;
        char maximumRating = ratings.charAt(start);

        for (int currentPosition = start + 1;
             currentPosition <= end;
             currentPosition++) {

            char currentRating =
                    ratings.charAt(currentPosition);

            if (currentRating > maximumRating) {
                maximumRating = currentRating;
                maximumPosition = currentPosition;
            }
        }

        return maximumPosition;
    }

    /**
     * Verifica che la banca contenga almeno il numero di batterie richiesto.
     *
     * @param ratings valori delle batterie
     * @param batteriesToSelect numero di batterie da selezionare
     */
    private static void validateBankLength(
            String ratings,
            int batteriesToSelect) {

        if (ratings.length() < batteriesToSelect) {
            throw new IllegalArgumentException(
                    "Battery bank contains "
                            + ratings.length()
                            + " batteries, but "
                            + batteriesToSelect
                            + " are required");
        }
    }
}