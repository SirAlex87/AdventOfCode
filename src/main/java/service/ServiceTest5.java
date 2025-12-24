package service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ServiceTest5 {

	private List<String> rangeFresh = new ArrayList<String>();
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
			rangeFresh.add(s);			
		}
	}

	public int chechAvailableAreFresh()
	{

		int countDistinct = (int) listaAvailable.stream().distinct() // considera ogni valore una sola volta
				.filter(x->isPresent(x)) // presente nell'altra lista
				.count();
		return countDistinct;
	}
	
	

	private boolean isPresent(BigInteger x) {
		boolean check=false;
		for (int i=0;i<rangeFresh.size();i++) {
			String[] splitArray = rangeFresh.get(i).split("-");
			BigInteger start = new BigInteger(splitArray[0]);
			BigInteger end = new BigInteger(splitArray[1]);
			check=x.compareTo(start) >= 0 && x.compareTo(end) <= 0;
			if(check) {
				break;
			}
		}
		return check;
	}

	@Override
	public String toString() {
		return "ServiceTest5 [rangeFresh=" + rangeFresh + ", listaAvailable=" + listaAvailable + ", startAvailable="
				+ startAvailable + "]";
	}
}
