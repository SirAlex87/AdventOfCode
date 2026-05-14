package service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.javatuples.Pair;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import model.MetricheStringheCoincidenti;

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
			outer:
			for (Map.Entry<Integer, Integer> es : values.entrySet()) {
			    int chiaveForme = es.getKey();
			    List<Pair<Integer, Integer>> formeValori = mappaForme.get(chiaveForme);
			
			    for (int j = 1; j <= es.getValue(); j++) {
			        List<List<Pair<Integer, Integer>>> formeValoriListaCombinazioni =
			            calculateCombinationSelectForm(maxDimensionX, maxDimensionY, formeValori);
			
			        if (formeValoriListaCombinazioni.isEmpty()) {
			        	trovatoFit=false;
			            break outer; 
			        }
			
			        listaTutteCombinazioniPerGriglia.add(formeValoriListaCombinazioni);
			    }
			}
			if(trovatoFit) {
				mapRegioneFitted.put(chiave+"-"+e.getValue(), trovatoFit);
			}


		});
		
	}

	private List<List<Pair<Integer, Integer>>> calculateCombinationSelectForm(int maxDimensionX, int maxDimensionY, List<Pair<Integer,Integer>> formeValori) {
		List<List<Pair<Integer, Integer>>> formeValoriListaCombinazioni = new ArrayList<>();
		//noRotation
		List<List<Pair<Integer, Integer>>> formeValoriListaCombinazioniNoRotatation =this.calculateCombinationSelectFormForRotation(maxDimensionX, maxDimensionY, formeValori);
		formeValoriListaCombinazioni.addAll(formeValoriListaCombinazioniNoRotatation);
		//rotation+90
		formeValori=this.rotation(formeValori);
		List<List<Pair<Integer, Integer>>> formeValoriListaCombinazioni90Rotatation =this.calculateCombinationSelectFormForRotation(maxDimensionX, maxDimensionY, formeValori);
		formeValoriListaCombinazioni.addAll(formeValoriListaCombinazioni90Rotatation);
		//rotation+180
		formeValori=this.rotation(formeValori);
		List<List<Pair<Integer, Integer>>> formeValoriListaCombinazioni180Rotatation =this.calculateCombinationSelectFormForRotation(maxDimensionX, maxDimensionY, formeValori);
		formeValoriListaCombinazioni.addAll(formeValoriListaCombinazioni180Rotatation);
		//rotation+270
		formeValori=this.rotation(formeValori);
		List<List<Pair<Integer, Integer>>> formeValoriListaCombinazioni270Rotatation =this.calculateCombinationSelectFormForRotation(maxDimensionX, maxDimensionY, formeValori);
		formeValoriListaCombinazioni.addAll(formeValoriListaCombinazioni270Rotatation);
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
		System.out.println(mapRegioneFitted);	
		System.out.println("numero regioni Fitted:"+mapRegioneFitted.size());		
	}
	
	

	
}
