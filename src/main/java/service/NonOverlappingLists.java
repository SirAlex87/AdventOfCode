package service;

import java.util.*;

import org.javatuples.Pair;



public class NonOverlappingLists {
    private NonOverlappingLists() {
        /* This utility class should not be instantiated */
    }


    // Verifica intersezione tra due intervalli
    static boolean intersect(Pair<Integer, Integer> a, Pair<Integer, Integer> b) {
        return a.equals(b);
    }

    // Verifica se due liste sono compatibili
    static boolean compatible(List<Pair<Integer, Integer>> l1,
                              List<Pair<Integer, Integer>> l2) {
        for (Pair<Integer, Integer> p1 : l1) {
            for (Pair<Integer, Integer> p2 : l2) {
                if (intersect(p1, p2)) {
                    return false;
                }
            }
        }
        return true;
    }

    // Verifica se una lista è compatibile con tutte quelle già scelte
    static boolean isValid(List<List<Pair<Integer, Integer>>> chosen,
                           List<Pair<Integer, Integer>> candidate) {
        for (List<Pair<Integer, Integer>> l : chosen) {
            if (!compatible(l, candidate)) {
                return false;
            }
        }
        return true;
    }

    // Backtracking per trovare n liste valide
    static boolean backtrack(List<List<Pair<Integer, Integer>>> input,
                             int n,
                             int index,
                             List<List<Pair<Integer, Integer>>> current,
                             List<List<Pair<Integer, Integer>>> result) {

        if (current.size() == n) {
            result.addAll(current);
            return true;
        }

        for (int i = index; i < input.size(); i++) {
            List<Pair<Integer, Integer>> candidate = input.get(i);

            if (isValid(current, candidate)) {
                current.add(candidate);

                if (backtrack(input, n, i + 1, current, result)) {
                    return true;
                }

                current.remove(current.size() - 1);
            }
        }

        return false;
    }

    public static List<List<Pair<Integer, Integer>>> findNLists(
            List<List<Pair<Integer, Integer>>> input, int n) {

        List<List<Pair<Integer, Integer>>> result = new ArrayList<>();
        backtrack(input, n, 0, new ArrayList<>(), result);
        return result;
    }
}
