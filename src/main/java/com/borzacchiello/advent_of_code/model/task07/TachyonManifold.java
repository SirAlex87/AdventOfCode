package com.borzacchiello.advent_of_code.model.task07;

/**
 * Rappresenta la struttura del manifold di tachioni.
 *
 * @param cells celle del manifold
 * @param startColumn colonna contenente il punto iniziale S
 */
public record TachyonManifold(
        char[][] cells,
        int startColumn) {

    public TachyonManifold {

        char[][] copy =
                new char[cells.length][];

        for (int row = 0;
             row < cells.length;
             row++) {

            copy[row] = cells[row].clone();
        }

        cells = copy;
    }

    public int rowCount() {
        return cells.length;
    }

    public int columnCount() {

        if (cells.length == 0) {
            return 0;
        }

        return cells[0].length;
    }

    public char cellAt(
            int row,
            int column) {

        return cells[row][column];
    }

    public boolean isInsideColumn(
            int column) {

        return column >= 0
                && column < columnCount();
    }

    public char[][] copyCells() {

        char[][] copy =
                new char[cells.length][];

        for (int row = 0;
             row < cells.length;
             row++) {

            copy[row] = cells[row].clone();
        }

        return copy;
    }

    @Override
    public char[][] cells() {
        return copyCells();
    }
}