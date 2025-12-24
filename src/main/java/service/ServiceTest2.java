package service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ServiceTest2 {

	private String s1;
	private String s2;
	private static List<BigInteger> invalidIds = new ArrayList<>();

	public void countInvalidIds(String s) {
		// System.out.println(s);
		String[] strParts = s.split("-");
		BigInteger a1 = new BigInteger(strParts[0]);
		BigInteger a2 = new BigInteger(strParts[1]);
		for (BigInteger i = a1; i.compareTo(a2) <= 0; i = i.add(BigInteger.ONE)) {
			String a = String.valueOf(i);
			int lenght = a.length();
			if (lenght % 2 == 0) {
				s1 = a.substring(0, lenght / 2);
				s2 = a.substring(lenght / 2);
				if (s1.equals(s2)) {
					System.out.println(s);
					System.out.println(this.toString());
					invalidIds.add(i);
				}
			}
		}
	}
	
	public void countInvalidIds2(String s) {
		// System.out.println(s);
		String[] strParts = s.split("-");
		BigInteger a1 = new BigInteger(strParts[0]);
		BigInteger a2 = new BigInteger(strParts[1]);
		for (BigInteger i = a1; i.compareTo(a2) <= 0; i = i.add(BigInteger.ONE)) {
			String a = String.valueOf(i);
			boolean isInvalid=false;
			int lenght = a.length();
			for (int j=1;j<=lenght/2;j++) {
				String aTemp = a.replace(a.substring(0, j), "");
				if (aTemp.isBlank()) {
					isInvalid=true;
					break;
				}
			}
			if (isInvalid) {
				System.out.println(s);
				System.out.println(a);
				invalidIds.add(i);
			}
		}
	}

	@Override
	public String toString() {
		return "ServiceTest2 [s1=" + s1 + ", s2=" + s2 + ", invalidIds=" + invalidIds + "]";
	}

	public BigInteger sommaInvalidIds() {

		return invalidIds.stream().reduce(BigInteger.ZERO, BigInteger::add);

	}

}
