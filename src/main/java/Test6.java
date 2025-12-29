import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import service.ServiceTest2;
import service.ServiceTest3;
import service.ServiceTest4;
import service.ServiceTest5;
import service.ServiceTest6;
import util.Utility;

public class Test6 {
	public static void main(String[] args) {
		String data = null;
		List<ArrayList<String>> lista = new ArrayList<ArrayList<String>>();
		try {
			//data = Utility.readFromInputStream("prova6Test.txt");
			data = Utility.readFromInputStream("prova6.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(data);
		

		ServiceTest6 serviceTest6 = new ServiceTest6();
		//extracted1(data, serviceTest6);
		extracted2(data, serviceTest6);
	}

	private static void extracted1(String data, ServiceTest6 serviceTest6) {
		while(data.contains("  ")) {
			data=data.replace("  ", " ");
		}
		data=data.replace(" ", "-");
		String[] strParts = data.split("\n");

		String[] strParts2 = Arrays.stream(strParts).map(s -> {
			String t = s;
			// leading '-'
			if (t.startsWith("-")) {
				t = t.substring(1);
			}
			// trailing '-'
			if (t.endsWith("-")) {
				t = t.substring(0, t.length() - 1);
			}
			return t;
		}).toArray(String[]::new);

		serviceTest6.initilizeArray(strParts2);
		Arrays.stream(strParts2).forEach(s -> serviceTest6.populateArray(s));
		System.out.println(serviceTest6.calculate());

		//System.out.println(serviceTest6.chechAvailableAreFresh());

		// System.out.println(serviceTest1.getCount());
	}

	private static void extracted2(String data, ServiceTest6 serviceTest6) {
		String[] strParts = data.split("\n");
		serviceTest6.findDelimitator(strParts[strParts.length-1]);
		serviceTest6.populateSecondArray(strParts);
		serviceTest6.printArray();
		System.out.println(serviceTest6.calculate2());
	}

}
