package com.borzacchiello.advent_of_code.strategy.impl.task08;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.model.task08.JunctionBoxData;
import com.borzacchiello.advent_of_code.model.task08.JunctionConnection;
import com.borzacchiello.advent_of_code.strategy.TaskStrategy;

@Component
public class Task08Part1Strategy
        implements TaskStrategy<JunctionBoxData, BigInteger> {

    private static final int CONNECTIONS_TO_PROCESS =
            1000;

    private static final int LARGEST_CIRCUITS =
            3;

    @Override
    public TaskKey getKey() {
        return new TaskKey(8, 1);
    }

    @Override
    public BigInteger execute(
            JunctionBoxData data) {

        return calculate(
                data,
                CONNECTIONS_TO_PROCESS);
    }

    public BigInteger calculate(
            JunctionBoxData data,
            int connectionsToProcess) {

        validateInput(
                data,
                connectionsToProcess);

        List<JunctionConnection> connections =
                JunctionConnectionCalculator
                        .calculateConnections(
                                data.boxes());

        int actualConnectionsToProcess =
                Math.min(
                        connectionsToProcess,
                        connections.size());

        DisjointSet disjointSet =
                new DisjointSet(
                        data.boxes().size());

        // Devono essere processate esattamente
        // le prime N coppie più vicine.
        //
        // Una coppia conta anche quando le due junction box
        // appartengono già allo stesso circuito.
        for (int connectionIndex = 0;
             connectionIndex
                     < actualConnectionsToProcess;
             connectionIndex++) {

            JunctionConnection connection =
                    connections.get(connectionIndex);

            disjointSet.union(
                    connection.firstIndex(),
                    connection.secondIndex());
        }

        return multiplyLargestCircuitSizes(
                disjointSet,
                data.boxes().size());
    }

    private BigInteger multiplyLargestCircuitSizes(
            DisjointSet disjointSet,
            int boxCount) {

        Set<Integer> roots =
                new HashSet<>();

        for (int boxIndex = 0;
             boxIndex < boxCount;
             boxIndex++) {

            roots.add(
                    disjointSet.find(boxIndex));
        }

        List<Integer> circuitSizes =
                roots.stream()
                        .map(disjointSet::componentSize)
                        .sorted(
                                Comparator.reverseOrder())
                        .toList();

        BigInteger result =
                BigInteger.ONE;

        int circuitsToMultiply =
                Math.min(
                        LARGEST_CIRCUITS,
                        circuitSizes.size());

        for (int circuitIndex = 0;
             circuitIndex < circuitsToMultiply;
             circuitIndex++) {

            result = result.multiply(
                    BigInteger.valueOf(
                            circuitSizes.get(
                                    circuitIndex)));
        }

        return result;
    }

    private void validateInput(
            JunctionBoxData data,
            int connectionsToProcess) {

        if (data == null) {
            throw new InvalidTaskInputException(
                    "Task 8 data cannot be null");
        }

        if (data.boxes().isEmpty()) {
            throw new InvalidTaskInputException(
                    "Task 8 contains no junction boxes");
        }

        if (connectionsToProcess < 0) {
            throw new InvalidTaskInputException(
                    "Connections to process "
                            + "cannot be negative");
        }
    }
}