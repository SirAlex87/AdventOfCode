import java.io.IOException;
import java.util.Arrays;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import service.ServiceTest9;
import util.Utility;

public class Test9 {
	private static final Logger logger = LogManager.getLogger(Test9.class);
	
	public static void main(String[] args) {
		String data = null;
		try {
			//data = Utility.readFromInputStream("prova9Test.txt");
			data = Utility.readFromInputStream("prova9.txt");
			logger.debug(data);
			
			extracted1(data);
			extracted2ThirdVersion(data);
		} catch (IOException e) {
			logger.error(e);
		}
		
	}

	private static void extracted1(String data) {
		ServiceTest9 serviceTest9 = new ServiceTest9();
		String[] strParts = data.split("\n");
		Arrays.stream(strParts).forEach(serviceTest9::populateList);
		serviceTest9.calculateDistance2();
		logger.info("l'area più grande è:{}",serviceTest9.calculateLargerArea2());
	}
	
	private static void extracted2ThirdVersion(String data) {
		ServiceTest9 serviceTest9 = new ServiceTest9();
		String[] strParts = data.split("\n");
		Arrays.stream(strParts).forEach(serviceTest9::populateList);
		serviceTest9.populateListaSimboliV3();
		serviceTest9.completeMapX();
		serviceTest9.calculateArea();
		logger.log(Level.INFO,"l'area più grande è:{}",serviceTest9.calculateLargerArea2());
	}

}
