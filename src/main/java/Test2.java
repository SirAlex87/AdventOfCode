import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import service.ServiceTest2;
import util.Utility;

public class Test2 {
	private static final Logger logger = LogManager.getLogger(Test2.class);
	
	public static void main(String[] args) {
		String data = null;
		try {
			//data = Utility.readFromInputStream("prova2Test.txt");
			data = Utility.readFromInputStream("prova2.txt");
			logger.debug(data);
			String[] strParts = data.split(",");			
			extracted1(strParts);
			extracted2(strParts);
		} catch (IOException e) {
			logger.error(e);
		}
		
	}

	private static void extracted1(String[] strParts) {
		ServiceTest2 serviceTest2 = new ServiceTest2();
		Arrays.stream(strParts).filter(Objects::nonNull) // evita NPE
				.map(String::strip) // meglio di trim() per Unicode
				.filter(s -> !s.isBlank()) // filtra vuote e “solo spazi”
				.forEach(serviceTest2::countInvalidIds);
		logger.info(serviceTest2.sommaInvalidIds());
	}

	private static void extracted2(String[] strParts) {
		ServiceTest2 serviceTest2 = new ServiceTest2();
		serviceTest2.pulisciLista();
		Arrays.stream(strParts).filter(Objects::nonNull) // evita NPE
				.map(String::strip) // meglio di trim() per Unicode
				.filter(s -> !s.isBlank()) // filtra vuote e “solo spazi”
				.forEach(serviceTest2::countInvalidIds2);
		logger.info(serviceTest2.sommaInvalidIds());
	}

}
