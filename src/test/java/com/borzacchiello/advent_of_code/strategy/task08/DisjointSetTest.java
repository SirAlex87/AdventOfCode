package com.borzacchiello.advent_of_code.strategy.task08;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.borzacchiello.advent_of_code.strategy.impl.task08.DisjointSet;

class DisjointSetTest {

    private DisjointSet disjointSet;

    @BeforeEach
    void setUp() {

        disjointSet =
                new DisjointSet(5);
    }

    @Test
    void shouldStartWithOneComponentPerElement() {

        assertEquals(
                5,
                disjointSet.componentCount());

        for (int index = 0;
             index < 5;
             index++) {

            assertEquals(
                    1,
                    disjointSet.componentSize(index));
        }
    }

    @Test
    void shouldMergeTwoComponents() {

        boolean merged =
                disjointSet.union(
                        0,
                        1);

        assertTrue(merged);

        assertEquals(
                disjointSet.find(0),
                disjointSet.find(1));

        assertEquals(
                4,
                disjointSet.componentCount());

        assertEquals(
                2,
                disjointSet.componentSize(0));
    }

    @Test
    void shouldNotMergeComponentsAlreadyConnected() {

        disjointSet.union(0, 1);

        boolean mergedAgain =
                disjointSet.union(
                        0,
                        1);

        assertFalse(mergedAgain);

        assertEquals(
                4,
                disjointSet.componentCount());
    }

    @Test
    void shouldMergeMultipleCircuits() {

        disjointSet.union(0, 1);
        disjointSet.union(1, 2);

        disjointSet.union(3, 4);

        assertEquals(
                2,
                disjointSet.componentCount());

        assertEquals(
                3,
                disjointSet.componentSize(0));

        assertEquals(
                2,
                disjointSet.componentSize(3));

        disjointSet.union(2, 3);

        assertEquals(
                1,
                disjointSet.componentCount());

        assertEquals(
                5,
                disjointSet.componentSize(4));
    }
}