package com.borzacchiello.advent_of_code.util.algorithm.range;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class IntRangeTest {
	@Test
	void contains() {
		var r = new IntRange(5, 10);
		assertTrue(r.contains(5));
		assertTrue(r.contains(10));
		assertFalse(r.contains(11));
	}

	@Test
	void overlap() {
		assertTrue(new IntRange(1, 5).overlaps(new IntRange(5, 10)));
		assertFalse(new IntRange(1, 5).overlaps(new IntRange(6, 10)));
	}

	@Test
	void length() {
		assertEquals(10, new IntRange(1, 10).length());
	}
}
