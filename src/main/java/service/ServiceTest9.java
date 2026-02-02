package service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import util.MinResult;

public class ServiceTest9 {
	private List<Integer> listaY = new ArrayList<>();
	private List<Integer> listaX = new ArrayList<>();
	private List<List<Integer>> listaFinale=new ArrayList<>();	

	public void populateList(String chiave) {
		List<Integer> numeri = Arrays.stream(chiave.split(",")) // -1 conserva eventuali vuoti
                .map(String::trim)
                .filter(s -> !s.isEmpty())          // scarta elementi vuoti (es. "43,,44")
                .map(Integer::parseInt)             // può lanciare NumberFormatException se non numerico
                .collect(Collectors.toList());

		listaY.add(numeri.get(0));
		listaX.add(numeri.get(1));
		List<Integer> listaTemp= new ArrayList<>();
		listaTemp.add(numeri.get(0));
		listaTemp.add(numeri.get(1));
		listaFinale.add(listaTemp);
	}

	private Optional<Integer> findMin(List<Integer> lista) {

		return lista.stream().min(Comparator.naturalOrder());

	}
	
	private Optional<Integer> findMax(List<Integer> lista) {

		return lista.stream().max(Comparator.naturalOrder());

	}
	
	private Optional<Integer> findMinXAssociatedToY(Integer value) {

		List listTemp=listaFinale.stream().filter(el->el.get(0).equals(value)).map(el->el.get(1)).toList();
		return this.findMin(listTemp);

	}
	
	private Optional<Integer> findMaxAssociatedToY(Integer value) {

		List listTemp=listaFinale.stream().filter(el->el.get(0).equals(value)).map(el->el.get(1)).toList();
		return this.findMax(listTemp);

	}
	
	public BigInteger calculateLargerArea() {
		Optional<Integer> maxListaY=this.findMax(listaY);
		Optional<Integer> minListaX=this.findMinXAssociatedToY(maxListaY.get());		
		Optional<Integer> minListaY=this.findMin(listaY);
		Optional<Integer> maxListaX=this.findMaxAssociatedToY(minListaY.get());
		//calcolo area 1
		BigInteger area1=BigInteger.valueOf((maxListaY.get()-minListaY.get()+1)*(maxListaX.get()-minListaX.get()+1));
		
		maxListaY=this.findMax(listaY);
		maxListaX=this.findMaxAssociatedToY(maxListaY.get());		
		minListaY=this.findMin(listaY);
		minListaX=this.findMinXAssociatedToY(minListaY.get());
		
		//calcolo area2
		BigInteger area2=BigInteger.valueOf((maxListaY.get()-minListaY.get()+1)*(maxListaX.get()-minListaX.get()+1));
		
		return area1.compareTo(area2)>0?area1:area2;
	}

}
