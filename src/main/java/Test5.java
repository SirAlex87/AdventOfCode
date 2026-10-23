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

			logger.debug(data);
			String[] strParts = data.split("\n");

			
			extracted1(strParts);
			extracted2(strParts);
		} catch (IOException e) {
			logger.error(e);
		}
	}

	private static void extracted1(String[] strParts) {
		ServiceTest5 serviceTest5 = new ServiceTest5();
		Arrays.stream(strParts).forEach(serviceTest5::populateList);
		logger.debug("{}",serviceTest5);

		logger.info(serviceTest5.chechAvailableAreFresh());
	}

	private static void extracted2(String[] strParts) {
		ServiceTest5 serviceTest5 = new ServiceTest5();
		Arrays.stream(strParts).forEach(serviceTest5::populateList);
		logger.debug("{}",serviceTest5);

		logger.info(serviceTest5.howManyFresh());
	}

}
