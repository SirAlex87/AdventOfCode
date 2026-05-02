package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;

import model.MetricheStringheCoincidenti;

public class ServiceTest11 {

	private Map<String, List<String>> mappaConnessioni = new HashMap<>();
	private List<String> listaChiaviArrivo = new ArrayList<>();
	
	public void populateList(String s) {
		String chiave = s.substring(0, s.indexOf(":"));
		List<String> valori=new ArrayList<>(Arrays.asList(s.substring(s.indexOf(":")+2).split(" ")));
		mappaConnessioni.put(chiave, valori);
	}

	public List<String> calcolaPathArrivo(String uscita) {
		listaChiaviArrivo=mappaConnessioni.entrySet()
		        .stream()
		        .filter(entry -> entry.getValue().contains(uscita))
		        .map(Map.Entry::getKey).toList();
		return listaChiaviArrivo;
	}

	public List<List<String>> calcolaPath(String partenza) {
		List<String> valoriPartenza=mappaConnessioni.get(partenza);
		List<List<String>> results=new ArrayList();
		List<String> listaObiettiviRaggiunti=new ArrayList<>();
		boolean trovato=this.proseguiPath(valoriPartenza, listaObiettiviRaggiunti, results);
		//System.out.println(results);
		//System.out.println("tutti i path sono: "+results.size());
		return results;
		
	}

	private boolean proseguiPath(List<String> valoriPartenza, List<String> listaObiettiviRaggiunti, List<List<String>> results) {
		boolean trovato=false;
		//System.out.println(valoriPartenza);
		if( valoriPartenza==null) {
			return false;
		}
		for(String nuovaChiave:valoriPartenza) {
			if(listaObiettiviRaggiunti.contains(nuovaChiave)){
				return false;
			}
			if(results.contains(listaObiettiviRaggiunti))
			{
				return false;
			}
			if(!listaChiaviArrivo.contains(nuovaChiave)) {
				listaObiettiviRaggiunti.add(nuovaChiave);
				trovato=this.proseguiPath(mappaConnessioni.get(nuovaChiave), listaObiettiviRaggiunti,results);
			}
			else {
				List<String> listaObiettiviRaggiuntiTemp = new ArrayList<>();
				listaObiettiviRaggiuntiTemp.addAll(listaObiettiviRaggiunti);
				listaObiettiviRaggiuntiTemp.add(nuovaChiave);
				results.add(listaObiettiviRaggiuntiTemp);
				trovato=true;
			}
			
		}
		if(!listaObiettiviRaggiunti.isEmpty()) {
			listaObiettiviRaggiunti.remove(listaObiettiviRaggiunti.size()-1);
		}
		return trovato;
	}

	private List<String> confrontoChiavi(Map<String, List<String>> firstResults,
			Map<String, List<String>> lastResults) {
			
		   List<String> listaChiavi= new ArrayList<>();
			Set<String> chiaviComuni = new HashSet<>(firstResults.keySet());
			chiaviComuni.retainAll(lastResults.keySet());
			if (!chiaviComuni.isEmpty()) {
				String chiave = chiaviComuni.iterator().next();
				listaChiavi.addAll(firstResults.get(chiave));
				listaChiavi.addAll(lastResults.get(chiave));
				//this.printList(listaBottoni);
			}
			return listaChiavi;

	}
	




}
