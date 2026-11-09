package com.borzacchiello.advent_of_code.util.algorithm.collection;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.Test;

class CollectionAlgorithmsTest {
	@Test
	void pairs() {
		assertEquals(3, CollectionAlgorithms.uniquePairs(List.of(1, 2, 3)).size());
		assertTrue(CollectionAlgorithms.uniquePairs(List.of(1)).isEmpty());
	}

	@Test
	void distinct() {
		assertEquals(List.of("A", "B", "C"), CollectionAlgorithms.distinctPreservingOrder(List.of("A", "B", "A", "C")));
	}

	@Test
	void partition() {
		var r = CollectionAlgorithms.partition(List.of(1, 2, 3, 4), n -> n % 2 == 0);
		assertEquals(List.of(2, 4), r.matching());
		assertEquals(List.of(1, 3), r.notMatching());
	}

	@Test
	void frequencies() {
		assertEquals(Map.of("A", 2L, "B", 1L), CollectionAlgorithms.frequencies(List.of("A", "B", "A")));
	}

	@Test
	void group() {
		assertEquals(List.of("A", "C"), CollectionAlgorithms.groupBy(List.of("A", "BB", "C"), String::length).get(1));
	}

	@Test
	void intersection() {
		assertEquals(Set.of(3, 4), CollectionAlgorithms.intersection(List.of(1, 2, 3, 4), List.of(3, 4, 5)));
	}

	@Test
	void minmax() {
		var r = CollectionAlgorithms.minMax(List.of(7, 2, 10, 4)).orElseThrow();
		assertEquals(2, r.min());
		assertEquals(10, r.max());
	}
}
