import java.io.IOException;
import java.util.Arrays;

import service.ServiceTest10;
import util.Utility;

public class Test10 {
	public static void main(String[] args) {
		String data = null;
		try {
			data = Utility.readFromInputStream("prova10Test.txt");
			//data = Utility.readFromInputStream("prova10.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(data);
		

		ServiceTest10 serviceTest10 = new ServiceTest10();
		extracted1(data, serviceTest10);
		//extracted2(data, serviceTest10);
	}

	private static void extracted1(String data, ServiceTest10 serviceTest10) {
		String[] strParts = data.split("\n");
		Arrays.stream(strParts).forEach(s -> serviceTest10.populateList(s));
	}

	private static void extracted2(String data, ServiceTest10 serviceTest10) {
		
	}
	
	

}
