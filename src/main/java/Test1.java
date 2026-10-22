import java.io.IOException;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import service.ServiceTest1;
import util.Utility;

public class Test1 {

	private static final Logger logger = LogManager.getLogger(Test1.class);
	
	public static void main(String[] args) {
		String data = null;
		try {
			Test1 test1=new Test1();
			//data = Utility.readFromInputStream("prova1Test.txt");
			data = Utility.readFromInputStream("prova1.txt");
			//System.out.println(data);
			String[] strParts = data.split("\n");
			test1.extracted1(strParts);
			test1.extracted2(strParts);
		} catch (IOException e) {
			logger.error(e);
		}
		
	}

	private void extracted1(String[] strParts) {
		ServiceTest1 service = new ServiceTest1();
		Arrays.stream(strParts).forEach(service::countPassingToZero);
		logger.info(service.getCount());
	}
	
	private void extracted2(String[] strParts) {
		ServiceTest1 service = new ServiceTest1();
		Arrays.stream(strParts).forEach(service::countTimesPassingToZero);
		logger.info(service.getCount());
	}

}
