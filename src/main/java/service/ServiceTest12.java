package service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import model.MetricheStringheCoincidenti;

public class ServiceTest12 {
	private Map<Integer,List<List<Integer>>> mappaForme=new HashMap<>();
	private ListMultimap<String,List<Integer>> mappaGriglia=ArrayListMultimap.create();
	
	public Integer populateList(String s, int result) {
		if (StringUtils.isNotBlank(s)) {
			if (s.contains("x") && s.contains(":")) {
				String dimensioniGrigia=s.substring(0, s.indexOf(":"));
				String valori=s.substring(s.indexOf(":")+2);

				List<Integer> lista =Arrays.stream(valori.split(" ")).map(Integer::parseInt).toList();
				mappaGriglia.put(dimensioniGrigia, lista);

			} else if (s.contains(":")) {
				result = Integer.parseInt(s.substring(0, s.indexOf(":")));
				mappaForme.put(result, new ArrayList());
			} else {
				List<List<Integer>> listaTemp = mappaForme.get(result);

				List<Integer> indici = new ArrayList<>();

				int index = StringUtils.indexOf(s, '#');
				while (index != -1) {
					indici.add(index);
					index = StringUtils.indexOf(s, '#', index + 1);
				}
				listaTemp.add(indici);
				mappaForme.put(result, listaTemp);

			}
		}
		return result;
	}

	@Override
	public String toString() {
		return "ServiceTest12 [mappaForme=" + mappaForme + ", mappaGriglia=" + mappaGriglia + "]";
	}

	public void calculateCombination() {
		mappaGriglia.entries().stream().forEach(e->{
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
			values.entrySet().forEach(es->{
				int chiaveForme=es.getKey();
				List<List<Integer>> formeValori=mappaForme.get(chiaveForme);
				IntStream.range(1, es.getValue()+1).forEach(j->{
					calculateCombinationSelectForm(maxDimensionX, maxDimensionY,formeValori);
				});
			});

		});
		
	}

	private void calculateCombinationSelectForm(int maxDimensionX, int maxDimensionY, List<List<Integer>> formeValori) {
		//noRotation
		this.calculateCombinationSelectFormForRotation(maxDimensionX, maxDimensionY, formeValori);
		//rotation+90
		formeValori=this.rotation(formeValori);
		this.calculateCombinationSelectFormForRotation(maxDimensionX, maxDimensionY, formeValori);
		//rotation+180
		formeValori=this.rotation(formeValori);
		this.calculateCombinationSelectFormForRotation(maxDimensionX, maxDimensionY, formeValori);
		//rotation+270
		formeValori=this.rotation(formeValori);
		this.calculateCombinationSelectFormForRotation(maxDimensionX, maxDimensionY, formeValori);
	}

	private List<List<Integer>> rotation(List<List<Integer>> formeValori) {

		List<List<Integer>> formeValoriAfterRotation=new ArrayList<>();
int max = formeValori.stream()
                     .flatMap(List::stream)
                     .mapToInt(Integer::intValue)
                     .max()
                     .orElseThrow();
Map<Integer,List<Integer>> mappaForme=new HashMap<>();
		for(int i=max;i<formeValori.size();i--) {
			mappaForme.put(i, formeValori.get(max-i));
		}
		
		for(int x=0;x<max;x++) {			
			List<Integer> listaTemp= new ArrayList<>();
			for(int y=0;y<max;y++) {
				if(mappaForme.get(y).contains(x)){
					listaTemp.add(x);
				}
			}	
			formeValoriAfterRotation.add(listaTemp);
		}
		return formeValoriAfterRotation;
		
	}

	private void calculateCombinationSelectFormForRotation(int maxDimensionX, int maxDimensionY, List<List<Integer>> formeValori) {
		List<List<List<Integer>>> formeValoriListaCombinazioni = new ArrayList<>();
		for (int x = 0; x < maxDimensionX; x++) {
			if (formeValori.size() + x < maxDimensionX) {
				for (int y = 0; y < maxDimensionY; y++) {
					List<List<Integer>> formeValoriTemp = new ArrayList<>();
					for (int i = 0; i < formeValori.size(); i++) {
						List<Integer> listaAvanzata = this.avanzaY(formeValori.get(i), maxDimensionX, maxDimensionY, y);
						if (listaAvanzata != null) {
							formeValoriTemp.add(listaAvanzata);
						} else {
							break;
						}
					}
					if (formeValoriTemp.size() == formeValori.size())
						formeValoriListaCombinazioni.add(formeValoriTemp);
				}
			}
		}
	}

	private List<Integer> avanzaY(List<Integer> list, int maxDimensionX, int maxDimensionY, int y) {
		boolean superatiLimiti=false;
		List<Integer> listTemp=new ArrayList<>();
		for(int i=0;i<list.size();i++) {
			if(list.get(i)+y<=maxDimensionY) {
				listTemp.add(list.get(i)+y);
			}
			else {
				superatiLimiti=true;
			}
		}
		if(superatiLimiti) {
			return null;
		}
		else {    
			return listTemp;
		}
	}
	
	

	
}
