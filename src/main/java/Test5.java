import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import service.ServiceTest5;
import util.Utility;

public class Test5 {
	private static Logger logger = LogManager.getLogger(Test5.class);
	
	public static void main(String[] args) {
		String data = null;
		List<ArrayList<String>> lista = new ArrayList<ArrayList<String>>();
		try {
			//data = Utility.readFromInputStream("prova5Test.txt");
			data = Utility.readFromInputStream("prova5.txt");

			// System.out.println(data);
			String[] strParts = data.split("\n");

			ServiceTest5 serviceTest5 = new ServiceTest5();
			//extracted1(strParts, serviceTest5);
			extracted2(strParts, serviceTest5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void extracted1(String[] strParts, ServiceTest5 serviceTest5) {
		Arrays.stream(strParts).forEach(s -> serviceTest5.populateList(s));
		logger.info(serviceTest5.toString());

		logger.info(serviceTest5.chechAvailableAreFresh());

		// System.out.println(serviceTest1.getCount());
	}

	private static void extracted2(String[] strParts, ServiceTest5 serviceTest5) {
		Arrays.stream(strParts).forEach(s -> serviceTest5.populateList(s));
		logger.info(serviceTest5.toString());

		logger.info(serviceTest5.howManyFresh());
	}

}
