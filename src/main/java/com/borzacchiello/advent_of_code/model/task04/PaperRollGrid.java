package com.borzacchiello.advent_of_code.model.task04;

/**
 * Rappresenta la disposizione dei rotoli di carta all'interno
 * della griglia del Printing Department.
 *
 * @param cells celle della griglia
 */
public record PaperRollGrid(char[][] cells) {

    /**
     * Restituisce una copia indipendente della griglia.
     *
     * <p>La copia viene utilizzata dalla Parte 2 perché la rimozione
     * iterativa dei rotoli deve modificare lo stato della griglia senza
     * alterare l'input originale ricevuto dalla Strategy.</p>
     *
     * @return copia della matrice
     */
    public char[][] copyCells() {

        char[][] copy = new char[cells.length][];

        for (int row = 0; row < cells.length; row++) {
            copy[row] = cells[row].clone();
        }

        return copy;
    }
}