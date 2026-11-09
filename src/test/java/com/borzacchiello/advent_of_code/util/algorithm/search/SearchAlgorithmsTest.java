package com.borzacchiello.advent_of_code.util.algorithm.search;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.Test;

class SearchAlgorithmsTest {
	private static final Map<String, List<String>> G = Map.of("A", List.of("B", "C"), "B", List.of("D"), "C",
			List.of("E"), "D", List.of("F"), "E", List.of("F"), "F", List.of());

	@Test
	void bfs() {
		assertEquals("F",
				SearchAlgorithms.breadthFirstSearch("A", "F"::equals, n -> G.getOrDefault(n, List.of())).orElseThrow());
	}

	@Test
	void dfs() {
		assertEquals("F",
				SearchAlgorithms.depthFirstSearch("A", "F"::equals, n -> G.getOrDefault(n, List.of())).orElseThrow());
	}

	@Test
	void missing() {
		assertTrue(SearchAlgorithms.breadthFirstSearch("A", "Z"::equals, n -> G.getOrDefault(n, List.of())).isEmpty());
	}

	@Test
	void reachable() {
		assertEquals(Set.of("A", "B", "C", "D", "E", "F"),
				SearchAlgorithms.findReachable("A", n -> G.getOrDefault(n, List.of())));
	}
}
