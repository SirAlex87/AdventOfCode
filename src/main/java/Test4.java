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
		try {
			//data = Utility.readFromInputStream("prova4Test.txt");
			data = Utility.readFromInputStream("prova4.txt");

			logger.debug(data);
			String[] strParts = data.split("\n");
			extracted1(strParts);
			extracted2(strParts);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void extracted1(String[] strParts) {
		ServiceTest4 serviceTest4 = new ServiceTest4();
		Arrays.stream(strParts).filter(Objects::nonNull) // evita NPE
				.map(String::strip) // meglio di trim() per Unicode
				.filter(s -> !s.isBlank()) // filtra vuote e “solo spazi”
				.forEach(serviceTest4::populateList);

		logger.debug("{}", serviceTest4);
		logger.info(serviceTest4.calculateRollsPaperAvailable());
	}

	private static void extracted2(String[] strParts) {
		ServiceTest4 serviceTest4 = new ServiceTest4();
		Arrays.stream(strParts).filter(Objects::nonNull) // evita NPE
				.map(String::strip) // meglio di trim() per Unicode
				.filter(s -> !s.isBlank()) // filtra vuote e “solo spazi”
				.forEach(serviceTest4::populateList);

		logger.debug("{}", serviceTest4);
		int conta = 0;
		int contaTot = 0;
		do {
			conta = serviceTest4.calculateRollsPaperAvailable();
			serviceTest4.updateLista();
			logger.debug("{}", serviceTest4);
			logger.debug(conta);
			contaTot += conta;
		} while (conta > 0);
		logger.info(contaTot);
	}

}
