package com.borzacchiello.advent_of_code.util.solver;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class OrToolsSolverFactoryTest {
	@Test
	void defaultSolver() {
		assertNotNull(OrToolsSolverFactory.createIntegerSolver());
	}

	@Test
	void namedSolver() {
		assertNotNull(OrToolsSolverFactory.createSolver("CBC"));
	}

	@Test
	void invalidNames() {
		assertThrows(IllegalArgumentException.class, () -> OrToolsSolverFactory.createSolver(null));
		assertThrows(IllegalArgumentException.class, () -> OrToolsSolverFactory.createSolver(" "));
	}

	@Test
	void unknown() {
		assertThrows(IllegalStateException.class, () -> OrToolsSolverFactory.createSolver("NON_EXISTING_SOLVER"));
	}
}
