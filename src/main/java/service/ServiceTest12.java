package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javatuples.Pair;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

public class ServiceTest12 {
	private static Logger logger = LogManager.getLogger(ServiceTest12.class);
	
	
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
				mappaForme.put(acc.getValue0(), new ArrayList<>());
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

	public void getRegionsFitted() {

		Map<String, Boolean> mapRegioneFittedFiltered = mapRegioneFitted.entrySet()
		    .stream()
		    .filter(entry -> Boolean.TRUE.equals(entry.getValue()))
		    .collect(Collectors.toMap(
		        Map.Entry::getKey,
		        Map.Entry::getValue
		    ));

		logger.debug(mapRegioneFittedFiltered);	
		logger.info("numero regioni Fitted:{}",mapRegioneFittedFiltered.size());		
	}

	public void calculateArea() {
		mappaGriglia.entries().stream().forEach(e->{
			listaTutteCombinazioniPerGriglia.clear();
			String chiave=e.getKey();
			logger.log(Level.DEBUG,"griglia:{}",chiave);
			String[] arrayDimenstions=chiave.split("x");
			int maxDimensionX=Integer.parseInt(arrayDimenstions[0]);
			int maxDimensionY=Integer.parseInt(arrayDimenstions[1]);
			int areaGriglia=maxDimensionX*maxDimensionY;
			int areaCalcolata=0;
			boolean trovatoFit=false;
			Map<Integer,Integer> values = new HashMap<>();

			IntStream.range(0, e.getValue().size())
			         .filter(i -> e.getValue().get(i) > 0)
			         .forEach(i ->
			             values.put(i, e.getValue().get(i))
			         );			
			
			for (Map.Entry<Integer, Integer> es : values.entrySet()) {
			    int chiaveForme = es.getKey();
			    logger.log(Level.DEBUG,"forma:{}",chiaveForme);
			    areaCalcolata=areaCalcolata+9*es.getValue();			    
			}
			if(areaCalcolata<=areaGriglia) {
				trovatoFit=true;				
			}
			mapRegioneFitted.put(chiave+"-"+e.getValue(), trovatoFit);
		});
		
	}
	
	

	
}
