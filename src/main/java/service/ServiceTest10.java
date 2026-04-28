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
import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

import model.Distance;
import model.MetricheBottoniPremuti;
import model.MetricheStringheCoincidenti;
import util.Utility;

public class ServiceTest10 {

	private List<String> results = new ArrayList<>();
	private Map<Integer, List<List<Integer>>> mappaBottoni = new HashMap<>();
	private Map<Integer, List<Integer>> mappaVoltage = new HashMap<>();
	private ListMultimap<String, Integer> multimapPercorsiMinimi = ArrayListMultimap.create();

private int best = Integer.MAX_VALUE;
private int[] bestSolution = null;


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
		List<Integer> listaTemp = mappaVoltage.get(results.size() - 1);
		listaTemp=Arrays.stream(voltage.split(",")).map(Integer::parseInt)
				.collect(Collectors.toList());
		mappaVoltage.put(results.size() - 1, listaTemp);
	}

	public void printMap() {
		System.out.println("Stampa Bottoni");
		this.printMap(mappaBottoni);
		System.out.println("Stampa Voltaggio");
		this.printMap(mappaVoltage);
		//System.out.println("Stampa Percorsi Minimi");
		//this.printMap(percorsiMinimi);
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

	public void controllaBottoni() {
		for(int i=0;i<results.size();i++) {			
			List<Integer> partenza = new ArrayList<>(Collections.nCopies(mappaVoltage.get(i).size(), 0));
			List<Integer> obiettivo = mappaVoltage.get(i);
			System.out.print("obiettivo:" + obiettivo);
			Map<List<Integer>, Integer> massimoPressioniBottoni = this.massimoPressioniBottoni(mappaBottoni.get(i), obiettivo);
			Map<List<Integer>, MetricheBottoniPremuti> firstResults = new HashMap<>();
			firstResults.put(partenza, new MetricheBottoniPremuti());
			firstResults = fase1(partenza, obiettivo,
					mappaBottoni.get(i));
			
			Map<List<Integer>, MetricheBottoniPremuti> lastResults = this.fase1PartendoDaFine(obiettivo, mappaBottoni.get(i));
			List<List<Integer>> listaObiettiviRaggiunti= new ArrayList<>();
			
			firstResults=this.proiezioni(firstResults, obiettivo, mappaBottoni.get(i),massimoPressioniBottoni);
			List<Integer> chiave=firstResults.keySet().iterator().next();
			listaObiettiviRaggiunti=firstResults.get(chiave).getBottoniPremuti();
			this.printList(listaObiettiviRaggiunti);
//			while(listaObiettiviRaggiunti.isEmpty()) {
//				firstResults=this.ripremiBottoni(firstResults, mappaBottoni.get(i), true);
//				listaObiettiviRaggiunti = this.confrontoChiavi2(firstResults, lastResults);
//				if(!listaObiettiviRaggiunti.isEmpty()) {
//					continue;
//				}
//				lastResults=this.ripremiBottoni(lastResults, mappaBottoni.get(i), false);
//				listaObiettiviRaggiunti = this.confrontoChiavi2(firstResults, lastResults);
//			}

			multimapPercorsiMinimi.put(obiettivo.toString(), listaObiettiviRaggiunti.size());
			System.out.println("----->"+multimapPercorsiMinimi.get(obiettivo.toString()));
		}
		
	}
	
	public void controllaBottoni2() {
		for(int i=0;i<results.size();i++) {			
			List<Integer> partenza = new ArrayList<>(Collections.nCopies(mappaVoltage.get(i).size(), 0));
			List<Integer> obiettivo = mappaVoltage.get(i);
			int minPressioni=this.task2(obiettivo, mappaBottoni.get(i));
			multimapPercorsiMinimi.put(obiettivo.toString(), minPressioni);
			System.out.println("----->"+multimapPercorsiMinimi.get(obiettivo.toString()));
		}
		
	}
	
	public void controllaBottoni3() {
		for(int i=0;i<results.size();i++) {			
			List<Integer> partenza = new ArrayList<>(Collections.nCopies(mappaVoltage.get(i).size(), 0));
			List<Integer> obiettivo = mappaVoltage.get(i);
			List<List<Integer>> listaBottoni = mappaBottoni.get(i);
			bestSolution = new int[listaBottoni.size()];
			System.out.print("obiettivo:" + obiettivo);
			
			int[] counters = new int[obiettivo.size()];
			int[] used = new int[listaBottoni.size()];
			Map<String, Integer> memo = new HashMap<>();
			int[] order=computeOrder(listaBottoni, obiettivo);
			//dfs(0, counters, used, 0,obiettivo, listaBottoni);
			//dfsOttimizzato(0, counters, used, 0,obiettivo, listaBottoni,memo,order);
			this.idaStar(obiettivo, listaBottoni);
			System.out.println("Soluzione minima:");
			    int total = 0;
			    for (int j = 0; j < mappaBottoni.get(i).size(); j++) {
			        System.out.println("Bottone " + j + ": " + bestSolution[j]);
			        total += bestSolution[j];
			    }
			    multimapPercorsiMinimi.put(obiettivo.toString(), total);
				System.out.println("----->"+multimapPercorsiMinimi.get(obiettivo.toString()));
				bestSolution = null;
				best = Integer.MAX_VALUE;
		}
		
	}

	private Map<List<Integer>, MetricheBottoniPremuti> proiezioni(
			Map<List<Integer>, MetricheBottoniPremuti> firstResults, List<Integer> obiettivo,
			List<List<Integer>> listaBottoni, Map<List<Integer>, Integer> massimoPressioniBottoni) {

		List<Integer> chiave = firstResults.keySet().iterator().next();

		List<Integer> indici = new ArrayList<>();
	indici =
	        IntStream.range(0, obiettivo.size())
	                // se esiste almeno una differenza < 0 → ritorno [-2]
	                .filter(i -> obiettivo.get(i) - chiave.get(i) < 0)
	                .findAny()
	                .isPresent()
	        ? List.of(-2)
	        : IntStream.range(0, obiettivo.size())
	                // prendo tutti gli indici con differenza > 0
	                .filter(i -> obiettivo.get(i) - chiave.get(i) > 0)
	                .boxed()
	                .toList();
		
		if (indici.isEmpty()) {
			return firstResults;
		}
		else if (indici.get(0) == -2) {
			return new HashMap<>();
		}

		List<List<Integer>> bottoniCliccabili = new ArrayList<>();
		listaBottoni.stream().forEach(c -> {
			Set<Integer> intersezione = new HashSet<>(c);
			//intersezione.retainAll(indici);
			if (!intersezione.isEmpty()) {
				bottoniCliccabili.add(c);
			}
		});
		boolean trovato =false;
		for (List<Integer> bottone : bottoniCliccabili) {
			List<Integer> nuovaChiave = new ArrayList<>();
			nuovaChiave.addAll(chiave);
			this.cliccaBottone(nuovaChiave, bottone, true);
			MetricheBottoniPremuti metricheBottoniPremuti = new MetricheBottoniPremuti();
			System.out.println(chiave);
			metricheBottoniPremuti.getBottoniPremuti().addAll(firstResults.get(chiave).getBottoniPremuti());
			metricheBottoniPremuti.getBottoniPremuti().add(bottone);
			Map<List<Integer>, MetricheBottoniPremuti> resultTemp = new HashMap<>();
			resultTemp.put(nuovaChiave, metricheBottoniPremuti);
			resultTemp=this.proiezioni(resultTemp, obiettivo, listaBottoni,massimoPressioniBottoni);
			if(!resultTemp.isEmpty()) {
				firstResults=resultTemp;
			}
			List<Integer> chiaveRisultante = firstResults.keySet().iterator().next();
			if (chiaveRisultante.equals(obiettivo)) {
				trovato=true;
				break;
			}
		}
		if(trovato) {
			return firstResults;
		}
		else {
			return new HashMap<>();
		}
	}

	private List<List<Integer>> confrontoChiavi2(Map<List<Integer>, MetricheBottoniPremuti> firstResults,
			Map<List<Integer>, MetricheBottoniPremuti> lastResults) {
		List<List<Integer>> listaBottoni= new ArrayList<>();
		Set<List<Integer>> chiaviComuni = new HashSet<>(firstResults.keySet());
		chiaviComuni.retainAll(lastResults.keySet());
		if (!chiaviComuni.isEmpty()) {
			List<Integer> chiave = chiaviComuni.iterator().next();
			listaBottoni.addAll(firstResults.get(chiave).getBottoniPremuti());
			listaBottoni.addAll(lastResults.get(chiave).getBottoniPremuti());
			this.printList(listaBottoni);
		}
		return listaBottoni;
	}

	private Map<List<Integer>, MetricheBottoniPremuti> ripremiBottoni(
			Map<List<Integer>, MetricheBottoniPremuti> partenza,
			List<List<Integer>> listaBottoni, boolean first) {
		Set<List<Integer>> chiaviPartenza = partenza.keySet();
		Map<List<Integer>, MetricheBottoniPremuti> tempResults = new HashMap<>();
		for (List<Integer> chiavePartenza : chiaviPartenza) {
			for (List<Integer> bottoneDaPremere : listaBottoni) {
				MetricheBottoniPremuti metricheBottoniPremuti = new MetricheBottoniPremuti();
				metricheBottoniPremuti.getBottoniPremuti().addAll(partenza.get(chiavePartenza).getBottoniPremuti());
				metricheBottoniPremuti.getBottoniPremuti().add(bottoneDaPremere);
				List<Integer> nuovaChiave = new ArrayList<>();
				nuovaChiave.addAll(chiavePartenza);
				this.cliccaBottone(nuovaChiave, bottoneDaPremere, first);
				if (!tempResults.containsKey(nuovaChiave)) {
					tempResults.put(nuovaChiave, metricheBottoniPremuti);
				}

			}

		}
		return tempResults;

	}

	private List<List<Integer>> bottoniCliccabili(List<Integer> chiaveFirstResult, List<Integer> chiaveLastResult,
			List<List<Integer>> listaBottoni, boolean somma) {
		List<List<Integer>> listaBottoniCliccabili =new ArrayList<>();
		for (List<Integer> azione : listaBottoni) {

			List<Integer> temp = new ArrayList<>(chiaveFirstResult);
			boolean valida = true;

			for (int idx : azione) {
				if(somma) {
					temp.set(idx, temp.get(idx) + 1);
				}
				else {
					temp.set(idx, temp.get(idx) - 1);
				}
				
				if(somma) {
					if (temp.get(idx) > chiaveLastResult.get(idx)) {
						valida = false;
						break;
					}
				}
				else {
					if (temp.get(idx) < chiaveLastResult.get(idx)) {
						valida = false;
						break;
					}
				}
			}

			if (valida) {
				listaBottoniCliccabili.add(azione);
			}
		}
		return listaBottoniCliccabili;
	}

	private Map<List<Integer>, MetricheBottoniPremuti> fase1(List<Integer> partenza, List<Integer> obiettivo,
			List<List<Integer>> listaBottoni) {
		List<Integer> bottoneDaPremere= new ArrayList<>();
		Map<List<Integer>, MetricheBottoniPremuti> mappaBottoniPremuti= new HashMap<>();
		MetricheBottoniPremuti metricheBottoniPremuti=new MetricheBottoniPremuti();
		do {
			bottoneDaPremere=this.bottoneMigliore(partenza, obiettivo, listaBottoni);
			if (bottoneDaPremere!=null) {
				metricheBottoniPremuti.getBottoniPremuti().add(bottoneDaPremere);
				this.cliccaBottone(partenza, bottoneDaPremere, true);
			}
		}
		while(bottoneDaPremere!=null && !bottoneDaPremere.isEmpty());
		mappaBottoniPremuti.put(partenza, metricheBottoniPremuti);
		return mappaBottoniPremuti;
	}
	
	private Map<List<Integer>, MetricheBottoniPremuti> fase1PartendoDaFine(List<Integer> partenza,
			List<List<Integer>> listaBottoni) {
		Map<List<Integer>, MetricheBottoniPremuti> mappaBottoniPremuti= new HashMap<>();		
		for(int  i=0;i<listaBottoni.size();i++) {
			List<Integer> partenzaTemp = new ArrayList<>();
			partenzaTemp.addAll(partenza);
			MetricheBottoniPremuti metricheBottoniPremuti=new MetricheBottoniPremuti();
			metricheBottoniPremuti.getBottoniPremuti().add(listaBottoni.get(i));
			this.cliccaBottone(partenzaTemp, listaBottoni.get(i), false);
			mappaBottoniPremuti.put(partenzaTemp, metricheBottoniPremuti);
		}
		return mappaBottoniPremuti;
	}

	private List<Integer> bottoneMigliore(List<Integer> partenza, List<Integer> obiettivo,
			List<List<Integer>> listaBottoni) {

		List<List<Integer>> listaBottoniTemp = new ArrayList<>();
		listaBottoniTemp.addAll(listaBottoni);
		List<List<Integer>> bottoniCliccabili = new ArrayList<>();
		do {
			int maxSize = listaBottoniTemp.stream().mapToInt(List::size).max().orElse(0);
	
			List<List<Integer>> listeMax = listaBottoniTemp.stream().filter(l -> l.size() == maxSize).toList();
	
			int max =
					IntStream.range(0, obiettivo.size())
					.filter(i -> listaBottoniTemp.stream().anyMatch(a -> a.contains(i)))
	             // rispetto il vincolo sulla soglia
	             .filter(i -> partenza.get(i) < (obiettivo.get(i) + 1) / 2)
	             // considero solo l'obiettivo
	             .map(i -> obiettivo.get(i))
	             // prendo il massimo
	             .max()
	             .orElse(0);
	
			List<Integer> indici = IntStream.range(0, obiettivo.size()).filter(i -> obiettivo.get(i) == max).boxed()
					.toList();
	
			listeMax.stream().forEach(c -> {
				Set<Integer> intersezione = new HashSet<>(c);
				intersezione.retainAll(indici);
				if (!intersezione.isEmpty()) {
					bottoniCliccabili.add(c);
				}
			});
			if(bottoniCliccabili.isEmpty()) {
				listaBottoniTemp.removeAll(listeMax);
			}
		}	
		while(bottoniCliccabili.isEmpty() && !listaBottoniTemp.isEmpty());

		List<Integer> miglioreAzione = null;
		int migliorScore = -1;

		for (List<Integer> azione : bottoniCliccabili) {

			List<Integer> temp = new ArrayList<>(partenza);
			boolean valida = true;
			int score = 0;

			for (int idx : azione) {
				temp.set(idx, temp.get(idx) + 1);

				int soglia = (obiettivo.get(idx) + 1) / 2;
				if (temp.get(idx) > soglia) {
					valida = false;
					break;
				}

				score += obiettivo.get(idx);
			}

			if (valida && score > migliorScore) {
				migliorScore = score;
				miglioreAzione = azione;
			}
		}
		if(miglioreAzione==null && !bottoniCliccabili.isEmpty()) {
			List<List<Integer>> bottoniCliccabiliTemp =listaBottoni.stream().filter(c->!bottoniCliccabili.contains(c)).toList();
			if(!bottoniCliccabiliTemp.isEmpty())
				miglioreAzione=this.bottoneMigliore(partenza, obiettivo, bottoniCliccabiliTemp);
		}

		return miglioreAzione;
	}
	
	private void cliccaBottone(List<Integer> partenza, List<Integer> bottoneDaPremere, boolean somma) {

		if(somma) {
			for (int idx : bottoneDaPremere) {
				partenza.set(idx, partenza.get(idx) + 1);
			}
		}
		else {
			for (int idx : bottoneDaPremere) {
				partenza.set(idx, partenza.get(idx) - 1);	
			}
		}

	}

	public Map<List<Integer>, Integer> massimoPressioniBottoni(List<List<Integer>> bottoni, List<Integer> obiettivo) {
		Map<List<Integer>, Integer> result = new HashMap<>();
		for (List<Integer> bottone : bottoni) {
			int max = Integer.MAX_VALUE;
			for (int indice : bottone) {
				max = Math.min(max, obiettivo.get(indice));
			}
			result.put(bottone, max);
		}

		return result;

	}
	
	/**
	 * Nel tuo codice stai facendo questo:

	scegli quante volte premere ogni bottone
	sommi gli effetti sui contatori
	verifichi se arrivi esattamente all’obiettivo
	tra tutte le combinazioni valide, scegli quella con meno pressioni
	
	👉 ILP fa la stessa cosa, ma:
	
	senza esplorare rami
	senza DFS
	senza euristiche
	senza memo
	 * @param obiettivo
	 * @param listaBottoni
	 * @return
	 * https://or-tools.github.io/docs/java/
	 * https://medium.com/@pauljohncronin/linear-programming-with-or-tools-e541a272c4a0
	 * https://www.youtube.com/watch?v=ce9CKjbmWN0
	 */
	public int task2(List<Integer> obiettivo, List<List<Integer>> listaBottoni) {
		// Carica le librerie native di OR-Tools (obbligatorio una sola volta)
        Loader.loadNativeLibraries();


     // Crea un solver di programmazione lineare intera.
     // "CBC" è un solver open-source per Integer Linear Programming.

        MPSolver solver =
            MPSolver.createSolver("CBC"); // risolutore intero


     // Array delle variabili decisionali:
     // x[i] = numero di volte che premi il bottone i

        MPVariable[] x = new MPVariable[listaBottoni.size()];

        // Variabili: x[i] >= 0, intero
        for (int i = 0; i < x.length; i++) {

        	// Crea una variabile intera:
        	    // - minimo 0
        	    // - massimo infinito
        	    // - nome simbolico "x_i"

            x[i] = solver.makeIntVar(0, Double.POSITIVE_INFINITY, "x" + i);
        }

        // Vincoli sui 9 contatori
     // Per ogni contatore j imponiamo un vincolo di uguaglianza
        for (int j = 0; j < obiettivo.size(); j++) {

        	// Crea un vincolo:
        	    // lato sinistro = target[j]
        	    // lato destro   = target[j]
        	    // quindi: = target[j]
        	

            MPConstraint c = solver.makeConstraint(obiettivo.get(j), obiettivo.get(j));


         // Per ogni bottone i controlliamo se
             // quel bottone incrementa il contatore j

            for (int i = 0; i < listaBottoni.size(); i++) {
                for (int idx : listaBottoni.get(i)) {
                	// Se il bottone i agisce sul contatore j
                    if (idx == j) {
                    	// Aggiungiamo x[i] * 1 al lato sinistro del vincolo
                        c.setCoefficient(x[i], 1);
                        break;
                    }
                }
            }
        }

        // Funzione obiettivo: minimizzare il numero di pressioni
        MPObjective obj = solver.objective();
        for (MPVariable v : x) {
            obj.setCoefficient(v, 1);
        }
     // Imposta il tipo di problema: MINIMIZZAZIONE
        obj.setMinimization();

        // Risolvi
        MPSolver.ResultStatus result = solver.solve();
        int total = 0;
        if (result == MPSolver.ResultStatus.OPTIMAL) {
            System.out.println("Soluzione ottima:");            
            for (int i = 0; i < x.length; i++) {
                int val = (int) x[i].solutionValue();
                total += val;
                System.out.println("Bottone " + i + ": " + val);
            }
            System.out.println("Totale pressioni = " + total);
        } else {
            System.out.println("Nessuna soluzione trovata");
        }
        return total;
    }
	

	private boolean exceeds(int[] cur, List<Integer> obiettivo) {
    for (int i = 0; i < obiettivo.size(); i++) {
        if (cur[i] > obiettivo.get(i)) return true;
    }
    return false;
}
	
	private boolean isTarget(int[] cur, List<Integer> obiettivo) {
		for (int i = 0; i < obiettivo.size(); i++) {
	        if (cur[i] != obiettivo.get(i)) return false;
	    }
	    return true;
	}
	
	private int lowerBound(int[] cur, List<Integer> obiettivo) {
	    int lb = 0;
	    for (int i = 0; i < obiettivo.size(); i++) {
	        lb = Math.max(lb, obiettivo.get(i) - cur[i]);
	    }
	    return lb;
	}
	
	private void dfs(
	        int button,
	        int[] curCounters,
	        int[] used,
	        int pressesSoFar, List<Integer> obiettivo, List<List<Integer>> listaBottoni
	) {
	    // Bound: già peggio del migliore
	    if (pressesSoFar >= best) return;

	    // Se superiamo target → taglio
	    if (exceeds(curCounters, obiettivo)) return;

	    // Se siamo al target → aggiorna best
	    if (isTarget(curCounters,obiettivo)) {
	        best = pressesSoFar;
	        System.arraycopy(used, 0, bestSolution, 0, listaBottoni.size());
	        return;
	    }

	    // Finito bottoni
	    if (button == listaBottoni.size()) return;

	    // Stima massima utile per questo bottone
	    int maxTimes = Integer.MAX_VALUE;
	    for (int idx : listaBottoni.get(button)) {
	        maxTimes = Math.min(
	            maxTimes,
	            obiettivo.get(idx) - curCounters[idx]
	        );
	    }

	    // Prova da max a 0 (più potatura)
	    for (int k = maxTimes; k >= 0; k--) {

	        // Applica k volte bottone
	        for (int idx : listaBottoni.get(button)) {
	            curCounters[idx] += k;
	        }
	        used[button] = k;

	        dfs(
	            button + 1,
	            curCounters,
	            used,
	            pressesSoFar + k,
	            obiettivo,
	            listaBottoni
	        );

	        // rollback
	        for (int idx : listaBottoni.get(button)) {
	            curCounters[idx] -= k;
	        }
	        used[button] = 0;
	    }
	}
	


private int buttonWeight(List<Integer> list, List<Integer> obiettivo) {
    int w = 0;
    for (int idx : list) {
        w += 100 - obiettivo.get(idx); // contatori piccoli = più restrittivi
    }
    return w;
}

private int[] computeOrder(List<List<Integer>> listaBottoni, List<Integer> obiettivo) {
	return IntStream.range(0, listaBottoni.size())
            .boxed()
            .sorted((a, b) ->
                Integer.compare(
                    buttonWeight(listaBottoni.get(b),obiettivo),
                    buttonWeight(listaBottoni.get(a),obiettivo)
                )
            )
            .mapToInt(Integer::intValue)
            .toArray();
}


	
	private void dfsOttimizzato(
	        int button,
	        int[] curCounters,
	        int[] used,
	        int pressesSoFar, List<Integer> obiettivo, List<List<Integer>> listaBottoni,
	        Map<String, Integer> memo, int[] order
	) {
	    // Bound: già peggio del migliore
	    if (pressesSoFar >= best) return;
	    

	 // Lower bound
	     if (pressesSoFar + lowerBound(curCounters, obiettivo) >= best) return;


	    // Se superiamo target → taglio
	    if (exceeds(curCounters, obiettivo)) return;

	    // Se siamo al target → aggiorna best
	    if (isTarget(curCounters,obiettivo)) {
	        best = pressesSoFar;
	        System.arraycopy(used, 0, bestSolution, 0, listaBottoni.size());
	        return;
	    }

	    // Finito bottoni
	    if (button == listaBottoni.size()) return;
	    

	 // Memorizzazione
	     String key = button + ":" + Arrays.toString(curCounters);
	     Integer seen = memo.get(key);
	     if (seen != null && seen <= pressesSoFar) return;
	     memo.put(key, pressesSoFar);

	     int b = order[button];

	     // Calcolo massimo utilizzo bottone
	     int maxTimes = Integer.MAX_VALUE;
	     for (int idx : listaBottoni.get(b))
	         maxTimes = Math.min(maxTimes, obiettivo.get(idx) - curCounters[idx]);

	     // ulteriore bound
	     maxTimes = Math.min(maxTimes, best - pressesSoFar);
	     maxTimes = Math.max(0, maxTimes);



	  // Esplora da grande a piccolo
	      for (int k = maxTimes; k >= 0; k--) {

	          // applica
	          for (int idx : listaBottoni.get(b))
	        	  curCounters[idx] += k;
	          used[b] = k;

	          dfsOttimizzato(button + 1, curCounters, used, pressesSoFar + k, obiettivo,listaBottoni, memo, order);

	          // rollback
	          for (int idx : listaBottoni.get(b))
	        	  curCounters[idx] -= k;
	          used[b] = 0;
	      }

	}
	

public void idaStar(
        List<Integer> obiettivo,
        List<List<Integer>> listaBottoni
) {
    int[] curCounters = new int[obiettivo.size()];
    int[] used = new int[listaBottoni.size()];
    bestSolution = new int[listaBottoni.size()];

    int[] order = computeOrder(listaBottoni, obiettivo);

    int bound = lowerBound(curCounters, obiettivo);

    while (true) {
        Map<String, Integer> memo = new HashMap<>();

        int t = dfsIda(
            0,
            curCounters,
            used,
            0,
            bound,
            obiettivo,
            listaBottoni,
            memo,
            order
        );

        if (t == -1) {
            return; // soluzione ottima trovata
        }

        if (t == Integer.MAX_VALUE) {
            throw new RuntimeException("Nessuna soluzione trovata");
        }

        bound = t; // aumenta soglia IDA*
    }
}



private int dfsIda(
        int button,
        int[] curCounters,
        int[] used,
        int pressesSoFar,
        int bound,
        List<Integer> obiettivo,
        List<List<Integer>> listaBottoni,
        Map<String, Integer> memo,
        int[] order
) {
    int f = pressesSoFar + lowerBound(curCounters, obiettivo);

    if (f > bound) return f;

    if (exceeds(curCounters, obiettivo)) return Integer.MAX_VALUE;

    if (isTarget(curCounters, obiettivo)) {
        best = pressesSoFar;
        System.arraycopy(used, 0, bestSolution, 0, listaBottoni.size());
        return -1; // FOUND
    }

    if (button == listaBottoni.size()) return Integer.MAX_VALUE;

    String key = button + ":" + Arrays.toString(curCounters);
    Integer seen = memo.get(key);
    if (seen != null && seen <= pressesSoFar) return Integer.MAX_VALUE;
    memo.put(key, pressesSoFar);

    int b = order[button];

    int maxTimes = Integer.MAX_VALUE;
    for (int idx : listaBottoni.get(b))
        maxTimes = Math.min(maxTimes, obiettivo.get(idx) - curCounters[idx]);

    maxTimes = Math.min(maxTimes, bound - pressesSoFar);
    maxTimes = Math.max(0, maxTimes);

    int minNextBound = Integer.MAX_VALUE;

    for (int k = maxTimes; k >= 0; k--) {

        for (int idx : listaBottoni.get(b))
            curCounters[idx] += k;
        used[b] = k;

        int t = dfsIda(
            button + 1,
            curCounters,
            used,
            pressesSoFar + k,
            bound,
            obiettivo,
            listaBottoni,
            memo,
            order
        );

        if (t == -1) return -1;
        minNextBound = Math.min(minNextBound, t);

        for (int idx : listaBottoni.get(b))
            curCounters[idx] -= k;
        used[b] = 0;
    }

    return minNextBound;
}




}
