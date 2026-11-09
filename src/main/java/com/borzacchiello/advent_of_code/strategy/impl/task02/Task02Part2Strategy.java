package com.borzacchiello.advent_of_code.strategy.impl.task02;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Component;

import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.model.task02.ProductIdRange;
import com.borzacchiello.advent_of_code.strategy.TaskStrategy;

@Component
public class Task02Part2Strategy implements TaskStrategy<List<ProductIdRange>, BigInteger> {

    @Override
    public TaskKey getKey() {
        return new TaskKey(2, 2);
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
     * ripetuta almeno due volte.
     *
     * @param id ID da verificare
     * @return true se l'ID è invalido, false altrimenti
     */
    private boolean isInvalid(BigInteger id) {

        String value = id.toString();
        int length = value.length();

        for (int patternLength = 1;
             patternLength <= length / 2;
             patternLength++) {

            if (length % patternLength == 0
                    && isRepeatedPattern(value, patternLength)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Verifica se la stringa è composta esclusivamente dalla
     * ripetizione del pattern iniziale della lunghezza specificata.
     *
     * @param value stringa da verificare
     * @param patternLength lunghezza del pattern da verificare
     * @return true se il pattern si ripete per tutta la stringa
     */
    private boolean isRepeatedPattern(String value, int patternLength) {

        for (int i = patternLength; i < value.length(); i++) {

            if (value.charAt(i)
                    != value.charAt(i % patternLength)) {
                return false;
            }
        }

        return true;
    }
}