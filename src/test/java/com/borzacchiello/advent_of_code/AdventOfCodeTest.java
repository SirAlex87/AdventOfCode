package com.borzacchiello.advent_of_code;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AdventOfCodeTest {

    private String input;

    @BeforeEach
    void setUp() {
        input = "AdventOfCode";
    }

    @Test
    @DisplayName("Verifica che il valore iniziale non sia null")
    void shouldNotBeNull() {
        assertNotNull(input);
    }

    @Test
    @DisplayName("Verifica il valore della stringa")
    void shouldHaveExpectedValue() {
        assertEquals("AdventOfCode", input);
    }

    @Test
    @DisplayName("Verifica che la stringa non sia vuota")
    void shouldNotBeEmpty() {
        assertFalse(input.isEmpty());
    }

    @Test
    @DisplayName("Verifica una condizione booleana")
    void shouldStartWithAdvent() {
        assertTrue(input.startsWith("Advent"));
    }
}
