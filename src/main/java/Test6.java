import java.io.IOException;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import service.ServiceTest6;
import util.Utility;

public class Test6 {
	private static final Logger logger = LogManager.getLogger(Test6.class);
	
	public static void main(String[] args) {
		String data = null;
		try {
			//data = Utility.readFromInputStream("prova6Test.txt");
			data = Utility.readFromInputStream("prova6.txt");
			logger.debug(data);
			
			extracted1(data);
			extracted2(data);
		} catch (IOException e) {
			logger.error(e);
		}
		
	}

	private static void extracted1(String data) {
		ServiceTest6 serviceTest6 = new ServiceTest6();
		while(data.contains("  ")) {
			data=data.replace("  ", " ");
		}
		data=data.replace(" ", "-");
		String[] strParts = data.split("\n");

		String[] strParts2 = Arrays.stream(strParts).map(s -> {
			String t = s;
			// leading '-'
			if (t.startsWith("-")) {
				t = t.substring(1);
			}
			// trailing '-'
			if (t.endsWith("-")) {
				t = t.substring(0, t.length() - 1);
			}
			return t;
		}).toArray(String[]::new);

		serviceTest6.initilizeArray(strParts2);
		Arrays.stream(strParts2).forEach(serviceTest6::populateArray);
		logger.info(serviceTest6.calculate());
	}

	private static void extracted2(String data) {
		ServiceTest6 serviceTest6 = new ServiceTest6();
		String[] strParts = data.split("\n");
		serviceTest6.findDelimitator(strParts[strParts.length-1]);
		serviceTest6.populateSecondArray(strParts);
		serviceTest6.printArray();
		logger.info(serviceTest6.calculate2());
	}

}
