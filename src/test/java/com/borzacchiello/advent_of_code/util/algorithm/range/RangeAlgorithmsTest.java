package com.borzacchiello.advent_of_code.util.algorithm.range;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.Test;

class RangeAlgorithmsTest {
	@Test
	void basics() {
		assertTrue(RangeAlgorithms.overlaps(new IntRange(1, 5), new IntRange(4, 10)));
		assertTrue(RangeAlgorithms.areMergeable(new IntRange(1, 5), new IntRange(6, 10)));
		assertEquals(new IntRange(5, 10), RangeAlgorithms.intersection(new IntRange(1, 10), new IntRange(5, 15)));
	}

	@Test
	void merge() {
		assertEquals(new IntRange(1, 10), RangeAlgorithms.merge(new IntRange(1, 5), new IntRange(6, 10)));
		assertThrows(IllegalArgumentException.class,
				() -> RangeAlgorithms.merge(new IntRange(1, 5), new IntRange(7, 10)));
	}

	@Test
	void all() {
		assertEquals(List.of(new IntRange(1, 15), new IntRange(30, 40)), RangeAlgorithms.mergeAll(
				List.of(new IntRange(30, 40), new IntRange(11, 15), new IntRange(4, 10), new IntRange(1, 5))));
	}
}
