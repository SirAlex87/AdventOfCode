package service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
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

	private List<String> results = new ArrayList<>();
	private Map<Integer, List<List<Integer>>> mappaBottoni = new HashMap<>();
	private Map<Integer, List<List<Integer>>> mappaVoltage = new HashMap<>();
	private ListMultimap<String, Integer> multimapPercorsiMinimi = ArrayListMultimap.create();

	public void populateList(String s) {
		results.add(s.substring(1, s.indexOf("]")));
		mappaBottoni.put(results.size() - 1, new ArrayList<>());
		mappaVoltage.put(results.size() - 1, new ArrayList<>());

		String temp = s;
		while (temp.indexOf("(") > -1) {
			temp = temp.substring(temp.indexOf("("));
			String tempBottoni = temp.substring(temp.indexOf("(") + 1, temp.indexOf(")"));
			temp = temp.substring(1);
			List<List<Integer>> listaTemp = mappaBottoni.get(results.size() - 1);
			listaTemp.add(Arrays.stream(tempBottoni.split(",")).map(Integer::parseInt)
					.collect(Collectors.toCollection(ArrayList::new)));
			mappaBottoni.put(results.size() - 1, listaTemp);
		}

		String voltage = s.substring(s.indexOf("{") + 1, s.indexOf("}"));
		List<List<Integer>> listaTemp = mappaVoltage.get(results.size() - 1);
		listaTemp.add(Arrays.stream(voltage.split(",")).map(Integer::parseInt)
				.collect(Collectors.toCollection(ArrayList::new)));
		mappaVoltage.put(results.size() - 1, listaTemp);
	}

	public void printMap() {
//		System.out.println("Stampa Bottoni");
//		this.printMap(mappaBottoni);
//		System.out.println("Stampa Voltaggio");
//		this.printMap(mappaVoltage);
//		System.out.println("Stampa Percorsi Minimi");
//		this.printMap(percorsiMinimi);
	}

	public void controllaStringa() {		
		for(int i=0;i<results.size();i++) {
			String partenza = StringUtils.repeat(".", results.get(i).length());
			String obiettivo = results.get(i);
			System.out.print("obiettivo:" + obiettivo);
			Map<String, MetricheStringheCoincidenti> firstResults = premiPrimiBottoni(partenza, obiettivo, i,
					mappaBottoni.get(i));
			Optional<List<List<Integer>>> listaObiettiviRaggiunti = this.raggiuntoObiettivo(firstResults);
			if (listaObiettiviRaggiunti.isPresent()) {
				multimapPercorsiMinimi.put(obiettivo, listaObiettiviRaggiunti.get().size());
				System.out.println("----->"+multimapPercorsiMinimi.get(obiettivo));
				continue;
			}
			Map<String, MetricheStringheCoincidenti> lastResults = premiPrimiBottoni(obiettivo, partenza, i,
					mappaBottoni.get(i));
			List<List<Integer>> listaObiettiviRaggiunti2 = this.confrontoChiavi(firstResults, lastResults);
			if (!listaObiettiviRaggiunti2.isEmpty()) {
				multimapPercorsiMinimi.put(obiettivo, listaObiettiviRaggiunti2.size());
				System.out.println("----->"+multimapPercorsiMinimi.get(obiettivo));
				continue;
			}
			while(listaObiettiviRaggiunti2.isEmpty()) {
				firstResults=this.ripremiBottoni2(i, obiettivo, firstResults);
				listaObiettiviRaggiunti2 = this.confrontoChiavi(firstResults, lastResults);
				if(!listaObiettiviRaggiunti2.isEmpty()) {
					continue;
				}
				lastResults=this.ripremiBottoni2(i, partenza, lastResults);
				listaObiettiviRaggiunti2 = this.confrontoChiavi(firstResults, lastResults);
			}
			
//			List<Integer> listaMinimeMosse = new ArrayList<>();
//			for (String tempStringa : firstResults.keySet()) {
//				this.ripremiBottoni(i, obiettivo, firstResults, listaMinimeMosse, tempStringa,1);
//			}
			multimapPercorsiMinimi.put(obiettivo, listaObiettiviRaggiunti2.size());
			System.out.println("----->"+multimapPercorsiMinimi.get(obiettivo));
		}

	}

	private List<List<Integer>> confrontoChiavi(Map<String, MetricheStringheCoincidenti> firstResults,
			Map<String, MetricheStringheCoincidenti> lastResults) {
			
		   List<List<Integer>> listaBottoni= new ArrayList<>();
			Set<String> chiaviComuni = new HashSet<>(firstResults.keySet());
			chiaviComuni.retainAll(lastResults.keySet());
			if (!chiaviComuni.isEmpty()) {
				String chiave = chiaviComuni.iterator().next();
				listaBottoni.addAll(firstResults.get(chiave).getBottoniPremuti());
				listaBottoni.addAll(lastResults.get(chiave).getBottoniPremuti());
				//this.printList(listaBottoni);
			}
			return listaBottoni;

	}

	private Map<String, MetricheStringheCoincidenti> premiPrimiBottoni(String partenza, String obiettivo,
			int numeroStringa, List<List<Integer>> listaBottoni) {
		List<List<Integer>> listaBottoniCliccabili = new ArrayList<>();
		if(!partenza.contains("#")) {
			listaBottoniCliccabili = this.bottoniCliccabili(listaBottoni, obiettivo, partenza);
		} else {
			listaBottoniCliccabili.addAll(listaBottoni);
		}
		//this.printList(listaBottoniCliccabili);
		Map<String, MetricheStringheCoincidenti> tempResults = new HashMap<>();
		for (int i = 0; i < listaBottoniCliccabili.size(); i++) {
			String stringaCambiata = cliccaBottone(partenza, listaBottoniCliccabili.get(i));
			MetricheStringheCoincidenti metricheStringheCoincidenti = this.stringheCoincidenti(obiettivo,
					stringaCambiata);
			metricheStringheCoincidenti.getBottoniPremuti().add(listaBottoniCliccabili.get(i));
			tempResults.put(stringaCambiata, metricheStringheCoincidenti);
		}
		//this.printMap(tempResults);
		return tempResults;
	}

	private List<Integer> ripremiBottoni(int i, String obiettivo, Map<String, MetricheStringheCoincidenti> firstResults,
			List<Integer> listaMinimeMosse, String tempStringa, int livello) {
		Optional<List<List<Integer>>> listaObiettiviRaggiunti;
//		System.out.println("Analizza INIZIO");
//		System.out.println("livello:"+livello);
//		System.out.println("stringa da analizzare:"+tempStringa);
//		System.out.println(firstResults.get(tempStringa));
//		System.out.println("Analizza FINE");
		Map<String, MetricheStringheCoincidenti> tempResults = this.premiBottoni(tempStringa, obiettivo, i,
				mappaBottoni.get(i), firstResults.get(tempStringa));
		Map<String, MetricheStringheCoincidenti> result = this.findKeysWithMinValue(tempResults);
		listaObiettiviRaggiunti = this.raggiuntoObiettivo(result);
		if (listaObiettiviRaggiunti.isPresent()) {
			listaMinimeMosse.add(listaObiettiviRaggiunti.get().size());
			return listaMinimeMosse;
		} else {
			if(listaMinimeMosse.size()>=1) {
				return listaMinimeMosse;
			}
			if(livello<=1000) {
				for (String tempS : tempResults.keySet()) {
					livello++;
					this.ripremiBottoni(i, obiettivo, tempResults, listaMinimeMosse, tempS, livello);
				}
			}
		}
		return listaMinimeMosse;
	}
	
	private Map<String, MetricheStringheCoincidenti> ripremiBottoni2(int i, String obiettivo, Map<String, MetricheStringheCoincidenti> firstResults) {

		Map<String, MetricheStringheCoincidenti> tempResults = new HashMap<>();
		for (String tempStringa : firstResults.keySet()) {
			this.premiBottoni2(tempStringa, obiettivo, mappaBottoni.get(i), firstResults.get(tempStringa), tempResults);
		}
		return tempResults;
	}

	private Map<String, MetricheStringheCoincidenti> premiBottoni2(String partenza, String obiettivo,
			List<List<Integer>> listaBottoni, MetricheStringheCoincidenti metricheStringheCoincidentiPrecedenti, Map<String, MetricheStringheCoincidenti> tempResults) {
		List<List<Integer>> listaBottoniCliccabili = new ArrayList<>();
		listaBottoniCliccabili.addAll(listaBottoni);
		listaBottoniCliccabili.remove(metricheStringheCoincidentiPrecedenti.getBottoniPremuti().getLast());
		for (int i = 0; i < listaBottoniCliccabili.size(); i++) {
			String stringaCambiata = cliccaBottone(partenza, listaBottoniCliccabili.get(i));
			MetricheStringheCoincidenti metricheStringheCoincidenti = this.stringheCoincidenti(obiettivo,
					stringaCambiata);
			metricheStringheCoincidenti.getBottoniPremuti().addAll(metricheStringheCoincidentiPrecedenti.getBottoniPremuti());
			metricheStringheCoincidenti.getBottoniPremuti().add(listaBottoniCliccabili.get(i));
			tempResults.computeIfAbsent(stringaCambiata, k -> metricheStringheCoincidenti);			
		}
		//this.printMap(tempResults);
		return tempResults;
	}

	private Map<String, MetricheStringheCoincidenti> premiBottoni(String partenza, String obiettivo, int numeroStringa,
			List<List<Integer>> listaBottoni, MetricheStringheCoincidenti metricheStringheCoincidentiPrecedenti) {

		List<List<Integer>> listaBottoniCliccabili = this.bottoniCliccabili(listaBottoni, obiettivo, partenza);
		listaBottoniCliccabili.remove(metricheStringheCoincidentiPrecedenti.getBottoniPremuti().getLast());
		//this.printList(listaBottoniCliccabili);
		Map<String, MetricheStringheCoincidenti> tempResults = new HashMap<>();
		for (int i = 0; i < listaBottoniCliccabili.size(); i++) {
			String stringaCambiata = cliccaBottone(partenza, listaBottoniCliccabili.get(i));
			MetricheStringheCoincidenti metricheStringheCoincidenti = this.stringheCoincidenti(obiettivo,
					stringaCambiata);
			metricheStringheCoincidenti.getBottoniPremuti().addAll(metricheStringheCoincidentiPrecedenti.getBottoniPremuti());
			metricheStringheCoincidenti.getBottoniPremuti().add(listaBottoniCliccabili.get(i));
//			if (metricheStringheCoincidentiPrecedenti.getPosNonCoincidenti().size() >= metricheStringheCoincidenti
//					.getPosNonCoincidenti().size()) {
			if(!tempResults.containsKey(stringaCambiata)) {
				tempResults.put(stringaCambiata, metricheStringheCoincidenti);
			}
		}
		//this.printMap(tempResults);
		return tempResults;
	}

	private Optional<List<List<Integer>>> raggiuntoObiettivo(Map<String, MetricheStringheCoincidenti> results) {
		return results.values().stream().filter(e -> e.getPosNonCoincidenti().size() == 0)
				.map(MetricheStringheCoincidenti::getBottoniPremuti).findFirst();
	}

	private Optional<Integer> findMinLista(List<Integer> lista) {

		return lista.stream().min(Comparator.naturalOrder());

	}

	private Map<String, MetricheStringheCoincidenti> findKeysWithMinValue(
			Map<String, MetricheStringheCoincidenti> map) {

		Optional<Integer> min = map.values().stream().map(c -> c.getBottoniPremuti().size())
				.min(Comparator.naturalOrder());

		return map.entrySet().stream().filter(e -> e.getValue().getBottoniPremuti().size()==min.get())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

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

	private List<List<Integer>> bottoniCliccabili(List<List<Integer>> listaBottoni, String result, String temp) {
		List<List<Integer>> bottoniCliccabili = new ArrayList<>();
		char[] charsResult = result.toCharArray();
		char[] charsTemp = temp.toCharArray();
		MetricheStringheCoincidenti metricheStringheCoincidenti = this.stringheCoincidenti(result, temp);
		for (int i = 0; i < listaBottoni.size(); i++) {
			Set<Integer> intersezione = new HashSet<>(listaBottoni.get(i));
			intersezione.retainAll(metricheStringheCoincidenti.getPosNonCoincidenti());
			if (!intersezione.isEmpty()) {
				bottoniCliccabili.add(listaBottoni.get(i));
			}
		}

		return bottoniCliccabili;
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

		return new MetricheStringheCoincidenti(coincidenti, posCoincidenti, posNonCoincidenti);

	}

	public void printMap(Map<?, ?> map) {
		map.forEach((key, value) -> System.out.println(key + " = " + value));
	}

	public void printList(List<?> lista) {
		lista.forEach(v -> {
			System.out.println(v);
		});
	}

	public BigInteger sommaMinimi() {
		multimapPercorsiMinimi.values().stream().forEach(c->{
			System.out.println(c+",");
		});
		// TODO Auto-generated method stub
		return multimapPercorsiMinimi.values().stream()
		        .map(BigInteger::valueOf)
		        .reduce(BigInteger.ZERO, BigInteger::add);
	}

}
