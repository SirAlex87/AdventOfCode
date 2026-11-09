package com.borzacchiello.advent_of_code.util.solver;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPSolver;

public final class OrToolsSolverFactory {

    private static final String DEFAULT_SOLVER =
            "CBC";

    private OrToolsSolverFactory() {
        // Factory non istanziabile.
    }

    public static MPSolver createIntegerSolver() {

        Loader.loadNativeLibraries();

        MPSolver solver =
                MPSolver.createSolver(
                        DEFAULT_SOLVER);

        if (solver == null) {

            throw new IllegalStateException(
                    "Unable to create OR-Tools solver: "
                            + DEFAULT_SOLVER);
        }

        return solver;
    }

    public static MPSolver createSolver(
            String solverName) {

        if (solverName == null
                || solverName.isBlank()) {

            throw new IllegalArgumentException(
                    "Solver name cannot be null or blank");
        }

        Loader.loadNativeLibraries();

        MPSolver solver =
                MPSolver.createSolver(
                        solverName);

        if (solver == null) {

            throw new IllegalStateException(
                    "Unable to create OR-Tools solver: "
                            + solverName);
        }

        return solver;
    }
}
