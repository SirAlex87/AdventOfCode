import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import service.ServiceTest10;
import service.ServiceTest11;
import service.ServiceTest12;
import util.Utility;

public class Test12 {
	public static void main(String[] args) {
		String data = null;
		try {
			data = Utility.readFromInputStream("prova12Test.txt");
			//data = Utility.readFromInputStream("prova11TestParte2.txt");
			//data = Utility.readFromInputStream("prova12.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(data);
		

		ServiceTest12 serviceTest12 = new ServiceTest12();
		extracted1(data, serviceTest12);
		//extracted2(data, serviceTest12);
	}
	

	private static void extracted1(String data, ServiceTest12 serviceTest12) {
		String[] strParts = data.split("\n");
		int acc = 0;		
		for (String s : strParts) {
		    acc = serviceTest12.populateList(s, acc);
		}
		System.out.println(serviceTest12.toString());
		serviceTest12.calculateCombination();
	}
		

	private static void extracted2(String data, ServiceTest12 serviceTest12) {
		
	}
	
	

}
