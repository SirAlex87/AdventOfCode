package service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServiceTest7 {

	private static final Logger logger = LogManager.getLogger(ServiceTest7.class);

	private int contatore = 0;
	private char[][] arrayFinal = null;
	private Map<Integer, BigInteger> mappa = new HashMap<>();
	private int conta = 0;

	public void populateArray(String s) {
		arrayFinal[contatore] = s.toCharArray();
		contatore++;
	}

	public void initilizeArray(String[] strParts) {
		arrayFinal = new char[strParts.length][strParts[0].length()];

	}

	public void printArray() {

		for (int x = 0; x < arrayFinal.length; x++) { // scorre le righe
			for (int j = 0; j < arrayFinal[x].length; j++) { // scorre le colonne
				logger.log(Level.DEBUG, "{} ", arrayFinal[x][j]);
			}
			logger.debug("\n"); // nuova riga per ogni riga
		}

	}

	public String calculateSplit(int i, int j) {
		while (i < arrayFinal.length && j < arrayFinal[0].length && j >= 0 && arrayFinal[i][j] != '|') {
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
			} else if (arrayFinal[i][j] == '.') {
				arrayFinal[i][j] = '|';
			}
			i++;
		}

		return null;

	}

	public void calculateTimelines(int indiceStart) {
		mappa.put(indiceStart, BigInteger.ONE);
		for (int x = 2; x < arrayFinal.length; x = x + 2) {
			for (int j = 1; j < arrayFinal[0].length - 1; j++) {
				if (arrayFinal[x][j] == '^' && arrayFinal[x - 1][j] == '|') {
					manageMappa(j);
				}
			}
		}
	}

	private void manageMappa(int j) {
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
		return mappa.values().stream().reduce(BigInteger.ZERO, BigInteger::add);

	}

}
