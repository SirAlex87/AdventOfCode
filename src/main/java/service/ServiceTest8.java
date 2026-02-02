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

public class ServiceTest8 {
	private Map<String, List<Integer>> mappa = new HashMap<>();
	private List<String> lista = new ArrayList<>();
	private double [][] array;
	private List<List<String>> listaFinale=new ArrayList<>();
	private int cordinata1;
	private int cordinata2;

	public void populateList(String chiave) {
		List<Integer> numeri = Arrays.stream(chiave.split(",")) // -1 conserva eventuali vuoti
                .map(String::trim)
                .filter(s -> !s.isEmpty())          // scarta elementi vuoti (es. "43,,44")
                .map(Integer::parseInt)             // può lanciare NumberFormatException se non numerico
                .collect(Collectors.toList());

		mappa.put(chiave, numeri);
		lista.add(chiave);
	}

	public void calculateDistance() {
		array=new double[lista.size()][lista.size()];
		IntStream.range(0, lista.size()).forEach(s->{
			IntStream.range(0, lista.size()).filter(x->x!=s).forEach(x->{
				double distance=Math.sqrt(Math.pow(mappa.get(lista.get(x)).get(0) - mappa.get(lista.get(s)).get(0), 2) +
                         Math.pow(mappa.get(lista.get(x)).get(1) - mappa.get(lista.get(s)).get(1), 2) +
                         Math.pow(mappa.get(lista.get(x)).get(2) - mappa.get(lista.get(s)).get(2), 2));
				array[s][x]=distance;

			});
		});
		
	}
	
	public void populateCircuito(int numberConnections) {

		IntStream.range(0, numberConnections).forEach(i -> {
			Optional<MinResult> res = this.findMinPositive();
			res.ifPresentOrElse(r -> {
				System.out.printf("Min > 0.0 = %.3f in [%d][%d]%n, valori:%s e %s", r.getValue(), r.getRow(), r.getCol(),lista.get(r.getRow()),lista.get(r.getCol()));
				System.out.println();
				array[r.getRow()][r.getCol()] = 0.0;
				array[r.getCol()][r.getRow()] = 0.0;
				boolean isPresentFirstNumber = false;
				boolean isPresentSecondNumber = false;
				int indicePrimo=0;
				int indiceSecondo=0;
				int j = 0;
				for (j = 0; j < listaFinale.size(); j++) {
					if (listaFinale.get(j).contains(lista.get(r.getRow()))) {
						isPresentFirstNumber = true;
						indicePrimo=j;
					}
					if (listaFinale.get(j).contains(lista.get(r.getCol()))) {
						isPresentSecondNumber = true;
						indiceSecondo=j;
					}
				}
				if (isPresentFirstNumber && !isPresentSecondNumber) {
					listaFinale.get(indicePrimo).add(lista.get(r.getCol()));
					
				} else if (!isPresentFirstNumber && isPresentSecondNumber) {
					listaFinale.get(indiceSecondo).add(lista.get(r.getRow()));
				} else if (!isPresentFirstNumber && !isPresentSecondNumber){
					List<String> listaTemp = new ArrayList<>();
					listaTemp.add(lista.get(r.getRow()));
					listaTemp.add(lista.get(r.getCol()));
					listaFinale.add(listaTemp);
				}
				else {
					if(indicePrimo!=indiceSecondo) {
						listaFinale.get(indicePrimo).addAll(listaFinale.get(indiceSecondo));
						listaFinale.remove(indiceSecondo);
					}
				}
			}, () -> System.out.println("Nessun valore > 0.0 trovato"));

		});

	}
	
