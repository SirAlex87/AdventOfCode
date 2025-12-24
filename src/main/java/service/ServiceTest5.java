package service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ServiceTest5 {

	private List<BigInteger> listaFresh = new ArrayList<BigInteger>();
	private List<BigInteger> listaAvailable = new ArrayList<BigInteger>();
	private boolean startAvailable;

	public void populateList(String s) {
		if (s.isEmpty()) {
			startAvailable = true;
		}
		if (startAvailable) {
			if (!s.isEmpty())
				listaAvailable.add(new BigInteger(s));
		} else {
			String[] splitArray = s.split("-");
			BigInteger start = new BigInteger(splitArray[0]);
			BigInteger end = new BigInteger(splitArray[1]);

			for (BigInteger i = start; i.compareTo(end) <= 0; // i <= end
					i = i.add(BigInteger.ONE)) {
				listaFresh.add(i);
			}
		}
	}

	public int chechAvailableAreFresh()
	{

		int countDistinct = (int) listaAvailable.stream().distinct() // considera ogni valore una sola volta
				.filter(listaFresh::contains) // presente nell'altra lista
				.count();
		return countDistinct;
	}

	@Override
	public String toString() {
		return "ServiceTest5 [listaFresh=" + listaFresh + ", listaAvailable=" + listaAvailable + ", startAvailable="
				+ startAvailable + "]";
	}
}
