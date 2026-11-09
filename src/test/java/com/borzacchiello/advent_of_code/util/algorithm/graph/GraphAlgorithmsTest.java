package com.borzacchiello.advent_of_code.util.algorithm.graph;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigInteger;
import java.util.*;
import org.junit.jupiter.api.Test;

class GraphAlgorithmsTest {
	@Test
	void paths() {
		var g = Map.of("A", List.of("B", "C"), "B", List.of("D"), "C", List.of("D"));
		assertEquals(BigInteger.TWO, GraphAlgorithms.countPaths(g, "A", "D"));
	}

	@Test
	void unreachable() {
		assertEquals(BigInteger.ZERO, GraphAlgorithms.countPaths(Map.of("A", List.of("B")), "A", "Z"));
	}

	@Test
	void same() {
		assertEquals(BigInteger.ONE, GraphAlgorithms.countPaths(Map.of(), "A", "A"));
	}

	@Test
	void cycle() {
		var g = Map.of("A", List.of("B"), "B", List.of("A"));
		assertThrows(IllegalArgumentException.class, () -> GraphAlgorithms.countPaths(g, "A", "Z"));
	}
}
