package com.borzacchiello.advent_of_code.model.task06;

import java.math.BigInteger;

public enum MathOperation {

    ADD('+') {
        @Override
        public BigInteger apply(
                BigInteger left,
                BigInteger right) {

            return left.add(right);
        }

        @Override
        public BigInteger identity() {
            return BigInteger.ZERO;
        }
    },

    MULTIPLY('*') {
        @Override
        public BigInteger apply(
                BigInteger left,
                BigInteger right) {

            return left.multiply(right);
        }

        @Override
        public BigInteger identity() {
            return BigInteger.ONE;
        }
    };

    private final char symbol;

    MathOperation(char symbol) {
        this.symbol = symbol;
    }

    public char symbol() {
        return symbol;
    }

    public abstract BigInteger apply(
            BigInteger left,
            BigInteger right);

    public abstract BigInteger identity();

    public static MathOperation fromSymbol(
            char symbol) {

        for (MathOperation operation : values()) {
            if (operation.symbol == symbol) {
                return operation;
            }
        }

        throw new IllegalArgumentException(
                "Unsupported operation: " + symbol);
    }
}