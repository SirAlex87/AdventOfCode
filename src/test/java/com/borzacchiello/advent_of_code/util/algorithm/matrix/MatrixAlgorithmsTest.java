package com.borzacchiello.advent_of_code.util.algorithm.matrix;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.Test;

class MatrixAlgorithmsTest {
	@Test
	void strings() {
		assertEquals(List.of("ADF", "BE", "C"), MatrixAlgorithms.transpose(List.of("ABC", "DE", "F")));
	}

	@Test
	void nullRow() {
		assertEquals(List.of("AD", "BE", "C"), MatrixAlgorithms.transpose(Arrays.asList("ABC", null, "DE")));
	}

	@Test
	void matrix() {
		assertEquals(List.of(List.of(1, 4), List.of(2, 5), List.of(3, 6)),
				MatrixAlgorithms.transposeMatrix(List.of(List.of(1, 2, 3), List.of(4, 5, 6))));
	}

	@Test
	void irregular() {
		assertThrows(IllegalArgumentException.class,
				() -> MatrixAlgorithms.transposeMatrix(List.of(List.of(1, 2), List.of(3))));
	}
}
