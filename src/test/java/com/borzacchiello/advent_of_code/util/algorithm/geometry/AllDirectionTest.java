package com.borzacchiello.advent_of_code.util.algorithm.geometry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AllDirectionTest {
	@ParameterizedTest
	@CsvSource({ "UP,-1,0", "UP_RIGHT,-1,1", "RIGHT,0,1", "DOWN_RIGHT,1,1", "DOWN,1,0", "DOWN_LEFT,1,-1", "LEFT,0,-1",
			"UP_LEFT,-1,-1" })
	void deltas(String n, int r, int c) {
		var d = AllDirection.valueOf(n);
		assertEquals(r, d.getRowDelta());
		assertEquals(c, d.getColDelta());
	}
}
