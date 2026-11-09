package com.borzacchiello.advent_of_code.model.task06;

public record WorksheetData(char[][] cells) {

    public WorksheetData {

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

    public int operatorRow() {
        return cells.length - 1;
    }

    public char cellAt(
            int row,
            int column) {

        return cells[row][column];
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