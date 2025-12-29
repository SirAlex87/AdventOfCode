package service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ServiceTest6 {

	private static int i=0;
	private String [][] arrayFinal = null;
	private String [][] arraySecondFinal = null;
	List<Integer> listaPosizioniDelimitatori= new ArrayList<Integer>();
	
	public void populateArray(String s) {
		String []a = s.split("-");
		IntStream.rangeClosed(0, a.length-1).forEach(j->{
			arrayFinal[i][j]=a[j];
		});
		i++;
	}

	public void initilizeArray(String[] strParts) {
		arrayFinal= new String[strParts.length][strParts[0].split("-").length];
		
	}

	@Override
	public String toString() {
		return "ServiceTest6 [arrayFinal=" + Arrays.toString(arrayFinal) + "]";
	}

	public BigInteger calculate() {
		BigInteger contoFinale = BigInteger.ZERO;

		int numColumns = arrayFinal[0].length;

		for (int i = 0; i < numColumns; i++) { // scorre le righe
			BigInteger contoParziale = BigInteger.ZERO;
			for (int j = arrayFinal.length - 2; j >= 0; j--) {
				if (arrayFinal[arrayFinal.length - 1][i].equals("+")) {
					contoParziale = contoParziale.add(new BigInteger(arrayFinal[j][i]));
				}
				if (arrayFinal[arrayFinal.length - 1][i].equals("*")) {
					if (contoParziale.equals(BigInteger.ZERO)){
						contoParziale=BigInteger.ONE;
					}
					contoParziale = contoParziale.multiply(new BigInteger(arrayFinal[j][i]));
				}
			}
			contoFinale=contoFinale.add(contoParziale);
		}



		return contoFinale;
	}
	
	public BigInteger calculate2() {
		BigInteger contoFinale = BigInteger.ZERO;

		int numColumns = arrayFinal[0].length;
		int limite=0;

		for (int i = 0; i < numColumns; i++) {// colonne
			BigInteger contoParziale = BigInteger.ZERO;
			for (int j = 0; j <= arrayFinal.length - 2; j++) {// righe
				if (arrayFinal[arrayFinal.length - 1][i].trim().equals("+")) {
					limite=arrayFinal[j][i].length();
					for (int k = 0; k < limite; k++) {
						String xFinale = "";
						for (int x = 0; x <= arrayFinal.length - 2; x++) {// righe
							System.out.println("X:"+x+" Limite:"+limite+" k:"+k+" i:"+i+"j:"+j);
							String xTemp = arrayFinal[x][i].substring(limite - k-1,
									limite -k);
							if (xTemp.isBlank()) {
								xTemp = "";
							}
							xFinale += xTemp;
						}
						contoParziale = contoParziale.add(new BigInteger(xFinale));
					}
				}
				if (arrayFinal[arrayFinal.length - 1][i].trim().equals("*")) {
					if (contoParziale.equals(BigInteger.ZERO)) {
						contoParziale = BigInteger.ONE;
					}
					limite=arrayFinal[j][i].length();

					for (int k = 0; k < limite; k++) {
						String xFinale = "";
						for (int x = 0; x <= arrayFinal.length - 2; x++) {// righe
							System.out.println("X:"+x+" Limite:"+limite+" k:"+k+" i:"+i+"j:"+j);
							String xTemp = arrayFinal[x][i].substring(limite - k-1,
									limite -k);
							if (xTemp.isBlank()) {
								xTemp = "";
							}
							xFinale += xTemp;
						}
						contoParziale = contoParziale.multiply(new BigInteger(xFinale));
					}
				}
				break;
			}
			contoFinale = contoFinale.add(contoParziale);
		}

		return contoFinale;
	}

	public void populateSecondArray(String[] strParts) {
		String [] strParts2 = new String [strParts.length];
		int j=0;
		for(String s:strParts) {
			for(int i=0;i<listaPosizioniDelimitatori.size();i++) {
				s=s.substring(0, listaPosizioniDelimitatori.get(i)-1)+"-"+s.substring(listaPosizioniDelimitatori.get(i));
			}
			strParts2[j]=s;
			j++;
		}
		this.initilizeArray(strParts2);
		Arrays.stream(strParts2).forEach(s -> this.populateArray(s));
		
	}

	public void findDelimitator(String s) {		
		int plus=1;
		int multiply=1;
		int firstPos=0;
		while(plus>=0 || multiply>=0) {
			plus=s.indexOf('+',firstPos);
			if(plus==0) {
				plus=s.indexOf('+',1);
			}
			multiply=s.indexOf('*',firstPos);
			if(multiply==0) {
				multiply=s.indexOf('+',1);
			}
			if(plus>0 && multiply>0)
				firstPos = Math.min(plus, multiply);
			else if(plus>0) {
				firstPos=plus;
			}
			else if(multiply>0) {
				firstPos=multiply;
			}
			else {
				firstPos=0;
			}
			if(firstPos>0) {
				listaPosizioniDelimitatori.add(firstPos);
				firstPos++;
			}
		}
	}

	public void printArray() {

		for (int i = 0; i < arrayFinal.length; i++) { // scorre le righe
			for (int j = 0; j < arrayFinal[i].length; j++) { // scorre le colonne
				System.out.print(arrayFinal[i][j] + " ");
			}
			System.out.println(); // nuova riga per ogni riga
		}

		
	}
	
}
