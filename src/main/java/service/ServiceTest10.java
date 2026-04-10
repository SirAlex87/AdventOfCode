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

import org.apache.commons.lang3.StringUtils;
import org.javatuples.Pair;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import model.Distance;
import model.MetricheStringheCoincidenti;
import util.Utility;

public class ServiceTest10 {
	
	private List<String> results= new ArrayList<>();
	private Map<Integer,List<List<Integer>>> mappaBottoni=new HashMap<>();
	private Map<Integer,List<List<Integer>>> mappaVoltage=new HashMap<>();

	public void populateList(String s) {
		results.add(s.substring(1, s.indexOf("]")));
		mappaBottoni.put(results.size()-1, new ArrayList<>());
		mappaVoltage.put(results.size()-1, new ArrayList<>());

		String temp = s;
		while (temp.indexOf("(") > -1) {
			temp = temp.substring(temp.indexOf("("));
			String tempBottoni = temp.substring(temp.indexOf("(") + 1, temp.indexOf(")"));
			temp = temp.substring(1);
			List<List<Integer>> listaTemp= mappaBottoni.get(results.size()-1);
			listaTemp.add(Arrays.stream(tempBottoni.split(",")).map(Integer::parseInt)
					.collect(Collectors.toCollection(ArrayList::new)));
			mappaBottoni.put(results.size()-1, listaTemp);
		}
		
		String voltage=s.substring(s.indexOf("{")+1, s.indexOf("}"));
		List<List<Integer>> listaTemp= mappaVoltage.get(results.size()-1);
		listaTemp.add(Arrays.stream(voltage.split(",")).map(Integer::parseInt)
				.collect(Collectors.toCollection(ArrayList::new)));
		mappaVoltage.put(results.size()-1, listaTemp);
	}

	public void controllaStringa() {
		String partenza=StringUtils.repeat(".", results.get(0).length());
		String result=results.get(0);
		List<List<Integer>> listaBottoni=mappaBottoni.get(0);
		for(int i=0;i<listaBottoni.size();i++) {
			Map<String,MetricheStringheCoincidenti> tempResults=new HashMap<>();
			for(int j=0;j<listaBottoni.get(i).size();j++) {
				String stringaCambiata=cliccaBottone(partenza, listaBottoni.get(i));
				MetricheStringheCoincidenti metricheStringheCoincidenti = this.stringheCoincidenti(partenza, stringaCambiata);
				tempResults.put(stringaCambiata, metricheStringheCoincidenti);
			}
		}
		
	}
	
	private String cliccaBottone(String partenza, List<Integer> list) {

		char[] chars = partenza.toCharArray();

		for (Integer pos : list) {
			if (pos >= 0 && pos < chars.length) {
				if (chars[pos] == '.') {
					chars[pos] = '#';
				} else if (chars[pos] == '#') {
					chars[pos] = '.';
				}
			}
		}

		return new String(chars);

	}

	private MetricheStringheCoincidenti stringheCoincidenti(String partenza, String stringaCambiata) {

		int coincidenti = 0;
		List<Integer> posCoincidenti = new ArrayList<>();
		List<Integer> posNonCoincidenti = new ArrayList<>();

		int len = Math.min(partenza.length(), stringaCambiata.length());

		for (int i = 0; i < len; i++) {
			if (partenza.charAt(i) == stringaCambiata.charAt(i)) {
				coincidenti++;
				posCoincidenti.add(i);
			} else {
				posNonCoincidenti.add(i);
			}
		}

		System.out.println("Coincidono: " + coincidenti);
		System.out.println("Posizioni coincidenti: " + posCoincidenti);
		System.out.println("Posizioni non coincidenti: " + posNonCoincidenti);
		
		MetricheStringheCoincidenti metricheStringheCoincidenti = new MetricheStringheCoincidenti(coincidenti, posCoincidenti, posNonCoincidenti);
		return metricheStringheCoincidenti;

	}
	
}
