package com.borzacchiello.advent_of_code.util.algorithm.grid;

import java.util.Arrays;
import java.util.List;

import com.borzacchiello.advent_of_code.util.algorithm.geometry.AllDirection;

/**
 * Utility class containing common algorithms for working with grid positions.
 *
 * <p>The class provides operations for checking grid boundaries and retrieving
 * neighboring positions using orthogonal or diagonal movements.</p>
 */
public final class GridAlgorithms {

    private GridAlgorithms() {
        // Utility class: prevents instantiation.
    }

    /**
     * Checks whether the specified position is inside a grid.
     *
     * @param position the position to check
     * @param rows     the number of rows in the grid
     * @param cols     the number of columns in the grid
     * @return {@code true} if the position is inside the grid,
     *         {@code false} otherwise
     */
    public static boolean isInside(GridPosition position, int rows, int cols) {
        return position.row() >= 0
                && position.row() < rows
                && position.col() >= 0
                && position.col() < cols;
    }

    /**
     * Returns the valid orthogonal neighbors of the specified position.
     *
     * <p>Only movements UP, RIGHT, DOWN and LEFT are considered.</p>
     *
     * @param position the starting position
     * @param rows     the number of rows in the grid
     * @param cols     the number of columns in the grid
     * @return the list of valid orthogonal neighboring positions
     */
    public static List<GridPosition> getOrthogonalNeighbors(
            GridPosition position,
            int rows,
            int cols) {

        return Arrays.stream(AllDirection.values())
                .filter(GridAlgorithms::isOrthogonal)
                .map(direction -> move(position, direction))
                .filter(neighbor -> isInside(neighbor, rows, cols))
                .toList();
    }

    /**
     * Returns all valid neighbors of the specified position.
     *
     * <p>Both orthogonal and diagonal movements are considered,
     * for a maximum of eight neighboring positions.</p>
     *
     * @param position the starting position
     * @param rows     the number of rows in the grid
     * @param cols     the number of columns in the grid
     * @return the list of all valid neighboring positions
     */
    public static List<GridPosition> getAllNeighbors(
            GridPosition position,
            int rows,
            int cols) {

        return Arrays.stream(AllDirection.values())
                .map(direction -> move(position, direction))
                .filter(neighbor -> isInside(neighbor, rows, cols))
                .toList();
    }

    /**
     * Moves a position in the specified direction.
     *
     * @param position  the starting position
     * @param direction the movement direction
     * @return the resulting position
     */
    public static GridPosition move(
            GridPosition position,
            AllDirection direction) {

        return position.move(
                direction.getRowDelta(),
                direction.getColDelta());
    }

    /**
     * Checks whether a direction represents an orthogonal movement.
     *
     * @param direction the direction to check
     * @return {@code true} for UP, RIGHT, DOWN or LEFT
     */
    private static boolean isOrthogonal(AllDirection direction) {
        return direction.getRowDelta() == 0
                || direction.getColDelta() == 0;
    }
}
