package com.borzacchiello.advent_of_code.strategy.impl.task02;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.model.task02.ProductIdRange;
import com.borzacchiello.advent_of_code.strategy.TaskStrategy;

@Component
public class Task02Part1Strategy  implements TaskStrategy<List<ProductIdRange>, BigInteger> {

	@Override
	public TaskKey getKey() {
		return new TaskKey(2, 1);
	}

	@Override
    public BigInteger execute(List<ProductIdRange> ranges) {
        return ranges.stream()
                .map(this::sumInvalidIds)
                .reduce(BigInteger.ZERO, BigInteger::add);
    }

    /**
     * Calcola la somma degli ID non validi presenti nell'intervallo specificato.
     *
     * @param range intervallo di ID da analizzare
     * @return somma degli ID non validi presenti nell'intervallo
     */
    private BigInteger sumInvalidIds(ProductIdRange range) {

        BigInteger sum = BigInteger.ZERO;

        for (BigInteger id = range.start();
             id.compareTo(range.end()) <= 0;
             id = id.add(BigInteger.ONE)) {

            if (isInvalid(id)) {
                sum = sum.add(id);
            }
        }

        return sum;
    }

    /**
     * Verifica se un ID è composto da una sequenza di cifre
     * ripetuta esattamente due volte.
     *
     * @param id ID da verificare
     * @return true se l'ID è invalido, false altrimenti
     */
    private boolean isInvalid(BigInteger id) {

        String value = id.toString();
        int length = value.length();

        if (length % 2 != 0) {
            return false;
        }

        int middle = length / 2;

        String firstHalf = value.substring(0, middle);
        String secondHalf = value.substring(middle);

        return firstHalf.equals(secondHalf);
    }
}
