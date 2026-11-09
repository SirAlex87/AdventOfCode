package com.borzacchiello.advent_of_code.strategy.impl.task08;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.model.task08.JunctionBox;
import com.borzacchiello.advent_of_code.model.task08.JunctionBoxData;
import com.borzacchiello.advent_of_code.model.task08.JunctionConnection;
import com.borzacchiello.advent_of_code.strategy.TaskStrategy;

@Component
public class Task08Part2Strategy
        implements TaskStrategy<JunctionBoxData, BigInteger> {

    @Override
    public TaskKey getKey() {
        return new TaskKey(8, 2);
    }

    @Override
    public BigInteger execute(
            JunctionBoxData data) {

        validateInput(data);

        List<JunctionConnection> connections =
                JunctionConnectionCalculator
                        .calculateConnections(
                                data.boxes());

        DisjointSet disjointSet =
                new DisjointSet(
                        data.boxes().size());

        for (JunctionConnection connection :
                connections) {

            boolean circuitsMerged =
                    disjointSet.union(
                            connection.firstIndex(),
                            connection.secondIndex());

            // Ci interessa solamente una connessione
            // che unisce realmente due circuiti distinti.
            //
            // Quando rimane un solo componente,
            // questa è l'ultima connessione necessaria.
            if (circuitsMerged
                    && disjointSet.componentCount()
                    == 1) {

                return multiplyXCoordinates(
                        data,
                        connection);
            }
        }

        throw new IllegalStateException(
                "Unable to connect "
                        + "all junction boxes");
    }

    private BigInteger multiplyXCoordinates(
            JunctionBoxData data,
            JunctionConnection connection) {

        JunctionBox firstBox =
                data.boxes().get(
                        connection.firstIndex());

        JunctionBox secondBox =
                data.boxes().get(
                        connection.secondIndex());

        BigInteger firstX =
                BigInteger.valueOf(
                        firstBox.x());

        BigInteger secondX =
                BigInteger.valueOf(
                        secondBox.x());

        return firstX.multiply(secondX);
    }

    private void validateInput(
            JunctionBoxData data) {

        if (data == null) {
            throw new InvalidTaskInputException(
                    "Task 8 data cannot be null");
        }

        if (data.boxes().size() < 2) {
            throw new InvalidTaskInputException(
                    "Task 8 needs at least "
                            + "two junction boxes");
        }
    }
}