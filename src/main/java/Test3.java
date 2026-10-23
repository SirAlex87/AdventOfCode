import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import service.ServiceTest3;
import util.Utility;

public class Test3 {
	private static final Logger logger = LogManager.getLogger(Test3.class);
	
	public static void main(String[] args) {
		String data = null;
		try {
			//data = Utility.readFromInputStream("prova3Test.txt");
			data = Utility.readFromInputStream("prova3.txt");

			logger.debug(data);
			String[] strParts = data.split("\n");	

			
			ServiceTest3 serviceTest3 = new ServiceTest3();
			extracted1(strParts, serviceTest3);
			extracted2(strParts, serviceTest3);
		} catch (IOException e) {
			logger.error(e);
		}
	}

	private static void extracted1(String[] strParts, ServiceTest3 serviceTest3) {
		Arrays.stream(strParts).filter(Objects::nonNull) // evita NPE
		.map(String::strip) // meglio di trim() per Unicode
		.filter(s -> !s.isBlank()) // filtra vuote e “solo spazi”
		.forEach(serviceTest3::findMax);		
		
		logger.debug(ServiceTest3.getVoltage());
		logger.info(serviceTest3.sommaVoltage());
	}

	private static void extracted2(String[] strParts, ServiceTest3 serviceTest3) {
		serviceTest3.clearVoltage();
		Arrays.stream(strParts).filter(Objects::nonNull) // evita NPE
		.map(String::strip) // meglio di trim() per Unicode
		.filter(s -> !s.isBlank()) // filtra vuote e “solo spazi”
		.forEach(serviceTest3::findMax2);		
		
		logger.debug(ServiceTest3.getVoltage());
		logger.info(serviceTest3.sommaVoltage());
	}

}
