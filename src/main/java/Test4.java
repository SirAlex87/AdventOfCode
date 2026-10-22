import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import service.ServiceTest4;
import util.Utility;

public class Test4 {
	private static Logger logger = LogManager.getLogger(Test4.class);
	
	public static void main(String[] args) {
		String data = null;
		List<ArrayList<String>> lista = new ArrayList<ArrayList<String>>();
		try {
			//data = Utility.readFromInputStream("prova4Test.txt");
			data = Utility.readFromInputStream("prova4.txt");

			// System.out.println(data);
			String[] strParts = data.split("\n");

			ServiceTest4 serviceTest4 = new ServiceTest4();
			// extracted1(strParts, serviceTest4);
			extracted2(strParts, serviceTest4);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void extracted1(String[] strParts, ServiceTest4 serviceTest4) {
		Arrays.stream(strParts).filter(Objects::nonNull) // evita NPE
				.map(String::strip) // meglio di trim() per Unicode
				.filter(s -> !s.isBlank()) // filtra vuote e “solo spazi”
				.forEach(s -> serviceTest4.populateList(s));

		logger.info(serviceTest4.toString());
		logger.info(serviceTest4.calculateRollsPaperAvailable());

		// System.out.println(serviceTest1.getCount());
	}

	private static void extracted2(String[] strParts, ServiceTest4 serviceTest4) {
		Arrays.stream(strParts).filter(Objects::nonNull) // evita NPE
				.map(String::strip) // meglio di trim() per Unicode
				.filter(s -> !s.isBlank()) // filtra vuote e “solo spazi”
				.forEach(s -> serviceTest4.populateList(s));

		logger.info(serviceTest4.toString());
		int conta = 0;
		int contaTot = 0;
		do {
			conta = serviceTest4.calculateRollsPaperAvailable();
			serviceTest4.updateLista();
			logger.info(serviceTest4.toString());
			logger.info(conta);
			contaTot += conta;
		} while (conta > 0);
		logger.info(contaTot);
	}

}
