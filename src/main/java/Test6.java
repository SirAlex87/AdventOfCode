import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import service.ServiceTest6;
import util.Utility;

public class Test6 {
	private static Logger logger = LogManager.getLogger(Test6.class);
	
	public static void main(String[] args) {
		String data = null;
		List<ArrayList<String>> lista = new ArrayList<ArrayList<String>>();
		try {
			//data = Utility.readFromInputStream("prova6Test.txt");
			data = Utility.readFromInputStream("prova6.txt");
			// System.out.println(data);
			

			ServiceTest6 serviceTest6 = new ServiceTest6();
			//extracted1(data, serviceTest6);
			extracted2(data, serviceTest6);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void extracted1(String data, ServiceTest6 serviceTest6) {
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
		Arrays.stream(strParts2).forEach(s -> serviceTest6.populateArray(s));
		logger.info(serviceTest6.calculate());

		//System.out.println(serviceTest6.chechAvailableAreFresh());

		// System.out.println(serviceTest1.getCount());
	}

	private static void extracted2(String data, ServiceTest6 serviceTest6) {
		String[] strParts = data.split("\n");
		serviceTest6.findDelimitator(strParts[strParts.length-1]);
		serviceTest6.populateSecondArray(strParts);
		serviceTest6.printArray();
		logger.info(serviceTest6.calculate2());
	}

}