	public void populateCircuito() {
		AtomicBoolean keepGoing = new AtomicBoolean(true);
		//entro una lambda non puoi modificare variabili locali a meno che non siano final o effectively final.
		//E nel tuo caso keepGoing viene modificato, quindi non è effectively final → errore.
		//si puo usare AtomicBoolean
		
		while(keepGoing.get()) {
			Optional<MinResult> res = this.findMinPositive();
			res.ifPresentOrElse(r -> {
				array[r.getRow()][r.getCol()] = 0.0;
				array[r.getCol()][r.getRow()] = 0.0;
				boolean isPresentFirstNumber = false;
				boolean isPresentSecondNumber = false;
				int indicePrimo=0;
				int indiceSecondo=0;
				int j = 0;
				for (j = 0; j < listaFinale.size(); j++) {
					if (listaFinale.get(j).contains(lista.get(r.getRow()))) {
						isPresentFirstNumber = true;
						indicePrimo=j;						
					}
					if (listaFinale.get(j).contains(lista.get(r.getCol()))) {
						isPresentSecondNumber = true;
						indiceSecondo=j;
					}
				}
				if (isPresentFirstNumber && !isPresentSecondNumber) {
					listaFinale.get(indicePrimo).add(lista.get(r.getCol()));
					System.out.printf("1. Min > 0.0 = %.3f in [%d][%d]%n, valori:%s e %s", r.getValue(), r.getRow(), r.getCol(),lista.get(r.getRow()),lista.get(r.getCol()));
					System.out.println();
					cordinata1=mappa.get(lista.get(r.getRow())).get(0);
					cordinata2=mappa.get(lista.get(r.getCol())).get(0);
				} else if (!isPresentFirstNumber && isPresentSecondNumber) {
					listaFinale.get(indiceSecondo).add(lista.get(r.getRow()));
					System.out.printf("2. Min > 0.0 = %.3f in [%d][%d]%n, valori:%s e %s", r.getValue(), r.getRow(), r.getCol(),lista.get(r.getRow()),lista.get(r.getCol()));
					System.out.println();
					cordinata1=mappa.get(lista.get(r.getRow())).get(0);
					cordinata2=mappa.get(lista.get(r.getCol())).get(0);
				} else if (!isPresentFirstNumber && !isPresentSecondNumber){
					List<String> listaTemp = new ArrayList<>();
					listaTemp.add(lista.get(r.getRow()));
					listaTemp.add(lista.get(r.getCol()));
					listaFinale.add(listaTemp);
					System.out.printf("3. Min > 0.0 = %.3f in [%d][%d]%n, valori:%s e %s", r.getValue(), r.getRow(), r.getCol(),lista.get(r.getRow()),lista.get(r.getCol()));
					System.out.println();
					cordinata1=mappa.get(lista.get(r.getRow())).get(0);
					cordinata2=mappa.get(lista.get(r.getCol())).get(0);
				}
				else {
					if(indicePrimo!=indiceSecondo) {
						listaFinale.get(indicePrimo).addAll(listaFinale.get(indiceSecondo));
						listaFinale.remove(indiceSecondo);
						System.out.printf("4. Min > 0.0 = %.3f in [%d][%d]%n, valori:%s e %s", r.getValue(), r.getRow(), r.getCol(),lista.get(r.getRow()),lista.get(r.getCol()));
						System.out.println();
						cordinata1=mappa.get(lista.get(r.getRow())).get(0);
						cordinata2=mappa.get(lista.get(r.getCol())).get(0);
					}
				}
			}, 
					() -> {
						keepGoing.set(false);
			        });


		}

	}
	
	private Optional<MinResult> findMinPositive() {

		return IntStream.range(0, array.length).boxed().flatMap(r -> {
			return IntStream.range(0, array[0].length).mapToObj(c -> new MinResult(r, c, array[r][c]))
					.filter(res -> res.getValue() > 0.0);
		}).min(java.util.Comparator.comparingDouble(MinResult::getValue));

	}

	public String toStringListaFinale() {
		return "ServiceTest8 [listaFinale=" + listaFinale + "]";
	}
	
	@Override
	public String toString() {
		return "ServiceTest8 [mappa=" + mappa + "\n, lista=" + lista + "]";
	}
	
	public void printArray() {

		for (int i = 0; i < array.length; i++) { // scorre le righe
			for (int j = 0; j < array[i].length; j++) { // scorre le colonne
				System.out.println("[" + i + "][" + j + "] -> " + array[i][j]);
			}
			System.out.println(); // nuova riga per ogni riga
		}

		
	}

	public BigInteger multiplySizesLargestCircuits(int numberOfLargestCircuits) {
		BigInteger result = BigInteger.ONE;

		List<List<String>> listaTop = listaFinale.stream()
				.sorted(Comparator.<List<String>>comparingInt(List::size).reversed()).limit(numberOfLargestCircuits)
				.toList();

		for (List<String> inner : listaTop) {
			result = result.multiply(BigInteger.valueOf(inner.size()));
		}

		return result;

	}

	public double multiplyLastCoordinates() {
		System.out.println("cordinata1:"+cordinata1+", cordinata2:"+cordinata2);
		return cordinata1*cordinata2;
	}


	
	
	


}
