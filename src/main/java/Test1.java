import java.io.IOException;
import java.util.Arrays;

import service.ServiceTest1;
import util.Utility;

public class Test1 {

	public static void main(String[] args) {
		String data = null;
		try {
			//data = Utility.readFromInputStream("prova1Test.txt");
			data = Utility.readFromInputStream("prova1.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(data);
		String[] strParts = data.split("\n");
		ServiceTest1 serviceTest1 = new ServiceTest1();
		//extracted1(strParts, serviceTest1);
		extracted2(strParts, serviceTest1);
	}

	private static void extracted1(String[] strParts, ServiceTest1 serviceTest1) {
		Arrays.stream(strParts).forEach(s -> serviceTest1.countPassingToZero(s));
		System.out.println(serviceTest1.getCount());
	}
	
	private static void extracted2(String[] strParts, ServiceTest1 serviceTest1) {
		Arrays.stream(strParts).forEach(s -> serviceTest1.countTimesPassingToZero(s));
		System.out.println(serviceTest1.getCount());
	}

}
