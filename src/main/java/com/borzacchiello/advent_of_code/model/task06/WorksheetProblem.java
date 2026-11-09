package com.borzacchiello.advent_of_code.model.task06;

/**
 * Rappresenta le colonne occupate da un singolo problema.
 *
 * @param startColumn prima colonna del problema, inclusa
 * @param endColumn ultima colonna del problema, inclusa
 * @param operation operazione da applicare
 */
public record WorksheetProblem(
        int startColumn,
        int endColumn,
        MathOperation operation) {

    public int width() {
        return endColumn - startColumn + 1;
    }
}
