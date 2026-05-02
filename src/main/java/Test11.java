import java.io.IOException;
import java.util.Arrays;

import service.ServiceTest10;
import service.ServiceTest11;
import util.Utility;

public class Test11 {
	public static void main(String[] args) {
		String data = null;
		try {
			//data = Utility.readFromInputStream("prova11Test.txt");
			data = Utility.readFromInputStream("prova11.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(data);
		

		ServiceTest11 serviceTest11 = new ServiceTest11();
		extracted1(data, serviceTest11);
		//extracted2(data, serviceTest10);
	}

	private static void extracted1(String data, ServiceTest11 serviceTest11) {
		String[] strParts = data.split("\n");
		Arrays.stream(strParts).forEach(s -> serviceTest11.populateList(s));
		serviceTest11.calcolaPathArrivo();
		serviceTest11.calcolaPath();
	}

	private static void extracted2(String data, ServiceTest11 serviceTest11) {
		
	}
	
	

}
