import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import service.ServiceTest2;
import util.Utility;

public class Test2 {
	public static void main(String[] args) {
		String data = null;
		try {
			//data = Utility.readFromInputStream("prova2Test.txt");
			data = Utility.readFromInputStream("prova2.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(data);
		String[] strParts = data.split(",");
		ServiceTest2 serviceTest2 = new ServiceTest2();
		// extracted1(strParts, serviceTest2);
		extracted2(strParts, serviceTest2);
	}

	private static void extracted1(String[] strParts, ServiceTest2 serviceTest2) {

		Arrays.stream(strParts).filter(Objects::nonNull) // evita NPE
				.map(String::strip) // meglio di trim() per Unicode
				.filter(s -> !s.isBlank()) // filtra vuote e “solo spazi”
				.forEach(s -> serviceTest2.countInvalidIds(s));
		System.out.println(serviceTest2.sommaInvalidIds());

		// System.out.println(serviceTest1.getCount());
	}

	private static void extracted2(String[] strParts, ServiceTest2 serviceTest2) {
		Arrays.stream(strParts).filter(Objects::nonNull) // evita NPE
				.map(String::strip) // meglio di trim() per Unicode
				.filter(s -> !s.isBlank()) // filtra vuote e “solo spazi”
				.forEach(s -> serviceTest2.countInvalidIds2(s));
		System.out.println(serviceTest2.sommaInvalidIds());
	}

}
