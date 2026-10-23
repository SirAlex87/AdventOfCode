package service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ServiceTest5 {

	private static final String LISTA_NON_CONTIENE_ELEMENTI = "lista1 non contiene elementi";
	private List<String> rangeFresh = new ArrayList<>();
	private List<BigInteger> listaAvailable = new ArrayList<>();
	private boolean startAvailable;

	public void populateList(String s) {
		if (s.isEmpty()) {
			startAvailable = true;
		}
		if (startAvailable) {
			if (!s.isEmpty())
				listaAvailable.add(new BigInteger(s));
		} else {
			rangeFresh.add(s);
		}
	}

	public int chechAvailableAreFresh() {

		return (int) listaAvailable.stream().distinct() // considera ogni valore una sola volta
				.filter(this::isPresent) // presente nell'altra lista
				.count();
	}

	private boolean isPresent(BigInteger x) {
		boolean check = false;
		for (int i = 0; i < rangeFresh.size(); i++) {
			String[] splitArray = rangeFresh.get(i).split("-");
			BigInteger start = new BigInteger(splitArray[0]);
			BigInteger end = new BigInteger(splitArray[1]);
			check = x.compareTo(start) >= 0 && x.compareTo(end) <= 0;
			if (check) {
				break;
			}
		}
		return check;
	}

	public BigInteger howManyFresh() {
		BigInteger conta = BigInteger.ZERO;
		List<BigInteger> lista1 = new ArrayList<>();
		List<BigInteger> lista2 = new ArrayList<>();
		List<BigInteger> listaFinale1 = new ArrayList<>();
		List<BigInteger> listaFinale2 = new ArrayList<>();
		for (int i = 0; i < rangeFresh.size(); i++) {
			String[] splitArray = rangeFresh.get(i).split("-");
			BigInteger start = new BigInteger(splitArray[0]);
			BigInteger end = new BigInteger(splitArray[1]);
			lista1.add(start);
			lista2.add(end);
		}
		BigInteger min;
		BigInteger max;
		BigInteger min2;
		for (int i = 0; i < rangeFresh.size(); i++) {
			if (i == 0) {
				min = lista1.stream()
				        .min(Comparator.naturalOrder())
				        .orElseThrow(() -> new IllegalStateException(LISTA_NON_CONTIENE_ELEMENTI));
				listaFinale1.add(min);
				lista1.remove(min);
				min2 = lista2.stream().min(Comparator.naturalOrder()).orElseThrow(() -> new IllegalStateException(LISTA_NON_CONTIENE_ELEMENTI));
				listaFinale2.add(min2);
				lista2.remove(min2);
			} else {
				min = lista1.stream().min(Comparator.naturalOrder()).orElseThrow(() -> new IllegalStateException(LISTA_NON_CONTIENE_ELEMENTI));
				max = listaFinale2.stream().max(Comparator.naturalOrder()).orElseThrow(() -> new IllegalStateException(LISTA_NON_CONTIENE_ELEMENTI));
				if (min.compareTo(max) <= 0) {
					listaFinale1.add(max.add(BigInteger.ONE));
				} else {
					listaFinale1.add(min);
				}
				lista1.remove(min);
				min2 = lista2.stream().min(Comparator.naturalOrder()).orElseThrow(() -> new IllegalStateException(LISTA_NON_CONTIENE_ELEMENTI));
				listaFinale2.add(min2);
				lista2.remove(min2);
			}
		}
		for (int j = 0; j < rangeFresh.size(); j++) {
			conta = conta.add(listaFinale2.get(j).subtract(listaFinale1.get(j)).add(BigInteger.ONE));
		}
		return conta;
	}

	@Override
	public String toString() {
		return "ServiceTest5 [rangeFresh=" + rangeFresh + ", listaAvailable=" + listaAvailable + ", startAvailable="
				+ startAvailable + "]";
	}
}
