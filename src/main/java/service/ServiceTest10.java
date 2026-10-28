package service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

import model.MetricheStringheCoincidenti;
import util.Utility;

public class ServiceTest10 {

	private static final Logger logger = LogManager.getLogger(ServiceTest10.class);

	private static final String STRING_ARROW = "----->";
	private List<String> results = new ArrayList<>();
	private Map<Integer, List<List<Integer>>> mappaBottoni = new HashMap<>();
	private Map<Integer, List<Integer>> mappaVoltage = new HashMap<>();
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
		List<Integer> listaTemp = Arrays.stream(voltage.split(",")).map(Integer::parseInt).toList();
		mappaVoltage.put(results.size() - 1, listaTemp);
	}

	public void printMap() {
		logger.debug("Stampa Bottoni");
		this.printMap(mappaBottoni);
		logger.debug("Stampa Voltaggio");
		this.printMap(mappaVoltage);
	}

	public void controllaStringa() {
		for (int i = 0; i < results.size(); i++) {
			String partenza = StringUtils.repeat(".", results.get(i).length());
			String obiettivo = results.get(i);
			boolean continua = true;
			logger.debug("obiettivo:{}", obiettivo);
			Map<String, MetricheStringheCoincidenti> firstResults = premiPrimiBottoni(partenza, obiettivo,
					mappaBottoni.get(i));
			Optional<List<List<Integer>>> listaObiettiviRaggiunti = this.raggiuntoObiettivo(firstResults);
			if (listaObiettiviRaggiunti.isPresent()) {
				multimapPercorsiMinimi.put(obiettivo, listaObiettiviRaggiunti.get().size());
				logger.debug("{}{}", STRING_ARROW, multimapPercorsiMinimi.get(obiettivo));
				continua = false;
			}
			if (continua) {
				Map<String, MetricheStringheCoincidenti> lastResults = premiPrimiBottoni(obiettivo, partenza,
						mappaBottoni.get(i));
				List<List<Integer>> listaObiettiviRaggiunti2 = this.confrontoChiavi(firstResults, lastResults);
				if (!listaObiettiviRaggiunti2.isEmpty()) {
					multimapPercorsiMinimi.put(obiettivo, listaObiettiviRaggiunti2.size());
					logger.debug("{}{}", STRING_ARROW, multimapPercorsiMinimi.get(obiettivo));
					continue;
				}
				while (listaObiettiviRaggiunti2.isEmpty()) {
					firstResults = this.ripremiBottoni2(i, obiettivo, firstResults);
					listaObiettiviRaggiunti2 = this.confrontoChiavi(firstResults, lastResults);
					if (!listaObiettiviRaggiunti2.isEmpty()) {
						continue;
					}
					lastResults = this.ripremiBottoni2(i, partenza, lastResults);
					listaObiettiviRaggiunti2 = this.confrontoChiavi(firstResults, lastResults);
				}
				multimapPercorsiMinimi.put(obiettivo, listaObiettiviRaggiunti2.size());
				logger.debug("{}{}", STRING_ARROW, multimapPercorsiMinimi.get(obiettivo));
			}
		}

	}

	private List<List<Integer>> confrontoChiavi(Map<String, MetricheStringheCoincidenti> firstResults,
			Map<String, MetricheStringheCoincidenti> lastResults) {

		List<List<Integer>> listaBottoni = new ArrayList<>();
		Set<String> chiaviComuni = new HashSet<>(firstResults.keySet());
		chiaviComuni.retainAll(lastResults.keySet());
		if (!chiaviComuni.isEmpty()) {
			String chiave = chiaviComuni.iterator().next();
			listaBottoni.addAll(firstResults.get(chiave).getBottoniPremuti());
			listaBottoni.addAll(lastResults.get(chiave).getBottoniPremuti());
			this.printList(listaBottoni);
		}
		return listaBottoni;

	}

	private Map<String, MetricheStringheCoincidenti> premiPrimiBottoni(String fromStart, String toEnd,
			List<List<Integer>> listaBottoni) {
		List<List<Integer>> listaBottoniCliccabili = new ArrayList<>();
		if (!fromStart.contains("#")) {
			listaBottoniCliccabili = this.bottoniCliccabili(listaBottoni, toEnd, fromStart);
		} else {
			listaBottoniCliccabili.addAll(listaBottoni);
		}
		this.printList(listaBottoniCliccabili);
		Map<String, MetricheStringheCoincidenti> tempResults = new HashMap<>();
		for (int i = 0; i < listaBottoniCliccabili.size(); i++) {
			String stringaCambiata = cliccaBottone(fromStart, listaBottoniCliccabili.get(i));
			MetricheStringheCoincidenti metricheStringheCoincidenti = this.stringheCoincidenti(toEnd, stringaCambiata);
			metricheStringheCoincidenti.getBottoniPremuti().add(listaBottoniCliccabili.get(i));
			tempResults.put(stringaCambiata, metricheStringheCoincidenti);
		}
		this.printMap(tempResults);
		return tempResults;
	}

	private Map<String, MetricheStringheCoincidenti> ripremiBottoni2(int i, String obiettivo,
			Map<String, MetricheStringheCoincidenti> firstResults) {

		Map<String, MetricheStringheCoincidenti> tempResults = new HashMap<>();
		for (Entry<String, MetricheStringheCoincidenti> mappaTemp : firstResults.entrySet()) {
			String tempStringa = mappaTemp.getKey();
			this.premiBottoni2(tempStringa, obiettivo, mappaBottoni.get(i), mappaTemp.getValue(), tempResults);
		}
		return tempResults;
	}

	private Map<String, MetricheStringheCoincidenti> premiBottoni2(String partenza, String obiettivo,
			List<List<Integer>> listaBottoni, MetricheStringheCoincidenti metricheStringheCoincidentiPrecedenti,
			Map<String, MetricheStringheCoincidenti> tempResults) {
		List<List<Integer>> listaBottoniCliccabili = new ArrayList<>();
		listaBottoniCliccabili.addAll(listaBottoni);
		listaBottoniCliccabili.remove(metricheStringheCoincidentiPrecedenti.getBottoniPremuti().getLast());
		for (int i = 0; i < listaBottoniCliccabili.size(); i++) {
			String stringaCambiata = cliccaBottone(partenza, listaBottoniCliccabili.get(i));
			MetricheStringheCoincidenti metricheStringheCoincidenti = this.stringheCoincidenti(obiettivo,
					stringaCambiata);
			metricheStringheCoincidenti.getBottoniPremuti()
					.addAll(metricheStringheCoincidentiPrecedenti.getBottoniPremuti());
			metricheStringheCoincidenti.getBottoniPremuti().add(listaBottoniCliccabili.get(i));
			tempResults.computeIfAbsent(stringaCambiata, k -> metricheStringheCoincidenti);
		}
		this.printMap(tempResults);
		return tempResults;
	}

	private Optional<List<List<Integer>>> raggiuntoObiettivo(Map<String, MetricheStringheCoincidenti> results) {
		return results.values().stream().filter(e -> e.getPosNonCoincidenti().isEmpty())
				.map(MetricheStringheCoincidenti::getBottoniPremuti).findFirst();
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
		map.forEach((key, value) -> logger.debug("{} = {}", key, value));
	}

	public void printList(List<?> lista) {
		lista.forEach(logger::debug);
	}

	public BigInteger sommaMinimi() {
		multimapPercorsiMinimi.values().stream().forEach(c -> logger.debug("{},", c));
		return multimapPercorsiMinimi.values().stream().map(BigInteger::valueOf).reduce(BigInteger.ZERO,
				BigInteger::add);
	}

	public void controllaBottoni2() {
		for (int i = 0; i < results.size(); i++) {
			List<Integer> obiettivo = mappaVoltage.get(i);
			int minPressioni = this.task2(obiettivo, mappaBottoni.get(i));
			multimapPercorsiMinimi.put(obiettivo.toString(), minPressioni);
			logger.debug("{}{}", STRING_ARROW, multimapPercorsiMinimi.get(obiettivo.toString()));
		}

	}

	/**
	 * Nel tuo codice stai facendo questo:
	 * 
	 * scegli quante volte premere ogni bottone sommi gli effetti sui contatori
	 * verifichi se arrivi esattamente all’obiettivo tra tutte le combinazioni
	 * valide, scegli quella con meno pressioni
	 * 
	 * 👉 ILP fa la stessa cosa, ma:
	 * 
	 * senza esplorare rami senza DFS senza euristiche senza memo
	 * 
	 * @param obiettivo
	 * @param listaBottoni
	 * @return https://or-tools.github.io/docs/java/
	 *         https://medium.com/@pauljohncronin/linear-programming-with-or-tools-e541a272c4a0
	 *         https://www.youtube.com/watch?v=ce9CKjbmWN0
	 */
	public int task2(List<Integer> obiettivo, List<List<Integer>> listaBottoni) {
		MPSolver solver = Utility.loadResolver();

		MPVariable[] x = settingVariablesResolver(listaBottoni, solver);

		// Vincoli sui 9 contatori
		// Per ogni contatore j imponiamo un vincolo di uguaglianza
		for (int j = 0; j < obiettivo.size(); j++) {

			// Crea un vincolo:
			// lato sinistro = target[j]
			// lato destro = target[j]
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
			logger.debug("Soluzione ottima:");
			for (int i = 0; i < x.length; i++) {
				int val = (int) x[i].solutionValue();
				total += val;
				logger.debug("Bottone{}:{} ", i, val);
			}
			logger.debug("Totale pressioni = {}", total);
		} else {
			logger.debug("Nessuna soluzione trovata");
		}
		return total;
	}

	private MPVariable[] settingVariablesResolver(List<List<Integer>> listaBottoni, MPSolver solver) {
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
		return x;
	}

}
