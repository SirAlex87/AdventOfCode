package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.javatuples.Pair;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

public class ServiceTest12 {
	private Map<Integer,List<Pair<Integer,Integer>>> mappaForme=new HashMap<>();
	private ListMultimap<String,List<Integer>> mappaGriglia=ArrayListMultimap.create();
	private List<List<List<Pair<Integer, Integer>>>> listaTutteCombinazioniPerGriglia=new ArrayList<>();
	private Map<String,Boolean> mapRegioneFitted=new HashMap<>();
	public Pair<Integer, Integer> populateList(String s, Pair<Integer, Integer> acc) {
		if (StringUtils.isNotBlank(s)) {
			if (s.contains("x") && s.contains(":")) {
				String dimensioniGrigia=s.substring(0, s.indexOf(":"));
				String valori=s.substring(s.indexOf(":")+2);

				List<Integer> lista =Arrays.stream(valori.split(" ")).map(Integer::parseInt).toList();
				mappaGriglia.put(dimensioniGrigia, lista);

			} else if (s.contains(":")) {
				acc = new Pair<>(Integer.parseInt(s.substring(0, s.indexOf(":"))), 0);
				mappaForme.put(acc.getValue0(), new ArrayList());
			} else {
				List<Pair<Integer,Integer>> indici = mappaForme.get(acc.getValue0());
				if(!indici.isEmpty()) {
					acc = new Pair<>(acc.getValue0(), acc.getValue1()+1);
				}
				int riga=acc.getValue1();

				int index = StringUtils.indexOf(s, '#');
				while (index != -1) {
					Pair<Integer, Integer> pair = new Pair<>(riga, index);
					indici.add(pair);
					index = StringUtils.indexOf(s, '#', index + 1);
				}
				mappaForme.put(acc.getValue0(), indici);

			}
		}
		return acc;
	}

	@Override
	public String toString() {
		return "ServiceTest12 [mappaForme=" + mappaForme + ", mappaGriglia=" + mappaGriglia + "]";
	}

	public void calculateCombination() {
		mappaGriglia.entries().stream().forEach(e->{
			listaTutteCombinazioniPerGriglia.clear();
			String chiave=e.getKey();
			System.out.println("griglia:"+chiave);
			String[] arrayDimenstions=chiave.split("x");
			int maxDimensionX=Integer.parseInt(arrayDimenstions[0]);
			int maxDimensionY=Integer.parseInt(arrayDimenstions[1]);

			Map<Integer,Integer> values = new HashMap<>();

			IntStream.range(0, e.getValue().size())
			         .filter(i -> e.getValue().get(i) > 0)
			         .forEach(i ->{
			             values.put(i, e.getValue().get(i));
			         });			
			
			boolean trovatoFit=true;
			List<List<Pair<Integer, Integer>>> formeValoriListaCombinazioni=new ArrayList();	
			for (Map.Entry<Integer, Integer> es : values.entrySet()) {
			    int chiaveForme = es.getKey();
			    System.out.println("forma:"+chiaveForme);
			    List<Pair<Integer, Integer>> formeValori = mappaForme.get(chiaveForme);
			    Map<List<Pair<Integer, Integer>>, List<List<Pair<Integer, Integer>>>> mappa= new HashMap<>();
			    for (int j = 1; j <= es.getValue(); j++) {
			    	if(j==1) {
			    		formeValoriListaCombinazioni=calculateCombinationSelectForm(maxDimensionX, maxDimensionY, formeValori);
			    		if (formeValoriListaCombinazioni.isEmpty()) {
				        	trovatoFit=false;
				            break; 
				        }
			    	}
			    	else {
//			    		for(List<List<Pair<Integer, Integer>>> listaTutteCombinazioniPerGrigliaFinoAdessoTemp:listaTutteCombinazioniPerGriglia) {
//			    				formeValoriListaCombinazioni.addAll(listaTutteCombinazioniPerGrigliaFinoAdessoTemp);
//			    			}
			    		formeValoriListaCombinazioni=calculateCombinationSelectFormRepeated(formeValoriListaCombinazioni);
			    		if(j==2)
			    			mappa=mappaCombinazioni(formeValoriListaCombinazioni);
			    		else {
			    			
			    		}
			    		
			    	}
			//TODO prima interazione giro AS-IS con i duplicati partire invece dai possibili path del passo precedente escludendo liste che si intersecano e così via, questo per forme uguali
			        //da capire come gestire con le altre forme
			        
			    }
			    if (formeValoriListaCombinazioni.isEmpty()) {
		        	trovatoFit=false;
		            break; 
		        }		
		        
			    if(!trovatoFit) {
			    	break;
			    }
			    listaTutteCombinazioniPerGriglia.add(formeValoriListaCombinazioni);
			}
			if(trovatoFit) {
				mapRegioneFitted.put(chiave+"-"+e.getValue(), trovatoFit);
			}


		});
		
	}

