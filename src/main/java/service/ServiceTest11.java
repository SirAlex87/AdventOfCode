package service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ServiceTest11 {

	private Map<String, List<String>> mappaConnessioni = new HashMap<>();
	private List<String> listaChiaviArrivo = new ArrayList<>();
	private List<String> listaChiaviDaEscludere = new ArrayList<>();
	
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
		listaChiaviDaEscludere = new ArrayList<>();
		List<String> valoriPartenza=new ArrayList<>();
		valoriPartenza.addAll(mappaConnessioni.get(partenza));
		List<List<String>> results=new ArrayList();
		List<String> listaObiettiviRaggiunti=new ArrayList<>();
		boolean trovato=this.proseguiPath(valoriPartenza, listaObiettiviRaggiunti, results);
		System.out.println(results);
		//System.out.println("tutti i path sono: "+results.size());
		return results;
		
	}
	
	

	public Map<String, List<String>> getMappaConnessioni() {
		return mappaConnessioni;
	}

	public void setMappaConnessioni(Map<String, List<String>> mappaConnessioni) {
		this.mappaConnessioni = mappaConnessioni;
	}

	private boolean proseguiPath(List<String> valoriPartenza, List<String> listaObiettiviRaggiunti, List<List<String>> results) {
		boolean trovato=false;
		//System.out.println(valoriPartenza);		
		
		if( valoriPartenza==null || valoriPartenza.isEmpty()) {
			return false;
		}
		else {
			valoriPartenza.removeAll(listaChiaviDaEscludere);
		}
		for(String nuovaChiave:valoriPartenza) {
//			if(listaObiettiviRaggiunti.contains(nuovaChiave)){
//				return false;
//			}
//			if(results.contains(listaObiettiviRaggiunti))
//			{
//				return false;
//			}
			if(!listaChiaviArrivo.contains(nuovaChiave)) {
				listaObiettiviRaggiunti.add(nuovaChiave);
				valoriPartenza=new ArrayList<>();
				if(mappaConnessioni.get(nuovaChiave)!=null)
					valoriPartenza.addAll(mappaConnessioni.get(nuovaChiave));
				
				trovato=this.proseguiPath(valoriPartenza, listaObiettiviRaggiunti,results);
				if(trovato==false) {
					listaChiaviDaEscludere.add(nuovaChiave);
				}
			}
			else {
				List<String> listaObiettiviRaggiuntiTemp = new ArrayList<>();
				listaObiettiviRaggiuntiTemp.addAll(listaObiettiviRaggiunti);
				listaObiettiviRaggiuntiTemp.add(nuovaChiave);
				results.add(listaObiettiviRaggiuntiTemp);
				trovato=true;
			}
			
		}
//		if(!listaObiettiviRaggiunti.isEmpty()) {
//			listaObiettiviRaggiunti.remove(listaObiettiviRaggiunti.size()-1);
//		}
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
	

	public  BigInteger countPaths(
            String current,
            String target,
            Map<String, List<String>> graph,
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
            totalPaths=totalPaths.add(countPaths(next, target, graph, memo));
        }

        memo.put(current, totalPaths);
        return totalPaths;
    }

	

public  BigInteger countPaths(
            String current,
            String target,
            Map<String, List<String>> graph,
            Map<String, BigInteger> memo, String passaggioIntermedio1, String passaggioIntermedio2, int numeroPassaggioIntermedio) {

        // Caso base: siamo arrivati a OUT
        if (current.equals(target)) {
        	if(numeroPassaggioIntermedio==2 || numeroPassaggioIntermedio==4)
        		return BigInteger.ONE;
        	else 
        		return BigInteger.ZERO;
        }
        
        if(numeroPassaggioIntermedio==0 && current.equals(passaggioIntermedio1)) {
        	numeroPassaggioIntermedio=1;
        }
        else if(numeroPassaggioIntermedio==1 && current.equals(passaggioIntermedio2)) {
        	numeroPassaggioIntermedio=2;
        }
        else if(numeroPassaggioIntermedio==0 && current.equals(passaggioIntermedio2)) {
        	numeroPassaggioIntermedio=3;
        }
        else if(numeroPassaggioIntermedio==3 && current.equals(passaggioIntermedio1)) {
        	numeroPassaggioIntermedio=4;
        }

        // Se già calcolato, ritorniamo il valore
        if (memo.containsKey(current) && (numeroPassaggioIntermedio==2 || numeroPassaggioIntermedio==4)) {
            return memo.get(current);
        }

        BigInteger totalPaths = BigInteger.ZERO;

        // Se il nodo non ha uscite
        if (!graph.containsKey(current)) {
            return BigInteger.ZERO;
        }

        // Visitiamo tutti i nodi successivi
        for (String next : graph.get(current)) {
            totalPaths =totalPaths.add(countPaths(next, target, graph, memo,passaggioIntermedio1, passaggioIntermedio2,numeroPassaggioIntermedio));
            if(numeroPassaggioIntermedio==2 && next.equals(passaggioIntermedio2)) {
            	numeroPassaggioIntermedio=1;
            }
            else if(numeroPassaggioIntermedio==3 && next.equals(passaggioIntermedio2)) {
            	numeroPassaggioIntermedio=0;
            }
            else if(numeroPassaggioIntermedio==4 && next.equals(passaggioIntermedio1)) {
            	numeroPassaggioIntermedio=3;
            }
            else if(numeroPassaggioIntermedio==1 && next.equals(passaggioIntermedio1)) {
            	numeroPassaggioIntermedio=0;
            }
        }
        if(totalPaths.compareTo(BigInteger.ZERO)>0)
        	memo.put(current, totalPaths);
        return totalPaths;
    }

	




}
