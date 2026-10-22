import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javatuples.Pair;

import service.ServiceTest12;
import util.Utility;

public class Test12 {
	private static Logger logger = LogManager.getLogger(Test12.class);
	
	public static void main(String[] args) {
		String data = null;
		try {
			 data = Utility.readFromInputStream("prova12Test.txt");
			//data = Utility.readFromInputStream("prova12.txt");
			// System.out.println(data);
				

				ServiceTest12 serviceTest12 = new ServiceTest12();
				extracted1(data, serviceTest12);
				//extracted2(data, serviceTest12);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

	private static void extracted1(String data, ServiceTest12 serviceTest12) {
		String[] strParts = data.split("\n");
		Pair<Integer, Integer> acc = new Pair<>(0, 0);		
		for (String s : strParts) {
		    acc = serviceTest12.populateList(s, acc);
		}
		logger.info(serviceTest12.toString());
		//serviceTest12.calculateArea();
		serviceTest12.calculateCombination();
		serviceTest12.getRegionsFitted();
	}
	
	

}
