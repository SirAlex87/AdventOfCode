package com.borzacchiello.advent_of_code.util.algorithm.grid;

public record GridPosition(int row, int col) {

    public GridPosition move(int deltaRow, int deltaCol) {
        return new GridPosition(row + deltaRow, col + deltaCol);
    }
}