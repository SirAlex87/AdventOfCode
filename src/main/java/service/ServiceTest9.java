package service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javatuples.Pair;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import util.Utility;

public class ServiceTest9 {
	private static final Logger logger = LogManager.getLogger(ServiceTest5.class);

	private List<Integer> listaY = new ArrayList<>();
	private List<Integer> listaX = new ArrayList<>();
	private List<List<Integer>> listaFinale = new ArrayList<>();
	private ListMultimap<Double, Pair<Integer, Integer>> multimap = ArrayListMultimap.create();
	private List<Pair<Integer, Integer>> listaCoppie = new ArrayList<>();
	private ListMultimap<BigInteger, Pair<Integer, Integer>> multimapBigInteger = ArrayListMultimap.create();
	private Map<Integer, Set<Integer>> mappaListaSimboliX = new HashMap<>();
	private Map<Integer, Set<Integer>> mappaListaSimboliY = new HashMap<>();

	public void populateList(String chiave) {
		List<Integer> numeri = Arrays.stream(chiave.split(",")) // -1 conserva eventuali vuoti
				.map(String::trim).filter(s -> !s.isEmpty()) // scarta elementi vuoti (es. "43,,44")
				.map(Integer::parseInt) // può lanciare NumberFormatException se non numerico
				.toList();

		listaY.add(numeri.get(0));
		listaX.add(numeri.get(1));
		List<Integer> listaTemp = new ArrayList<>();
		listaTemp.add(numeri.get(0));
		listaTemp.add(numeri.get(1));
		listaFinale.add(listaTemp);
		Pair<Integer, Integer> pair = new Pair<Integer, Integer>(numeri.get(0), numeri.get(1));
		listaCoppie.add(pair);
	}

	public void populateListaSimboliV3() {
		Integer maxRows = this.findMaxLista(listaX).get();
		Integer maxColumns = this.findMaxLista(listaY).get();
		logger.debug("maxRows:{}",maxRows);
		logger.debug("maxColumns:{}", maxColumns);
		IntStream.range(0, maxRows + 2).forEach(s -> {
			mappaListaSimboliX.put(s, new TreeSet<>());
		});
		listaCoppie.stream().forEach(c -> {
			mappaListaSimboliX.get(c.getValue1()).add(c.getValue0());
		});

		mappaListaSimboliX = calculatedMinMaxTreeSet(mappaListaSimboliX);

		IntStream.range(0, maxColumns + 3).forEach(s -> {
			mappaListaSimboliY.put(s, new TreeSet<>());
		});
		listaCoppie.stream().forEach(c -> {
			mappaListaSimboliY.get(c.getValue0()).add(c.getValue1());
		});

		mappaListaSimboliY = calculatedMinMaxTreeSet(mappaListaSimboliY);

	}

	private Map<Integer, Set<Integer>> calculatedMinMaxTreeSet(Map<Integer, Set<Integer>> mappa) {
		return mappa.entrySet().stream().filter(e -> !e.getValue().isEmpty())
				.collect(Collectors.toMap(Map.Entry::getKey, e -> {
					int min = e.getValue().stream().min(Integer::compareTo).get();
					int max = e.getValue().stream().max(Integer::compareTo).get();
					TreeSet<Integer> treeSet = new TreeSet<>();
					treeSet.add(min);
					treeSet.add(max);
					return treeSet;
				}));
	}

	public void calculateDistance2() {
		// array=new double[listaCoppie.size()][listaCoppie.size()];
		IntStream.range(0, listaCoppie.size()).forEach(s -> {
			IntStream.range(0, listaCoppie.size()).filter(x -> x != s).forEach(x -> {
				double distance = Math.sqrt(Math.pow(listaCoppie.get(x).getValue0() - listaCoppie.get(s).getValue0(), 2)
						+ Math.pow(listaCoppie.get(x).getValue1() - listaCoppie.get(s).getValue1(), 2));
				// array[s][x]=distance;
				multimap.put(distance, listaCoppie.get(x));
				multimap.put(distance, listaCoppie.get(s));
			});
		});

	}

	public void calculateArea() {
		// array=new double[listaCoppie.size()][listaCoppie.size()];
		IntStream.range(0, listaCoppie.size()).forEach(s -> {
			IntStream.range(0, listaCoppie.size()).filter(x -> x != s && containsTilesV3(x, s)).forEach(x -> {
				BigInteger coordinataY1 = null;
				BigInteger coordinataY2 = null;
				BigInteger coordinataX1 = null;
				BigInteger coordinataX2 = null;

				if (listaCoppie.get(x).getValue0() > listaCoppie.get(s).getValue0()) {
					coordinataY1 = BigInteger.valueOf(listaCoppie.get(x).getValue0());
					coordinataY2 = BigInteger.valueOf(listaCoppie.get(s).getValue0());
				} else {
					coordinataY1 = BigInteger.valueOf(listaCoppie.get(s).getValue0());
					coordinataY2 = BigInteger.valueOf(listaCoppie.get(x).getValue0());
				}

				if (listaCoppie.get(x).getValue1() > listaCoppie.get(s).getValue1()) {
					coordinataX1 = BigInteger.valueOf(listaCoppie.get(x).getValue1());
					coordinataX2 = BigInteger.valueOf(listaCoppie.get(s).getValue1());
				} else {
					coordinataX1 = BigInteger.valueOf(listaCoppie.get(s).getValue1());
					coordinataX2 = BigInteger.valueOf(listaCoppie.get(x).getValue1());
				}

				BigInteger area1 = (coordinataY1.subtract(coordinataY2).add(BigInteger.ONE))
						.multiply(coordinataX1.subtract(coordinataX2).add(BigInteger.ONE));
				// array[s][x]=distance;
				multimapBigInteger.put(area1, listaCoppie.get(x));
				multimapBigInteger.put(area1, listaCoppie.get(s));
				double distance = Math.sqrt(Math.pow(listaCoppie.get(x).getValue0() - listaCoppie.get(s).getValue0(), 2)
						+ Math.pow(listaCoppie.get(x).getValue1() - listaCoppie.get(s).getValue1(), 2));
				// array[s][x]=distance;
				multimap.put(distance, listaCoppie.get(x));
				multimap.put(distance, listaCoppie.get(s));
			});
		});

	}

