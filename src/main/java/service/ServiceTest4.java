package service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ServiceTest4 {

	private static final String NET_STRING = "@";
	private List<ArrayList<String>> lista = new ArrayList<>();
	private List<String> listaRemoved = new ArrayList<>();
	
	private static final int[][] DIRECTIONS = {
	        {-1, -1}, {-1, 0}, {-1, 1},
	        { 0, -1},          { 0, 1},
	        { 1, -1}, { 1, 0}, { 1, 1}
	    };

	public void populateList(String s) {		
		lista.add(
		        new ArrayList<>(
		            s.chars()
		             .mapToObj(c -> String.valueOf((char) c))
		             .toList()
		        )
		    );
	}

	@Override
	public String toString() {
		return "ServiceTest4 [lista=" + lista + "]";
	}

	public int calculateRollsPaperAvailable() {
        listaRemoved.clear();

        int conta = 0;

        Predicate<Position> isNetPosition = position ->
                isValidPosition(position.row(), position.column())
                && NET_STRING.equals(
                        lista.get(position.row()).get(position.column())
                );

        for (int row = 0; row < lista.size(); row++) {
            List<String> currentLine = lista.get(row);

            for (int column = 0; column < currentLine.size(); column++) {

                if (!NET_STRING.equals(currentLine.get(column))) {
                    continue;
                }

                int adjacentNetCount = countAdjacentNet(
                        row,
                        column,
                        isNetPosition
                );

                if (adjacentNetCount < 4) {
                    conta++;
                    listaRemoved.add(row + "," + column);
                }
            }
        }

        return conta;
    }
	
	private int countAdjacentNet(
            int row,
            int column,
            Predicate<Position> condition) {

        int count = 0;

        for (int[] direction : DIRECTIONS) {
            Position adjacentPosition = new Position(
                    row + direction[0],
                    column + direction[1]
            );

            if (condition.test(adjacentPosition)) {
                count++;
            }
        }

        return count;
    }

    private boolean isValidPosition(int row, int column) {
        return row >= 0
                && row < lista.size()
                && column >= 0
                && column < lista.get(row).size();
    }

    private record Position(int row, int column) {
    }
	
	public void updateLista() {
		for (int i = 0; i < listaRemoved.size(); i++) {
			String[] a =listaRemoved.get(i).split(",");
			lista.get(Integer.parseInt(a[0])).set(Integer.parseInt(a[1]), "x");
		}
	}
}
