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
import util.Utility;

public class Test7 {
	public static void main(String[] args) {
		String data = null;
		List<ArrayList<String>> lista = new ArrayList<ArrayList<String>>();
		try {
			//data = Utility.readFromInputStream("prova7Test.txt");
			data = Utility.readFromInputStream("prova7.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(data);
		

		ServiceTest7 serviceTest7 = new ServiceTest7();
		int indiceStart=extracted1(data, serviceTest7);
		extracted2(serviceTest7,indiceStart);
	}

	private static int extracted1(String data, ServiceTest7 serviceTest7) {
		String[] strParts = data.split("\n");		

		serviceTest7.initilizeArray(strParts);
		Arrays.stream(strParts).forEach(s -> serviceTest7.populateArray(s));
		serviceTest7.printArray();
		int indiceStart=strParts[0].indexOf("S");
		serviceTest7.calculateSplit(0, indiceStart);
		System.out.println(serviceTest7.getConta());
		serviceTest7.printArray();
		return indiceStart;
	}

	private static void extracted2(ServiceTest7 serviceTest7, int indiceStart) {
		serviceTest7.calculateTimelines(indiceStart);
		System.out.println(serviceTest7.toString());
		System.out.println(serviceTest7.sumMap());
	}

}
