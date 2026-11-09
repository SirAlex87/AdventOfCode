package com.borzacchiello.advent_of_code.util.algorithm.math;

/**
 * Utility class containing common mathematical algorithms.
 */
public final class MathAlgorithms {

    private MathAlgorithms() {
        // Utility class: prevents instantiation.
    }

    /**
     * Calculates the greatest common divisor (GCD) of two numbers
     * using the Euclidean algorithm.
     *
     * @param first  the first number
     * @param second the second number
     * @return the greatest common divisor
     */
    public static long gcd(long first, long second) {

        long a = Math.abs(first);
        long b = Math.abs(second);

        while (b != 0) {
            long remainder = a % b;
            a = b;
            b = remainder;
        }

        return a;
    }

    /**
     * Calculates the least common multiple (LCM) of two numbers.
     *
     * @param first  the first number
     * @param second the second number
     * @return the least common multiple
     */
    public static long lcm(long first, long second) {

        if (first == 0 || second == 0) {
            return 0;
        }

        return Math.abs((first / gcd(first, second)) * second);
    }

    /**
     * Calculates a positive modulo.
     *
     * <p>
     * Unlike the Java remainder operator (%), this method always returns
     * a value between 0 (inclusive) and modulo (exclusive).
     * </p>
     *
     * @param value  the value
     * @param modulo the modulo, which must be positive
     * @return the positive modulo result
     * @throws IllegalArgumentException if modulo is not positive
     */
    public static long positiveMod(long value, long modulo) {

        if (modulo <= 0) {
            throw new IllegalArgumentException(
                    "Modulo must be greater than zero");
        }

        return Math.floorMod(value, modulo);
    }
}