package com.borzacchiello.advent_of_code.util.algorithm.geometry;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class Point2DTest {
	@Test
	void translate() {
		var p = new Point2D(2, 3);
		assertEquals(new Point2D(7, 1), p.translate(5, -2));
		assertEquals(new Point2D(2, 3), p);
	}

	@Test
	void distance() {
		assertEquals(7, new Point2D(0, 0).manhattanDistance(new Point2D(3, 4)));
	}
}
