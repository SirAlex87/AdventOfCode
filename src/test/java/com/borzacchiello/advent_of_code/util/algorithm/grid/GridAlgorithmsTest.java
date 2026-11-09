package com.borzacchiello.advent_of_code.util.algorithm.grid;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.borzacchiello.advent_of_code.util.algorithm.geometry.AllDirection;

class GridAlgorithmsTest {
	@Test
	void inside() {
		assertTrue(GridAlgorithms.isInside(new GridPosition(2, 3), 5, 5));
		assertFalse(GridAlgorithms.isInside(new GridPosition(-1, 0), 5, 5));
	}

	@Test
	void neighbours() {
		assertEquals(4, GridAlgorithms.getOrthogonalNeighbors(new GridPosition(2, 2), 5, 5).size());
		assertEquals(8, GridAlgorithms.getAllNeighbors(new GridPosition(2, 2), 5, 5).size());
		assertEquals(3, GridAlgorithms.getAllNeighbors(new GridPosition(0, 0), 5, 5).size());
	}

	@Test
	void move() {
		assertEquals(new GridPosition(3, 5), GridAlgorithms.move(new GridPosition(4, 4), AllDirection.UP_RIGHT));
	}
}
