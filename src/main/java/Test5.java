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
import util.Utility;

public class Test5 {
	public static void main(String[] args) {
		String data = null;
		List<ArrayList<String>> lista = new ArrayList<ArrayList<String>>();
		try {
			//data = Utility.readFromInputStream("prova5Test.txt");
			data = Utility.readFromInputStream("prova5.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(data);
		String[] strParts = data.split("\n");

		ServiceTest5 serviceTest5 = new ServiceTest5();
		extracted1(strParts, serviceTest5);
		//extracted2(strParts, serviceTest4);
	}

	private static void extracted1(String[] strParts, ServiceTest5 serviceTest5) {
		Arrays.stream(strParts)
				.forEach(s -> serviceTest5.populateList(s));
		System.out.println(serviceTest5.toString());

		System.out.println(serviceTest5.chechAvailableAreFresh());

		// System.out.println(serviceTest1.getCount());
	}

//	private static void extracted2(String[] strParts, ServiceTest5 serviceTest5) {
//		Arrays.stream(strParts).filter(Objects::nonNull) // evita NPE
//				.map(String::strip) // meglio di trim() per Unicode
//				.filter(s -> !s.isBlank()) // filtra vuote e “solo spazi”
//				.forEach(s -> serviceTest4.populateList(s));
//
//		System.out.println(serviceTest4.toString());
//		int conta = 0;
//		int contaTot = 0;
//		do {
//			conta = serviceTest4.calculateRollsPaperAvailable();
//			serviceTest4.updateLista();
//			System.out.println(serviceTest4.toString());
//			System.out.println(conta);
//			contaTot += conta;
//		} while (conta > 0);
//		System.out.println(contaTot);
//	}

}
