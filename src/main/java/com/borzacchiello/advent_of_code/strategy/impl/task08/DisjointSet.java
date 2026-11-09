package com.borzacchiello.advent_of_code.strategy.impl.task08;

public final class DisjointSet {

    private final int[] parent;
    private final int[] size;

    private int componentCount;

    public DisjointSet(int elementCount) {

        if (elementCount < 0) {
            throw new IllegalArgumentException(
                    "Element count cannot be negative");
        }

        parent = new int[elementCount];
        size = new int[elementCount];

        componentCount = elementCount;

        for (int index = 0;
             index < elementCount;
             index++) {

            parent[index] = index;
            size[index] = 1;
        }
    }
    public int find(int element) {

        validateElement(element);

        // Path compression:
        // ogni ricerca avvicina l'elemento alla radice
        // del proprio circuito.
        if (parent[element] != element) {
            parent[element] =
                    find(parent[element]);
        }

        return parent[element];
    }

    public boolean union(
            int first,
            int second) {

        int firstRoot = find(first);
        int secondRoot = find(second);

        // I due elementi appartengono già
        // allo stesso circuito.
        if (firstRoot == secondRoot) {
            return false;
        }

        // Union by size:
        // il circuito più piccolo viene collegato
        // alla radice del circuito più grande.
        if (size[firstRoot]
                < size[secondRoot]) {

            int temporary = firstRoot;
            firstRoot = secondRoot;
            secondRoot = temporary;
        }

        parent[secondRoot] = firstRoot;

        size[firstRoot] +=
                size[secondRoot];

        componentCount--;

        return true;
    }

    public int componentSize(int element) {

        int root = find(element);

        return size[root];
    }

    public int componentCount() {
        return componentCount;
    }

    private void validateElement(
            int element) {

        if (element < 0
                || element >= parent.length) {

            throw new IllegalArgumentException(
                    "Invalid element index: "
                            + element);
        }
    }
}