package service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ServiceTest4 {

	private List<ArrayList<String>> lista = new ArrayList<ArrayList<String>>();
	private List<String> listaRemoved = new ArrayList<String>();

	public void populateList(String s) {
		lista.add((ArrayList<String>) s.chars().mapToObj(c -> String.valueOf((char) c)) // convert each char to String
				.collect(Collectors.toList()));
	}

	@Override
	public String toString() {
		return "ServiceTest4 [lista=" + lista + "]";
	}

	public int calculateRollsPaperAvailable() {
		listaRemoved.clear();
		int conta = 0;
		for (int i = 0; i < lista.size(); i++) {
			List<String> listaLine = lista.get(i);
			for (int j = 0; j < listaLine.size(); j++) {
				int contaTemp = 0;
				if (listaLine.get(j).equals("@")) {
					// ha una riga sopra
					if (i > 0) {
						List<String> listaLineTemp = lista.get(i - 1);
						if (j > 0) {
							if (listaLineTemp.get(j - 1).equals("@")) {
								contaTemp++;
							}
						}
						if (j < listaLineTemp.size() - 1) {
							if (listaLineTemp.get(j + 1).equals("@")) {
								contaTemp++;
							}
						}
						if (listaLineTemp.get(j).equals("@")) {
							contaTemp++;
						}
					}
					// riga in mezzo
					if (j > 0) {
						if (listaLine.get(j - 1).equals("@")) {
							contaTemp++;
						}
					}
					if (j < listaLine.size() - 1) {
						if (listaLine.get(j + 1).equals("@")) {
							contaTemp++;
						}
					}
					// ha una riga sotto
					if (i < lista.size() - 1) {
						List<String> listaLineTemp = lista.get(i + 1);
						if (j > 0) {
							if (listaLineTemp.get(j - 1).equals("@")) {
								contaTemp++;
							}
						}
						if (j < listaLineTemp.size() - 1) {
							if (listaLineTemp.get(j + 1).equals("@")) {
								contaTemp++;
							}
						}
						if (listaLineTemp.get(j).equals("@")) {
							contaTemp++;
						}
					}
					if (contaTemp < 4) {
						conta++;
						listaRemoved.add(i+","+j);
					}
				}

			}
		}
		return conta;

	}
	
	public void updateLista() {
		for (int i = 0; i < listaRemoved.size(); i++) {
			String[] a =listaRemoved.get(i).split(",");
			lista.get(Integer.parseInt(a[0])).set(Integer.parseInt(a[1]), "x");
		}
	}
}
