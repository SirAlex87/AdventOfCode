package com.borzacchiello.advent_of_code.strategy.impl.task10;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.task10.MachineButton;
import com.borzacchiello.advent_of_code.model.task10.MachineConfiguration;
import com.borzacchiello.advent_of_code.util.Utility;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

public final class JoltageOptimizationCalculator {

    private JoltageOptimizationCalculator() {
        // Classe di utility non istanziabile.
    }

    public static int calculateMinimumPresses(
            MachineConfiguration machine) {

        MPSolver solver =
                Utility.loadResolver();

        if (solver == null) {
            throw new IllegalStateException(
                    "Unable to initialize "
                            + "the OR-Tools solver");
        }

        MPVariable[] buttonPresses =
                createButtonVariables(
                        machine,
                        solver);

        addCounterConstraints(
                machine,
                solver,
                buttonPresses);

        configureObjective(
                solver,
                buttonPresses);

        MPSolver.ResultStatus status =
                solver.solve();

        if (status
                != MPSolver.ResultStatus.OPTIMAL) {

            throw new InvalidTaskInputException(
                    "Task 10 joltage target "
                            + "has no optimal solution");
        }

        return calculateTotalPresses(
                buttonPresses);
    }

    private static MPVariable[]
            createButtonVariables(
                    MachineConfiguration machine,
                    MPSolver solver) {

        MPVariable[] variables =
                new MPVariable[
                        machine.buttons().size()];

        for (int buttonIndex = 0;
             buttonIndex < variables.length;
             buttonIndex++) {

            variables[buttonIndex] =
                    solver.makeIntVar(
                            0.0,
                            Double.POSITIVE_INFINITY,
                            "button_"
                                    + buttonIndex);
        }

        return variables;
    }

    private static void addCounterConstraints(
            MachineConfiguration machine,
            MPSolver solver,
            MPVariable[] buttonPresses) {

        for (int counterIndex = 0;
             counterIndex
                     < machine.joltageCounterCount();
             counterIndex++) {

            int target =
                    machine.joltageTarget()
                            .get(counterIndex);

            MPConstraint constraint =
                    solver.makeConstraint(
                            target,
                            target,
                            "counter_"
                                    + counterIndex);

            for (int buttonIndex = 0;
                 buttonIndex
                         < machine.buttons().size();
                 buttonIndex++) {

                MachineButton button =
                        machine.buttons()
                                .get(buttonIndex);

                if (button.positions()
                        .contains(counterIndex)) {

                    constraint.setCoefficient(
                            buttonPresses[
                                    buttonIndex],
                            1.0);
                }
            }
        }
    }

    private static void configureObjective(
            MPSolver solver,
            MPVariable[] buttonPresses) {

        MPObjective objective =
                solver.objective();

        for (MPVariable variable :
                buttonPresses) {

            objective.setCoefficient(
                    variable,
                    1.0);
        }

        objective.setMinimization();
    }

    private static int calculateTotalPresses(
            MPVariable[] buttonPresses) {

        long totalPresses = 0;

        for (MPVariable variable :
                buttonPresses) {

            // Le variabili sono intere, ma OR-Tools espone
            // il risultato come double.
            long presses =
                    Math.round(
                            variable.solutionValue());

            totalPresses =
                    Math.addExact(
                            totalPresses,
                            presses);
        }

        return Math.toIntExact(
                totalPresses);
    }
}