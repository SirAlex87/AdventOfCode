package service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.javatuples.Pair;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import model.Distance;

public class ServiceTest9 {
	private List<Integer> listaY = new ArrayList<>();
	private List<Integer> listaX = new ArrayList<>();
	private List<List<Integer>> listaFinale=new ArrayList<>();
	private ListMultimap<Double, Pair<Integer, Integer>> multimap = ArrayListMultimap.create();
	private List<Pair<Integer, Integer>> listaCoppie = new ArrayList<>();
	private double [][] array;

	public void populateList(String chiave) {
		List<Integer> numeri = Arrays.stream(chiave.split(",")) // -1 conserva eventuali vuoti
                .map(String::trim)
                .filter(s -> !s.isEmpty())          // scarta elementi vuoti (es. "43,,44")
                .map(Integer::parseInt)             // può lanciare NumberFormatException se non numerico
                .collect(Collectors.toList());

		listaY.add(numeri.get(0));
		listaX.add(numeri.get(1));
		List<Integer> listaTemp= new ArrayList<>();
		listaTemp.add(numeri.get(0));
		listaTemp.add(numeri.get(1));
		listaFinale.add(listaTemp);
		Pair<Integer, Integer> pair = new Pair<Integer, Integer>(numeri.get(0), numeri.get(1));
		listaCoppie.add(pair);
	}

	public void calculateDistance2() {
		array=new double[listaCoppie.size()][listaCoppie.size()];
		IntStream.range(0, listaCoppie.size()).forEach(s->{
			IntStream.range(0, listaCoppie.size()).filter(x->x!=s).forEach(x->{
				double distance=Math.sqrt(Math.pow(listaCoppie.get(x).getValue0() -listaCoppie.get(s).getValue0(), 2) +
                         Math.pow(listaCoppie.get(x).getValue1() - listaCoppie.get(s).getValue1(), 2));
				array[s][x]=distance;
				multimap.put(distance,listaCoppie.get(x));
				multimap.put(distance,listaCoppie.get(s));
			});
		});
		
	}	

	private Optional<Integer> findMin(List<Integer> lista) {

		return lista.stream().min(Comparator.naturalOrder());

	}
	
	private Optional<Double> findMax(List<Double> lista) {

		return lista.stream().max(Comparator.naturalOrder());

	}
	
	public BigInteger calculateLargerArea2() {
			
		Optional<Double> maxDistance=this.findMax(multimap.keySet().stream().toList());
		multimap.get(maxDistance.get());
		Pair<Integer, Integer> firstCouple= multimap.get(maxDistance.get()).get(0);
		Pair<Integer, Integer> secondCouple= multimap.get(maxDistance.get()).get(1);
		BigInteger coordinataY1=null;
		BigInteger coordinataY2=null;
		BigInteger coordinataX1=null;
		BigInteger coordinataX2=null;
		
		if(firstCouple.getValue0()>secondCouple.getValue0()) {
			coordinataY1=BigInteger.valueOf(firstCouple.getValue0());
			coordinataY2=BigInteger.valueOf(secondCouple.getValue0());
		}
		else {
			coordinataY1=BigInteger.valueOf(secondCouple.getValue0());
			coordinataY2=BigInteger.valueOf(firstCouple.getValue0());
		}
		
		if(firstCouple.getValue1()>secondCouple.getValue1()) {
			coordinataX1=BigInteger.valueOf(firstCouple.getValue1());
			coordinataX2=BigInteger.valueOf(secondCouple.getValue1());
		}
		else {
			coordinataX1=BigInteger.valueOf(secondCouple.getValue1());
			coordinataX2=BigInteger.valueOf(firstCouple.getValue1());
		}
		
		BigInteger area1=(coordinataY1.subtract(coordinataY2).add(BigInteger.ONE)).multiply(coordinataX1.subtract(coordinataX2).add(BigInteger.ONE));

		return area1;
		}

}