	private Map<List<Pair<Integer, Integer>>, List<List<Pair<Integer, Integer>>>> mappaCombinazioni(List<List<Pair<Integer, Integer>>> formeValoriListaCombinazioni) {
		Map<List<Pair<Integer, Integer>>, List<List<Pair<Integer, Integer>>>> mappa= new HashMap<>();
		for (List<Pair<Integer, Integer>> list : formeValoriListaCombinazioni) {
			mappa.put(list, calculateCombinationSelectFormRepeated(formeValoriListaCombinazioni));
		}
		return mappa;
	}

	private List<List<Pair<Integer, Integer>>> calculateCombinationSelectFormRepeated(
			List<List<Pair<Integer, Integer>>> listaFormePossibili) {

		// Converto in Set per lookup O(1)
		List<Set<Pair<Integer, Integer>>> sets = new ArrayList<>(listaFormePossibili.size());
		for (List<Pair<Integer, Integer>> list : listaFormePossibili) {
			sets.add(new HashSet<>(list));
		}

		List<List<Pair<Integer, Integer>>> result = new ArrayList<>();

		for (int i = 0; i < sets.size(); i++) {
			Set<Pair<Integer, Integer>> current = sets.get(i);

			boolean trovato=trovaDisjoint(sets, current);
			if(trovato)
				result.add(listaFormePossibili.get(i));
		}

		return result;
	}

	private boolean trovaDisjoint(
			List<Set<Pair<Integer, Integer>>> sets, 
			Set<Pair<Integer, Integer>> current) {
		boolean foundDisjoint = false;
		for (int j = 0; j < sets.size(); j++) {
			if (current.equals(sets.get(j)))
				continue;
			Set<Pair<Integer, Integer>> other = sets.get(j);
			// check disgiunzione
			boolean disjoint = true;
			for (Pair<Integer, Integer> p : current) {
				if (other.contains(p)) {
					disjoint = false;
					break;
				}
			}
			if (disjoint) {
				foundDisjoint = true;
				break; // basta una
			}
		}
		return foundDisjoint;
	}

	private List<List<Pair<Integer, Integer>>> calculateCombinationSelectForm(int maxDimensionX, int maxDimensionY, List<Pair<Integer,Integer>> formeValori) {
		List<List<Pair<Integer, Integer>>> formeValoriListaCombinazioni = new ArrayList<>();
		//noRotation
		List<List<Pair<Integer, Integer>>> formeValoriListaCombinazioniNoRotatation =this.calculateCombinationSelectFormForRotation(maxDimensionX, maxDimensionY, formeValori);
		formeValoriListaCombinazioni.addAll(formeValoriListaCombinazioniNoRotatation);
		//System.out.println("finito1");
		//rotation+90
		formeValori=this.rotation(formeValori);
		List<List<Pair<Integer, Integer>>> formeValoriListaCombinazioni90Rotatation =this.calculateCombinationSelectFormForRotation(maxDimensionX, maxDimensionY, formeValori);
		formeValoriListaCombinazioni.addAll(formeValoriListaCombinazioni90Rotatation);
		//System.out.println("finito2");
		//rotation+180
		formeValori=this.rotation(formeValori);
		List<List<Pair<Integer, Integer>>> formeValoriListaCombinazioni180Rotatation =this.calculateCombinationSelectFormForRotation(maxDimensionX, maxDimensionY, formeValori);
		formeValoriListaCombinazioni.addAll(formeValoriListaCombinazioni180Rotatation);
		//System.out.println("finito3");
		//rotation+270
		formeValori=this.rotation(formeValori);
		List<List<Pair<Integer, Integer>>> formeValoriListaCombinazioni270Rotatation =this.calculateCombinationSelectFormForRotation(maxDimensionX, maxDimensionY, formeValori);
		formeValoriListaCombinazioni.addAll(formeValoriListaCombinazioni270Rotatation);
		//System.out.println("finito4");
		return formeValoriListaCombinazioni;
	}

	private List<Pair<Integer, Integer>> rotation(List<Pair<Integer, Integer>> formeValori) {

		List<Pair<Integer, Integer>> formeValoriAfterRotation = new ArrayList<>();
		int max = formeValori.stream().mapToInt(c -> c.getValue1()).max().orElseThrow();
		for (Pair<Integer, Integer> pair : formeValori) {
			Pair<Integer, Integer> nuovapair = new Pair<>(pair.getValue1(), max - pair.getValue0());
			formeValoriAfterRotation.add(nuovapair);
		}
		return formeValoriAfterRotation;

	}

