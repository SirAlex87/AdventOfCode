package com.borzacchiello.advent_of_code.strategy.impl.task10;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.task10.MachineButton;
import com.borzacchiello.advent_of_code.model.task10.MachineConfiguration;

public final class IndicatorLightCalculator {

    private IndicatorLightCalculator() {
        // Classe di utility non istanziabile.
    }

    public static int calculateMinimumPresses(
            MachineConfiguration machine) {

        BigInteger targetState =
                createTargetState(
                        machine.indicatorTarget());

        if (targetState.equals(
                BigInteger.ZERO)) {

            return 0;
        }

        List<BigInteger> buttonMasks =
                machine.buttons()
                        .stream()
                        .map(IndicatorLightCalculator
                                ::createButtonMask)
                        .toList();

        return breadthFirstSearch(
                targetState,
                buttonMasks);
    }

    private static int breadthFirstSearch(
            BigInteger targetState,
            List<BigInteger> buttonMasks) {

        Queue<BigInteger> queue =
                new ArrayDeque<>();

        Map<BigInteger, Integer> distance =
                new HashMap<>();

        BigInteger initialState =
                BigInteger.ZERO;

        queue.add(initialState);
        distance.put(initialState, 0);

        while (!queue.isEmpty()) {

            BigInteger currentState =
                    queue.remove();

            int currentDistance =
                    distance.get(currentState);

            for (BigInteger buttonMask :
                    buttonMasks) {

                BigInteger nextState =
                        currentState.xor(
                                buttonMask);

                if (distance.containsKey(
                        nextState)) {

                    continue;
                }

                int nextDistance =
                        currentDistance + 1;

                if (nextState.equals(
                        targetState)) {

                    return nextDistance;
                }

                distance.put(
                        nextState,
                        nextDistance);

                queue.add(nextState);
            }
        }

        throw new InvalidTaskInputException(
                "Task 10 indicator target "
                        + "cannot be reached");
    }

    private static BigInteger createTargetState(
            String indicatorTarget) {

        BigInteger state =
                BigInteger.ZERO;

        for (int position = 0;
             position < indicatorTarget.length();
             position++) {

            char indicator =
                    indicatorTarget.charAt(position);

            if (indicator == '#') {
                state = state.setBit(position);
            }
        }

        return state;
    }

    private static BigInteger createButtonMask(
            MachineButton button) {

        BigInteger mask =
                BigInteger.ZERO;

        for (int position :
                button.positions()) {

            mask = mask.setBit(position);
        }

        return mask;
    }
}