package service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

public class ServiceTest7 {

	private static int i = 0;
	private char[][] arrayFinal = null;
	private List<String> lista = new ArrayList<String>();
	private Map<Integer, BigInteger> mappa = new HashMap<>();
	private int conta = 0;

	public void populateArray(String s) {
		arrayFinal[i] = s.toCharArray();
		i++;
	}

	public void initilizeArray(String[] strParts) {
		arrayFinal = new char[strParts.length][strParts[0].length()];

	}

	public void printArray() {

		for (int i = 0; i < arrayFinal.length; i++) { // scorre le righe
			for (int j = 0; j < arrayFinal[i].length; j++) { // scorre le colonne
				System.out.print(arrayFinal[i][j] + " ");
			}
			System.out.println(); // nuova riga per ogni riga
		}

	}

	public String calculateSplit(int i, int j) {
		while (i < arrayFinal.length && j < arrayFinal[0].length && j >= 0) {
			if (arrayFinal[i][j] == '^') {
				this.conta++;
				int jTempSup = j + 1;
				int jTempInf = j - 1;
				if (arrayFinal[i][jTempSup] == '.') {
					this.calculateSplit(i, jTempSup);
				}
				if (arrayFinal[i][jTempInf] == '.') {
					this.calculateSplit(i, jTempInf);
				}
				break;
			} else if (arrayFinal[i][j] == '|') {
				break;
			} else if (arrayFinal[i][j] == '.') {
				arrayFinal[i][j] = '|';
			}
			i++;
		}

		return null;

	}

	public void calculateTimelines(int indiceStart) {
		mappa.put(indiceStart, BigInteger.ONE);
		for (int i = 2; i < arrayFinal.length; i = i + 2) {
			for (int j = 1; j < arrayFinal[0].length - 1; j++) {
				if (arrayFinal[i][j] == '^' && arrayFinal[i - 1][j] == '|') {
					int jTempSup = j + 1;
					int jTempInf = j - 1;
					if (null == mappa.get(jTempSup)) {
						mappa.put(jTempSup, mappa.get(j));
					} else {
						mappa.put(jTempSup, mappa.get(j).add(mappa.get(jTempSup)));
					}
					if (null == mappa.get(jTempInf)) {
						mappa.put(jTempInf, mappa.get(j));
					} else {
						mappa.put(jTempInf, mappa.get(j).add(mappa.get(jTempInf)));
					}
					mappa.remove(j);
				}
			}
		}
	}

	public int getConta() {
		return conta;
	}

	public void setConta(int conta) {
		this.conta = conta;
	}

	@Override
	public String toString() {
		return "ServiceTest7 [mappa=" + mappa + "]";
	}

	public BigInteger sumMap() {


BigInteger sum = mappa.values()
                            .stream()
                            .reduce(BigInteger.ZERO, BigInteger::add);


		return sum;
	}

}