	private List<List<Pair<Integer, Integer>>> calculateCombinationSelectFormForRotation(int maxDimensionX, int maxDimensionY,
			List<Pair<Integer, Integer>> formeValori) {
		List<List<Pair<Integer, Integer>>> formeValoriListaCombinazioni = new ArrayList<>();
		for (int x = 0; x < maxDimensionX; x++) {
			for (int y = 0; y < maxDimensionY; y++) {
				List<Pair<Integer, Integer>> listaAvanzata = this.avanzaY(formeValori, maxDimensionY, y, maxDimensionX,
						x);
				if (!listaAvanzata.isEmpty() && listaAvanzata.size() == formeValori.size()) {
					formeValoriListaCombinazioni.add(listaAvanzata);
				}
			}
		}
		return formeValoriListaCombinazioni;
	}

	private List<Pair<Integer, Integer>> avanzaY(List<Pair<Integer, Integer>> list, int maxDimensionY, int y, int maxDimensionX, int x) {
		//System.out.println("avanzaY:"+y+"/"+maxDimensionY+"-"+x+"/"+maxDimensionX);
		boolean superatiLimiti=false;
		boolean coordinateNonPrese=true;
		boolean siamoAllInizio=false;
		List<List<Pair<Integer, Integer>>> ultimaListaCombinazioni=listaTutteCombinazioniPerGriglia.isEmpty()?new ArrayList<>():listaTutteCombinazioniPerGriglia.getLast();
		if(ultimaListaCombinazioni.isEmpty()) {
			siamoAllInizio=true;
		}
		List<Pair<Integer, Integer>> listTemp=new ArrayList<>();
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getValue1()+y<maxDimensionY && list.get(i).getValue0()+x<maxDimensionX) {
				Pair<Integer, Integer> pair = new Pair<>(list.get(i).getValue0()+x, list.get(i).getValue1()+y);
				
				if(siamoAllInizio) {
					listTemp.add(pair);
				}
				else {
					ultimaListaCombinazioni=this.coppiaPresa(pair,ultimaListaCombinazioni);
					if(!ultimaListaCombinazioni.isEmpty()) {
						listTemp.add(pair);
					}
					else {
						coordinateNonPrese=false;
						break;
					}
				}
				
			}
			else {
				superatiLimiti=true;
				break;
			}
		}
		if(superatiLimiti || !coordinateNonPrese) {
			return Collections.emptyList();
		}
		else {    
			return listTemp;
		}
	}

	private List<List<Pair<Integer, Integer>>> coppiaPresa(Pair<Integer, Integer> pair, List<List<Pair<Integer, Integer>>> ultimaListaCombinazioni) {

		return ultimaListaCombinazioni.stream()
			    .filter(l -> !l.contains(pair))
			    .toList();


	}

	public void getRegionsFitted() {

		Map<String, Boolean> mapRegioneFittedFiltered = mapRegioneFitted.entrySet()
		    .stream()
		    .filter(entry -> Boolean.TRUE.equals(entry.getValue()))
		    .collect(Collectors.toMap(
		        Map.Entry::getKey,
		        Map.Entry::getValue
		    ));

		System.out.println(mapRegioneFittedFiltered);	
		System.out.println("numero regioni Fitted:"+mapRegioneFittedFiltered.size());		
	}

	public void calculateArea() {
		mappaGriglia.entries().stream().forEach(e->{
			listaTutteCombinazioniPerGriglia.clear();
			String chiave=e.getKey();
			System.out.println("griglia:"+chiave);
			String[] arrayDimenstions=chiave.split("x");
			int maxDimensionX=Integer.parseInt(arrayDimenstions[0]);
			int maxDimensionY=Integer.parseInt(arrayDimenstions[1]);
			int areaGriglia=maxDimensionX*maxDimensionY;
			int areaCalcolata=0;
			boolean trovatoFit=false;
			Map<Integer,Integer> values = new HashMap<>();

			IntStream.range(0, e.getValue().size())
			         .filter(i -> e.getValue().get(i) > 0)
			         .forEach(i ->{
			             values.put(i, e.getValue().get(i));
			         });			
			
			for (Map.Entry<Integer, Integer> es : values.entrySet()) {
			    int chiaveForme = es.getKey();
			    System.out.println("forma:"+chiaveForme);
			    areaCalcolata=areaCalcolata+9*es.getValue();			    
			}
			if(areaCalcolata<=areaGriglia) {
				trovatoFit=true;				
			}
			mapRegioneFitted.put(chiave+"-"+e.getValue(), trovatoFit);
		});
		
	}
	
	

	
}
