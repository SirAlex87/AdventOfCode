package com.borzacchiello.advent_of_code.util.algorithm.matrix;

import java.util.ArrayList;
import java.util.List;

public final class MatrixAlgorithms {

    private MatrixAlgorithms() {
        // Classe di algoritmi non istanziabile.
    }

    // Traspone una rappresentazione testuale per righe.
    //
    // Le righe possono avere lunghezze differenti.
    // Se una riga non contiene un carattere nella colonna
    // corrente, viene semplicemente ignorata.
    //
    // Esempio:
    //
    // ABC
    // DE
    // F
    //
    // Risultato:
    //
    // ADF
    // BE
    // C

    public static List<String> transpose(
            List<String> input) {

        if (input == null || input.isEmpty()) {
            return List.of();
        }

        int maximumLength =
                input.stream()
                        .filter(value -> value != null)
                        .mapToInt(String::length)
                        .max()
                        .orElse(0);

        List<String> result =
                new ArrayList<>(maximumLength);

        for (int column = 0;
             column < maximumLength;
             column++) {

            StringBuilder transposedRow =
                    new StringBuilder();

            for (String row : input) {

                if (row != null
                        && column < row.length()) {

                    transposedRow.append(
                            row.charAt(column));
                }
            }

            result.add(
                    transposedRow.toString());
        }

        return List.copyOf(result);
    }

    // Traspone una matrice generica rettangolare.
    //
    // Esempio:
    //
    // 1 2 3
    // 4 5 6
    //
    // diventa:
    //
    // 1 4
    // 2 5
    // 3 6
    //
    // A differenza di transpose(List<String>),
    // questa operazione richiede una matrice rettangolare.

    public static <T> List<List<T>> transposeMatrix(
            List<List<T>> matrix) {

        if (matrix == null || matrix.isEmpty()) {
            return List.of();
        }

        if (matrix.getFirst() == null) {
            throw new IllegalArgumentException(
                    "Matrix rows cannot be null");
        }

        int columnCount =
                matrix.getFirst().size();

        boolean rectangular =
                matrix.stream()
                        .allMatch(row ->
                                row != null
                                        && row.size()
                                        == columnCount);

        if (!rectangular) {
            throw new IllegalArgumentException(
                    "Matrix must be rectangular");
        }

        if (columnCount == 0) {
            return List.of();
        }

        List<List<T>> result =
                new ArrayList<>(columnCount);

        for (int column = 0;
             column < columnCount;
             column++) {

            List<T> transposedRow =
                    new ArrayList<>(matrix.size());

            for (List<T> row : matrix) {

                transposedRow.add(
                        row.get(column));
            }

            result.add(
                    List.copyOf(transposedRow));
        }

        return List.copyOf(result);
    }
}