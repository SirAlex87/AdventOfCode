package com.borzacchiello.advent_of_code.util.algorithm.collection;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigInteger;
import java.util.*;
import org.junit.jupiter.api.Test;

class MapAlgorithmsTest {
	@Test
	void increment() {
		Map<String, Integer> m = new HashMap<>();
		MapAlgorithms.increment(m, "A");
		MapAlgorithms.increment(m, "A");
		assertEquals(2, m.get("A"));
	}

	@Test
	void numbers() {
		Map<String, Integer> i = new HashMap<>();
		MapAlgorithms.addInteger(i, "A", 5);
		MapAlgorithms.addInteger(i, "A", 7);
		assertEquals(12, i.get("A"));
		Map<String, Long> l = new HashMap<>();
		MapAlgorithms.addLong(l, "A", 10);
		assertEquals(10L, l.get("A"));
		Map<String, BigInteger> b = new HashMap<>();
		MapAlgorithms.addBigInteger(b, "A", BigInteger.TEN);
		assertEquals(BigInteger.TEN, b.get("A"));
	}

	@Test
	void lists() {
		Map<String, List<Integer>> m = new HashMap<>();
		MapAlgorithms.addToList(m, "A", 1);
		MapAlgorithms.addAllToList(m, "A", List.of(2, 3));
		assertEquals(List.of(1, 2, 3), m.get("A"));
	}
}
