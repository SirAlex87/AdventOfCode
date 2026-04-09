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

import org.javatuples.Pair;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import model.Distance;
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
	
}
