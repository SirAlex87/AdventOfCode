package com.borzacchiello.advent_of_code.strategy.impl.task03;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.model.task03.BatteryBank;
import com.borzacchiello.advent_of_code.strategy.TaskStrategy;

@Component
public class Task03Part1Strategy
        implements TaskStrategy<List<BatteryBank>, BigInteger> {

    private static final int BATTERIES_TO_SELECT = 2;

    @Override
    public TaskKey getKey() {
        return new TaskKey(3, 1);
    }

    /**
     * Calcola la somma dei massimi valori di joltage ottenibili
     * selezionando esattamente due batterie per ogni banca.
     *
     * @param banks banche di batterie da elaborare
     * @return somma dei massimi valori di joltage
     */
    @Override
    public BigInteger execute(List<BatteryBank> banks) {

        return banks.stream()
                .map(this::calculateMaximumJoltage)
                .reduce(BigInteger.ZERO, BigInteger::add);
    }

    /**
     * Calcola il massimo joltage ottenibile da una singola banca.
     *
     * @param bank banca di batterie da elaborare
     * @return massimo joltage ottenibile
     */
    private BigInteger calculateMaximumJoltage(
            BatteryBank bank) {

        return MaximumJoltageCalculator.calculate(
                bank,
                BATTERIES_TO_SELECT);
    }
}