	private boolean containsTilesV3(int x, int s) {
		int y2 = listaCoppie.get(x).getValue0() > listaCoppie.get(s).getValue0() ? listaCoppie.get(x).getValue0()
				: listaCoppie.get(s).getValue0();
		int y1 = listaCoppie.get(x).getValue0() < listaCoppie.get(s).getValue0() ? listaCoppie.get(x).getValue0()
				: listaCoppie.get(s).getValue0();
		int x2 = listaCoppie.get(x).getValue1() > listaCoppie.get(s).getValue1() ? listaCoppie.get(x).getValue1()
				: listaCoppie.get(s).getValue1();
		int x1 = listaCoppie.get(x).getValue1() < listaCoppie.get(s).getValue1() ? listaCoppie.get(x).getValue1()
				: listaCoppie.get(s).getValue1();
		boolean bool = false;
		for (int i = x1; i <= x2; i++) {
			TreeSet<Integer> setX = (TreeSet<Integer>) mappaListaSimboliX.get(i);
			if (setX != null) {
				int firstY = setX.first();
				int lastY = setX.last();
				if (y1 >= firstY && y2 <= lastY) {
					bool = true;
				} else {
					bool = false;
					break;
				}
			} else {
				bool = false;
				break;
			}
		}
		return bool;
	}

	private Optional<Integer> findMaxLista(List<Integer> lista) {

		return lista.stream().max(Comparator.naturalOrder());

	}

	private Optional<Double> findMax(List<Double> lista) {

		return lista.stream().max(Comparator.naturalOrder());

	}

	private Optional<BigInteger> findMaxBigInteger(List<BigInteger> lista) {

		return lista.stream().max(Comparator.naturalOrder());

	}

	public BigInteger calculateLargerArea2() {

		Optional<Double> maxDistance = this.findMax(multimap.keySet().stream().toList());
		Optional<BigInteger> maxArea = this.findMaxBigInteger(multimapBigInteger.keySet().stream().toList());
		multimap.get(maxDistance.get());
		Pair<Integer, Integer> firstCouple = multimap.get(maxDistance.get()).get(0);
		Pair<Integer, Integer> secondCouple = multimap.get(maxDistance.get()).get(1);
		BigInteger coordinataY1 = null;
		BigInteger coordinataY2 = null;
		BigInteger coordinataX1 = null;
		BigInteger coordinataX2 = null;

		if (firstCouple.getValue0() > secondCouple.getValue0()) {
			coordinataY1 = BigInteger.valueOf(firstCouple.getValue0());
			coordinataY2 = BigInteger.valueOf(secondCouple.getValue0());
		} else {
			coordinataY1 = BigInteger.valueOf(secondCouple.getValue0());
			coordinataY2 = BigInteger.valueOf(firstCouple.getValue0());
		}

		if (firstCouple.getValue1() > secondCouple.getValue1()) {
			coordinataX1 = BigInteger.valueOf(firstCouple.getValue1());
			coordinataX2 = BigInteger.valueOf(secondCouple.getValue1());
		} else {
			coordinataX1 = BigInteger.valueOf(secondCouple.getValue1());
			coordinataX2 = BigInteger.valueOf(firstCouple.getValue1());
		}

		BigInteger area1 = (coordinataY1.subtract(coordinataY2).add(BigInteger.ONE))
				.multiply(coordinataX1.subtract(coordinataX2).add(BigInteger.ONE));
		if (maxArea.isPresent())
			logger.info(maxArea.get());
		return area1;
	}

	public void completeMapX() {
		Integer maxKey = Collections.max(mappaListaSimboliX.keySet());
		Integer minKey = Collections.min(mappaListaSimboliX.keySet());
		IntStream.rangeClosed(minKey, maxKey).forEach(x -> {
			mappaListaSimboliY.keySet().stream().forEach(y -> {
				TreeSet<Integer> setY = (TreeSet<Integer>) mappaListaSimboliY.get(y);
				int firstY = setY.first();
				int lastY = setY.last();
				if (x >= firstY && x <= lastY) {
					TreeSet<Integer> setX = (TreeSet<Integer>) mappaListaSimboliX.get(x);
					if (setX == null) {
						TreeSet<Integer> treeSet = new TreeSet<>();
						treeSet.add(y);
						mappaListaSimboliX.put(x, treeSet);
					} else {
						int firstX = setX.first();
						int lastX = setX.last();
						if (y < firstX || y > lastX) {
							mappaListaSimboliX.get(x).add(y);
						}
					}
				}
			});
		});
		mappaListaSimboliX = calculatedMinMaxTreeSet(mappaListaSimboliX);
	}

}
