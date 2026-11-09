package com.borzacchiello.advent_of_code.util.algorithm.grid;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class GridPositionTest {
	@Test
	void move() {
		var p = new GridPosition(5, 7);
		assertEquals(new GridPosition(3, 10), p.move(-2, 3));
		assertEquals(new GridPosition(5, 7), p);
	}
}
