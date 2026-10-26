import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import service.ServiceTest7;
import util.Utility;

public class Test7 {
	private static final Logger logger = LogManager.getLogger(Test7.class);
	
	public static void main(String[] args) {
		String data = null;
		try {
			//data = Utility.readFromInputStream("prova7Test.txt");
			data = Utility.readFromInputStream("prova7.txt");
			logger.debug(data);
			

			ServiceTest7 serviceTest7 = new ServiceTest7();
			int indiceStart=extracted1(data, serviceTest7);
			extracted2(serviceTest7,indiceStart);
		} catch (IOException e) {
			logger.error(e);
		}
		
	}

	private static int extracted1(String data, ServiceTest7 serviceTest7) {
		String[] strParts = data.split("\n");		

		serviceTest7.initilizeArray(strParts);
		Arrays.stream(strParts).forEach(serviceTest7::populateArray);
		serviceTest7.printArray();
		int indiceStart=strParts[0].indexOf("S");
		serviceTest7.calculateSplit(0, indiceStart);
		logger.info(serviceTest7.getConta());
		serviceTest7.printArray();
		return indiceStart;
	}

	private static void extracted2(ServiceTest7 serviceTest7, int indiceStart) {
		serviceTest7.calculateTimelines(indiceStart);
		logger.debug(serviceTest7);
		logger.info(serviceTest7.sumMap());
	}

}
