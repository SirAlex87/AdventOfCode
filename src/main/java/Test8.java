import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import service.ServiceTest8;
import util.Utility;

public class Test8 {
	private static Logger logger = LogManager.getLogger(Test8.class);
	
	public static void main(String[] args) {
		String data = null;
		List<ArrayList<String>> lista = new ArrayList<ArrayList<String>>();
		try {
			//data = Utility.readFromInputStream("prova8Test.txt");
			data = Utility.readFromInputStream("prova8.txt");
			// System.out.println(data);
			

			ServiceTest8 serviceTest8 = new ServiceTest8();
			//extracted1(data, serviceTest8);
			extracted2(data, serviceTest8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private static void extracted1(String data, ServiceTest8 serviceTest8) {
		String[] strParts = data.split("\n");
		Arrays.stream(strParts).forEach(s -> serviceTest8.populateList(s));
		
		serviceTest8.calculateDistance();
		logger.info(serviceTest8.toString());
		serviceTest8.printArray();
		//per test
		//serviceTest8.populateCircuito(10);
		serviceTest8.printArray();
		serviceTest8.populateCircuito(1000);
		logger.info(serviceTest8.toStringListaFinale());
		
		logger.info(serviceTest8.multiplySizesLargestCircuits(3));
	}

	private static void extracted2(String data, ServiceTest8 serviceTest8) {
		String[] strParts = data.split("\n");
		Arrays.stream(strParts).forEach(s -> serviceTest8.populateList(s));
		
		serviceTest8.calculateDistance();
		logger.info(serviceTest8.toString());
		serviceTest8.printArray();
		serviceTest8.printArray();
		serviceTest8.populateCircuito();
		logger.info(serviceTest8.toStringListaFinale());
		
		logger.info(serviceTest8.multiplyLastCoordinates());
	}

}
