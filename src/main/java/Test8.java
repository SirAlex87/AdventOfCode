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
import service.ServiceTest7;
import service.ServiceTest8;
import util.Utility;

public class Test8 {
	public static void main(String[] args) {
		String data = null;
		List<ArrayList<String>> lista = new ArrayList<ArrayList<String>>();
		try {
			//data = Utility.readFromInputStream("prova8Test.txt");
			data = Utility.readFromInputStream("prova8.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(data);
		

		ServiceTest8 serviceTest8 = new ServiceTest8();
		//extracted1(data, serviceTest8);
		extracted2(data, serviceTest8);
	}

	private static void extracted1(String data, ServiceTest8 serviceTest8) {
		String[] strParts = data.split("\n");
		Arrays.stream(strParts).forEach(s -> serviceTest8.populateList(s));
		
		serviceTest8.calculateDistance();
		System.out.println(serviceTest8.toString());
		serviceTest8.printArray();
		//per test
		//serviceTest8.populateCircuito(10);
		serviceTest8.printArray();
		serviceTest8.populateCircuito(1000);
		System.out.println(serviceTest8.toStringListaFinale());
		
		System.out.println(serviceTest8.multiplySizesLargestCircuits(3));
	}

	private static void extracted2(String data, ServiceTest8 serviceTest8) {
		String[] strParts = data.split("\n");
		Arrays.stream(strParts).forEach(s -> serviceTest8.populateList(s));
		
		serviceTest8.calculateDistance();
		System.out.println(serviceTest8.toString());
		serviceTest8.printArray();
		serviceTest8.printArray();
		serviceTest8.populateCircuito();
		System.out.println(serviceTest8.toStringListaFinale());
		
		System.out.println(serviceTest8.multiplyLastCoordinates());
	}

}
