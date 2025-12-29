package service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ServiceTest5 {

	private List<String> rangeFresh = new ArrayList<String>();
	private List<BigInteger> listaAvailable = new ArrayList<BigInteger>();
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

		int countDistinct = (int) listaAvailable.stream().distinct() // considera ogni valore una sola volta
				.filter(x -> isPresent(x)) // presente nell'altra lista
				.count();
		return countDistinct;
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

//	public BigInteger howManyFresh() {
//		BigInteger conta = BigInteger.ZERO;
//		BigInteger[][] lista = new BigInteger[rangeFresh.size()][2];
//		for (int i = 0; i < rangeFresh.size(); i++) {
//			String[] splitArray = rangeFresh.get(i).split("-");
//			BigInteger start = new BigInteger(splitArray[0]);
//			BigInteger end = new BigInteger(splitArray[1]);
//			if (i == 0) {
//				lista[i][0] = start;
//				lista[i][1] = end;
//			} else {
//				boolean isPresentAlreadyInArrayThisInterval = false;
//				for (int j = 0; j < i; j++) {
//					if (start.compareTo(lista[j][0]) >= 0 && end.compareTo(lista[j][1]) <= 0) {
//						break;
//					} else if (start.compareTo(lista[j][0]) >= 0 && start.compareTo(lista[j][1]) <= 0
//							&& end.compareTo(lista[j][1]) >= 0) {
//						lista[j][1] = end;
//						isPresentAlreadyInArrayThisInterval = true;
//					} else if (start.compareTo(lista[j][0]) <= 0 && end.compareTo(lista[j][0]) >= 0
//							&& end.compareTo(lista[j][1]) <= 0) {
//						lista[j][0] = start;
//						isPresentAlreadyInArrayThisInterval = true;
//					} else if (start.compareTo(lista[j][0]) <= 0 && end.compareTo(lista[j][1]) >= 0) {
//						lista[j][0] = start;
//						lista[j][1] = end;
//						isPresentAlreadyInArrayThisInterval = true;
//					}
//				}
//				if (!isPresentAlreadyInArrayThisInterval) {
//					lista[i][0] = start;
//					lista[i][1] = end;
//				}
//			}
//
//		}
//		for (int j = 0; j < lista.length; j++) {
//			conta = conta.add(lista[j][1].subtract(lista[j][0]));
//		}
//
//		return conta;
//	}

	public BigInteger howManyFresh() {
		BigInteger conta = BigInteger.ZERO;
		BigInteger[][] arrayLista = new BigInteger[rangeFresh.size()][2];
		List<BigInteger> lista1 = new ArrayList<BigInteger>();
		List<BigInteger> lista2 = new ArrayList<BigInteger>();
		List<BigInteger> listaFinale1 = new ArrayList<BigInteger>();
		List<BigInteger> listaFinale2 = new ArrayList<BigInteger>();
		for (int i = 0; i < rangeFresh.size(); i++) {
			String[] splitArray = rangeFresh.get(i).split("-");
			BigInteger start = new BigInteger(splitArray[0]);
			BigInteger end = new BigInteger(splitArray[1]);
			lista1.add(start);
			lista2.add(end);
		}

		Optional<BigInteger> min = Optional.ofNullable(BigInteger.ZERO);
		Optional<BigInteger> max = Optional.ofNullable(BigInteger.ZERO);
		Optional<BigInteger> min2 = Optional.ofNullable(BigInteger.ZERO);
		for (int i = 0; i < rangeFresh.size(); i++) {
			if (i == 0) {
				min = lista1.stream().min(Comparator.naturalOrder());
				listaFinale1.add(min.get());
				lista1.remove(min.get());
				min2 = lista2.stream().min(Comparator.naturalOrder());
				listaFinale2.add(min2.get());
				lista2.remove(min2.get());
			} else {
				min = lista1.stream().min(Comparator.naturalOrder());
				max = listaFinale2.stream().max(Comparator.naturalOrder());
				if (min.get().compareTo(max.get()) <= 0) {
					listaFinale1.add(max.get().add(BigInteger.ONE));
				}
				else {
					listaFinale1.add(min.get());
				}
				lista1.remove(min.get());
				min2 = lista2.stream().min(Comparator.naturalOrder());
				listaFinale2.add(min2.get());
				lista2.remove(min2.get());
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
