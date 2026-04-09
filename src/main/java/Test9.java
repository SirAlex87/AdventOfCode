import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import service.ServiceTest2;
import service.ServiceTest3;
import service.ServiceTest4;
import service.ServiceTest5;
import service.ServiceTest6;
import service.ServiceTest7;
import service.ServiceTest8;
import service.ServiceTest9;
import util.Utility;

public class Test9 {
	public static void main(String[] args) {
		String data = null;
		List<ArrayList<String>> lista = new ArrayList<ArrayList<String>>();
		try {
			//data = Utility.readFromInputStream("prova9Test.txt");
			data = Utility.readFromInputStream("prova9.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(data);
		

		ServiceTest9 serviceTest9 = new ServiceTest9();
		//extracted1(data, serviceTest9);
		extracted2ThirdVersion(data, serviceTest9);
	}

	private static void extracted1(String data, ServiceTest9 serviceTest9) {
		String[] strParts = data.split("\n");
		Arrays.stream(strParts).forEach(s -> serviceTest9.populateList(s));
		serviceTest9.calculateDistance2();
		System.out.println("l'area più grande è:"+serviceTest9.calculateLargerArea2());
	}

	private static void extracted2FistVersion(String data, ServiceTest9 serviceTest9) {
		String[] strParts = data.split("\n");
		Arrays.stream(strParts).forEach(s -> serviceTest9.populateList(s));
		serviceTest9.populateArray();
		serviceTest9.printArray();
		System.out.println();
		serviceTest9.greenTilesHorizonatals();
		serviceTest9.greenTilesVerticals();
		serviceTest9.printArray();
		System.out.println();
		serviceTest9.greenTilesHorizonatalsCompleteX();
		serviceTest9.printArray();
		serviceTest9.calculateDistance3();
		System.out.println("l'area più grande è:"+serviceTest9.calculateLargerArea2());
	}
	
	private static void extracted2SecondVersion(String data, ServiceTest9 serviceTest9) {
		String[] strParts = data.split("\n");
		Arrays.stream(strParts).forEach(s -> serviceTest9.populateList(s));
		serviceTest9.populateListaSimboli();
		//serviceTest9.printListaSimboli();
		System.out.println();
		serviceTest9.greenTilesHorizonatalsV2();
		serviceTest9.printListaSimboli();
		serviceTest9.greenTilesVerticalsV2();
		serviceTest9.printListaSimboli();
		System.out.println();
		serviceTest9.greenTilesHorizonatalsCompleteXV2();
		serviceTest9.printListaSimboli();
		serviceTest9.calculateDistance4();
		System.out.println("l'area più grande è:"+serviceTest9.calculateLargerArea2());
	}
	
	private static void extracted2ThirdVersion(String data, ServiceTest9 serviceTest9) {
		String[] strParts = data.split("\n");
		Arrays.stream(strParts).forEach(s -> serviceTest9.populateList(s));
		serviceTest9.populateListaSimboliV3();
		serviceTest9.completeMapX();
		serviceTest9.calculateArea();
		System.out.println("l'area più grande è:"+serviceTest9.calculateLargerArea2());
	}

}
