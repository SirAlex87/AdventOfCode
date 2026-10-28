import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import service.ServiceTest9;
import util.Utility;

public class Test9 {
	private static Logger logger = LogManager.getLogger(Test9.class);
	
	public static void main(String[] args) {
		String data = null;
		List<ArrayList<String>> lista = new ArrayList<ArrayList<String>>();
		try {
			data = Utility.readFromInputStream("prova9Test.txt");
			//data = Utility.readFromInputStream("prova9.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(data);
		

		ServiceTest9 serviceTest9 = new ServiceTest9();
		extracted1(data, serviceTest9);
		//extracted2ThirdVersion(data, serviceTest9);
	}

	private static void extracted1(String data, ServiceTest9 serviceTest9) {
		String[] strParts = data.split("\n");
		Arrays.stream(strParts).forEach(s -> serviceTest9.populateList(s));
		serviceTest9.calculateDistance2();
		logger.info("l'area più grande è:"+serviceTest9.calculateLargerArea2());
	}

	private static void extracted2FistVersion(String data, ServiceTest9 serviceTest9) {
		String[] strParts = data.split("\n");
		Arrays.stream(strParts).forEach(s -> serviceTest9.populateList(s));
		serviceTest9.populateArray();
		serviceTest9.printArray();
		logger.info("\n");
		serviceTest9.greenTilesHorizonatals();
		serviceTest9.greenTilesVerticals();
		serviceTest9.printArray();
		logger.info("\n");
		serviceTest9.greenTilesHorizonatalsCompleteX();
		serviceTest9.printArray();
		serviceTest9.calculateDistance3();
		logger.log(Level.INFO,"l'area più grande è:{0}",serviceTest9.calculateLargerArea2());
	}
	
	private static void extracted2SecondVersion(String data, ServiceTest9 serviceTest9) {
		String[] strParts = data.split("\n");
		Arrays.stream(strParts).forEach(s -> serviceTest9.populateList(s));
		serviceTest9.populateListaSimboli();
		//serviceTest9.printListaSimboli();
		logger.info("\n");
		serviceTest9.greenTilesHorizonatalsV2();
		serviceTest9.printListaSimboli();
		serviceTest9.greenTilesVerticalsV2();
		serviceTest9.printListaSimboli();
		logger.info("\n");
		serviceTest9.greenTilesHorizonatalsCompleteXV2();
		serviceTest9.printListaSimboli();
		serviceTest9.calculateDistance4();
		logger.log(Level.INFO,"l'area più grande è:{0}",serviceTest9.calculateLargerArea2());
	}
	
	private static void extracted2ThirdVersion(String data, ServiceTest9 serviceTest9) {
		String[] strParts = data.split("\n");
		Arrays.stream(strParts).forEach(s -> serviceTest9.populateList(s));
		serviceTest9.populateListaSimboliV3();
		serviceTest9.completeMapX();
		serviceTest9.calculateArea();
		logger.log(Level.INFO,"l'area più grande è:{0}",serviceTest9.calculateLargerArea2());
	}

}
