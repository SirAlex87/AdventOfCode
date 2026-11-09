package com.borzacchiello.advent_of_code.util.algorithm.math;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MathAlgorithmsTest {
	@ParameterizedTest
	@CsvSource({ "48,18,6", "17,13,1", "0,5,5", "-48,18,6" })
	void gcd(long a, long b, long e) {
		assertEquals(e, MathAlgorithms.gcd(a, b));
	}

	@ParameterizedTest
	@CsvSource({ "4,6,12", "0,8,0", "-4,6,12" })
	void lcm(long a, long b, long e) {
		assertEquals(e, MathAlgorithms.lcm(a, b));
	}

	@ParameterizedTest
	@CsvSource({ "-1,5,4", "6,5,1", "5,5,0" })
	void mod(long v, long m, long e) {
		assertEquals(e, MathAlgorithms.positiveMod(v, m));
	}

	@Test
	void invalid() {
		assertThrows(IllegalArgumentException.class, () -> MathAlgorithms.positiveMod(1, 0));
	}
}
