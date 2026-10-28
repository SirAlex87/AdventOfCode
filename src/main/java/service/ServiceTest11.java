package service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServiceTest11 {
	private static final Logger logger = LogManager.getLogger(ServiceTest11.class);
	private Map<String, List<String>> mappaConnessioni = new HashMap<>();
	private List<String> listaChiaviArrivo = new ArrayList<>();
	private List<String> listaChiaviDaEscludere = new ArrayList<>();

	public void populateList(String s) {
		String chiave = s.substring(0, s.indexOf(":"));
		List<String> valori = new ArrayList<>(Arrays.asList(s.substring(s.indexOf(":") + 2).split(" ")));
		mappaConnessioni.put(chiave, valori);
	}

	public List<String> calcolaPathArrivo(String uscita) {
		listaChiaviArrivo = mappaConnessioni.entrySet().stream().filter(entry -> entry.getValue().contains(uscita))
				.map(Map.Entry::getKey).toList();
		return listaChiaviArrivo;
	}

	public List<List<String>> calcolaPath(String partenza) {
		listaChiaviDaEscludere = new ArrayList<>();
		List<String> valoriPartenza = new ArrayList<>();
		valoriPartenza.addAll(mappaConnessioni.get(partenza));
		List<List<String>> results = new ArrayList();
		List<String> listaObiettiviRaggiunti = new ArrayList<>();
		this.proseguiPath(valoriPartenza, listaObiettiviRaggiunti, results);
		logger.debug(results);
		return results;

	}

	public Map<String, List<String>> getMappaConnessioni() {
		return mappaConnessioni;
	}

	private boolean proseguiPath(List<String> valoriPartenza, List<String> listaObiettiviRaggiunti,
			List<List<String>> results) {
		boolean trovato = false;
		logger.debug(valoriPartenza);

		if (valoriPartenza == null || valoriPartenza.isEmpty()) {
			return false;
		} else {
			valoriPartenza.removeAll(listaChiaviDaEscludere);
		}
		for (String nuovaChiave : valoriPartenza) {
			if (!listaChiaviArrivo.contains(nuovaChiave)) {
				listaObiettiviRaggiunti.add(nuovaChiave);
				valoriPartenza = new ArrayList<>();
				if (mappaConnessioni.get(nuovaChiave) != null)
					valoriPartenza.addAll(mappaConnessioni.get(nuovaChiave));

				trovato = this.proseguiPath(valoriPartenza, listaObiettiviRaggiunti, results);
				if (!trovato) {
					listaChiaviDaEscludere.add(nuovaChiave);
				}
			} else {
				List<String> listaObiettiviRaggiuntiTemp = new ArrayList<>();
				listaObiettiviRaggiuntiTemp.addAll(listaObiettiviRaggiunti);
				listaObiettiviRaggiuntiTemp.add(nuovaChiave);
				results.add(listaObiettiviRaggiuntiTemp);
				trovato = true;
			}

		}
		return trovato;
	}

	public BigInteger countPaths(String current, String target, Map<String, List<String>> graph,
			Map<String, BigInteger> memo) {

		// Caso base: siamo arrivati a OUT
		if (current.equals(target)) {
			return BigInteger.ONE;
		}

		// Se già calcolato, ritorniamo il valore
		if (memo.containsKey(current)) {
			return memo.get(current);
		}

		BigInteger totalPaths = BigInteger.ZERO;

		// Se il nodo non ha uscite
		if (!graph.containsKey(current)) {
			return BigInteger.ZERO;
		}

		// Visitiamo tutti i nodi successivi
		for (String next : graph.get(current)) {
			totalPaths = totalPaths.add(countPaths(next, target, graph, memo));
		}

		memo.put(current, totalPaths);
		return totalPaths;
	}

}
