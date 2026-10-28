import java.io.IOException;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import service.ServiceTest8;
import util.Utility;

public class Test8 {
	private static final Logger logger = LogManager.getLogger(Test8.class);
	
	public static void main(String[] args) {
		String data = null;
		try {
			//data = Utility.readFromInputStream("prova8Test.txt");
			data = Utility.readFromInputStream("prova8.txt");
			logger.debug(data);
			
			extracted1(data);//69192
			extracted2(data);//7264308110
		} catch (IOException e) {
			logger.error(e);
		}
		
	}

	private static void extracted1(String data) {
		ServiceTest8 serviceTest8 = new ServiceTest8();
		String[] strParts = data.split("\n");
		Arrays.stream(strParts).forEach(serviceTest8::populateList);
		
		serviceTest8.calculateDistance();
		logger.debug(serviceTest8);
		serviceTest8.printArray();
		serviceTest8.populateCircuito(1000);
		if (logger.isDebugEnabled()) {
			logger.debug(serviceTest8.toStringListaFinale());
		}
		
		logger.info(serviceTest8.multiplySizesLargestCircuits(3));
	}

	private static void extracted2(String data) {
		ServiceTest8 serviceTest8 = new ServiceTest8();
		String[] strParts = data.split("\n");
		Arrays.stream(strParts).forEach(serviceTest8::populateList);
		logger.info("calculateDistance");
		serviceTest8.calculateDistance();
		logger.debug(serviceTest8);
		serviceTest8.printArray();
		logger.info("populateCircuito");
		serviceTest8.populateCircuito();
		if (logger.isDebugEnabled()) {
			logger.debug(serviceTest8.toStringListaFinale());
		}
		
		logger.info(serviceTest8.multiplyLastCoordinates());
	}

}
