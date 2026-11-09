package com.borzacchiello.advent_of_code.strategy.impl.task06;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.model.task06.WorksheetData;
import com.borzacchiello.advent_of_code.model.task06.WorksheetProblem;
import com.borzacchiello.advent_of_code.strategy.TaskStrategy;

@Component
public class Task06Part1Strategy
        implements TaskStrategy<WorksheetData, BigInteger> {

    @Override
    public TaskKey getKey() {
        return new TaskKey(6, 1);
    }

    /**
     * Risolve tutti i problemi leggendo ogni riga numerica
     * nel modo standard e somma i risultati ottenuti.
     */
    @Override
    public BigInteger execute(
            WorksheetData worksheet) {

        List<WorksheetProblem> problems =
                WorksheetProblemExtractor.extract(
                        worksheet);

        return problems.stream()
                .map(problem ->
                        StandardWorksheetCalculator.calculate(
                                worksheet,
                                problem))
                .reduce(
                        BigInteger.ZERO,
                        BigInteger::add);
    }
}