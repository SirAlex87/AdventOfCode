import java.io.IOException;
import java.util.Arrays;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import service.ServiceTest10;
import util.Utility;

public class Test10 {
	
	private static Logger logger = LogManager.getLogger(Test10.class);
	
	public static void main(String[] args) {
		String data = null;
		try {
			data = Utility.readFromInputStream("prova10Test.txt");
			//data = Utility.readFromInputStream("prova10.txt");
			// System.out.println(data);			

			ServiceTest10 serviceTest10 = new ServiceTest10();
			//extracted1(data, serviceTest10);
			extracted2(data, serviceTest10);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private static void extracted1(String data, ServiceTest10 serviceTest10) {
		String[] strParts = data.split("\n");
		Arrays.stream(strParts).forEach(s -> serviceTest10.populateList(s));
		serviceTest10.printMap();
		serviceTest10.controllaStringa();
		serviceTest10.printMap();
		logger.log(Level.INFO,"la somma è:{0}",serviceTest10.sommaMinimi());
	}

	private static void extracted2(String data, ServiceTest10 serviceTest10) {
		String[] strParts = data.split("\n");
		Arrays.stream(strParts).forEach(s -> serviceTest10.populateList(s));
		serviceTest10.printMap();
		//serviceTest10.controllaBottoni();
		serviceTest10.controllaBottoni2(); //valido funziona
		//serviceTest10.controllaBottoni3(); 
		//serviceTest10.printMap();
		logger.log(Level.INFO,"la somma è:{0}",serviceTest10.sommaMinimi());
	}
	
	

}
