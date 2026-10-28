import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javatuples.Pair;

import service.ServiceTest12;
import util.Utility;

public class Test12 {
	private static final Logger logger = LogManager.getLogger(Test12.class);
	
	public static void main(String[] args) {
		String data = null;
		try {
			 //data = Utility.readFromInputStream("prova12Test.txt");
			data = Utility.readFromInputStream("prova12.txt");
			logger.debug(data);
				

				ServiceTest12 serviceTest12 = new ServiceTest12();
				extracted1(data, serviceTest12);
		} catch (IOException e) {
			logger.error(e);
		}
		
	}
	

	private static void extracted1(String data, ServiceTest12 serviceTest12) {
		String[] strParts = data.split("\n");
		Pair<Integer, Integer> acc = new Pair<>(0, 0);		
		for (String s : strParts) {
		    acc = serviceTest12.populateList(s, acc);
		}
		logger.debug(serviceTest12);
		serviceTest12.calculateArea();
		serviceTest12.getRegionsFitted();
	}
	
	

}
