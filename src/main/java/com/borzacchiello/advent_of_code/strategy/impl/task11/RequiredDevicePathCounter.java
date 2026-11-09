package com.borzacchiello.advent_of_code.strategy.impl.task11;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.task11.DeviceGraph;

public final class RequiredDevicePathCounter {

    private static final String REQUIRED_DAC =
            "dac";

    private static final String REQUIRED_FFT =
            "fft";

    private RequiredDevicePathCounter() {
        // Classe di utility non istanziabile.
    }

    public static BigInteger countPaths(
            DeviceGraph graph,
            String start,
            String destination) {

        validateGraph(
                graph,
                start,
                destination);

        Map<PathState, BigInteger> memo =
                new HashMap<>();

        Set<PathState> currentPath =
                new HashSet<>();

        return countPaths(
                graph,
                new PathState(
                        start,
                        start.equals(REQUIRED_DAC),
                        start.equals(REQUIRED_FFT)),
                destination,
                memo,
                currentPath);
    }

    private static BigInteger countPaths(
            DeviceGraph graph,
            PathState state,
            String destination,
            Map<PathState, BigInteger> memo,
            Set<PathState> currentPath) {

        if (state.device()
                .equals(destination)) {

            if (state.visitedDac()
                    && state.visitedFft()) {

                return BigInteger.ONE;
            }

            return BigInteger.ZERO;
        }

        BigInteger cached =
                memo.get(state);

        if (cached != null) {
            return cached;
        }

        if (!currentPath.add(state)) {
            throw new InvalidTaskInputException(
                    "Task 11 graph contains a cycle "
                            + "involving device: "
                            + state.device());
        }

        BigInteger total =
                BigInteger.ZERO;

        for (String next :
                graph.destinations(
                        state.device())) {

            PathState nextState =
                    new PathState(
                            next,
                            state.visitedDac()
                                    || next.equals(
                                            REQUIRED_DAC),
                            state.visitedFft()
                                    || next.equals(
                                            REQUIRED_FFT));

            total = total.add(
                    countPaths(
                            graph,
                            nextState,
                            destination,
                            memo,
                            currentPath));
        }

        currentPath.remove(state);

        memo.put(
                state,
                total);

        return total;
    }

    private static void validateGraph(
            DeviceGraph graph,
            String start,
            String destination) {

        if (!graph.containsDevice(start)) {
            throw new InvalidTaskInputException(
                    "Task 11 start device not found: "
                            + start);
        }

        if (!graph.containsDevice(destination)) {
            throw new InvalidTaskInputException(
                    "Task 11 destination not found: "
                            + destination);
        }

        if (!graph.containsDevice(REQUIRED_DAC)) {
            throw new InvalidTaskInputException(
                    "Task 11 required device "
                            + "not found: dac");
        }

        if (!graph.containsDevice(REQUIRED_FFT)) {
            throw new InvalidTaskInputException(
                    "Task 11 required device "
                            + "not found: fft");
        }
    }

    private record PathState(
            String device,
            boolean visitedDac,
            boolean visitedFft) {
    }
}