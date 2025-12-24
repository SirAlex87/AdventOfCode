import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import service.ServiceTest2;
import service.ServiceTest3;
import util.Utility;

public class Test3 {
	public static void main(String[] args) {
		String data = null;
		try {
			//data = Utility.readFromInputStream("prova3Test.txt");
			data = Utility.readFromInputStream("prova3.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(data);
		String[] strParts = data.split("\n");	

		
		ServiceTest3 serviceTest3 = new ServiceTest3();
		//extracted1(strParts, serviceTest3);
		extracted2(strParts, serviceTest3);
	}

	private static void extracted1(String[] strParts, ServiceTest3 serviceTest3) {
		Arrays.stream(strParts).filter(Objects::nonNull) // evita NPE
		.map(String::strip) // meglio di trim() per Unicode
		.filter(s -> !s.isBlank()) // filtra vuote e “solo spazi”
		.forEach(s -> serviceTest3.findMax(s));		
		
		System.out.println(serviceTest3.getVoltage());
		System.out.println(serviceTest3.sommaVoltage());

		// System.out.println(serviceTest1.getCount());
	}

	private static void extracted2(String[] strParts, ServiceTest3 serviceTest3) {
		Arrays.stream(strParts).filter(Objects::nonNull) // evita NPE
		.map(String::strip) // meglio di trim() per Unicode
		.filter(s -> !s.isBlank()) // filtra vuote e “solo spazi”
		.forEach(s -> serviceTest3.findMax2(s));		
		
		System.out.println(serviceTest3.getVoltage());
		System.out.println(serviceTest3.sommaVoltage());
	}

}
