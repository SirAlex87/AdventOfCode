package service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ServiceTest3 {

	private static List<BigInteger> voltage = new ArrayList<>();
	private List<Integer> listaVoltage = new ArrayList<>();

	public void setVoltage() {
		String convertToBigInteger = 
				listaVoltage.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());

		System.out.println(convertToBigInteger);
		voltage.add(new BigInteger(convertToBigInteger));
		listaVoltage.clear();
	}

	public BigInteger sommaVoltage() {

		return voltage.stream().reduce(BigInteger.ZERO, BigInteger::add);

	}

	public static List<BigInteger> getVoltage() {
		return voltage;
	}

	public static void setVoltage(List<BigInteger> voltage) {
		ServiceTest3.voltage = voltage;
	}

	public void findMax(String s) {
		System.out.println(s);
		int[] arrayParts = new int[s.length()];

		for (int i = 0; i < s.length(); i++) {
			arrayParts[i] = Integer.parseInt(s.substring(i, i + 1));
		}

		int[] arrayParts1 = arrayParts.clone();
		arrayParts1[arrayParts1.length - 1] = 0;

		OptionalInt firstMax = Arrays.stream(arrayParts1).max();
		listaVoltage.add(firstMax.getAsInt());
		int firstPosition = IntStream.range(0, arrayParts1.length).filter(i -> arrayParts1[i] == firstMax.getAsInt())
				.findFirst().orElse(-1);
		

		int[] arrayParts2 = arrayParts.clone();
		for (int i = 0; i <= firstPosition; i++) {
			arrayParts2[i] = 0;
		}

		OptionalInt secondMax = Arrays.stream(arrayParts2).max();
		listaVoltage.add(secondMax.getAsInt());

		this.setVoltage();
	}
	
	public void findMax2(String s) {
		System.out.println(s);
		int[] arrayParts = new int[s.length()];

		for (int i = 0; i < s.length(); i++) {
			arrayParts[i] = Integer.parseInt(s.substring(i, i + 1));
		}
		int firstPosition=-1;
		for(int j =0;j<12;j++) {
			int[] arrayParts1 = arrayParts.clone();
			for(int i =arrayParts.length-1;i>arrayParts.length-12+j;i--) {
				arrayParts1[i] = 0;
			}
			for(int i =0;i<=firstPosition;i++) {
				arrayParts1[i] = 0;
			}
			OptionalInt firstMax = Arrays.stream(arrayParts1).max();
			firstPosition = IntStream.range(0, arrayParts1.length).filter(i -> arrayParts1[i] == firstMax.getAsInt())
					.findFirst().orElse(-1);
			listaVoltage.add(firstMax.getAsInt());

		}

		this.setVoltage();
	}

}
