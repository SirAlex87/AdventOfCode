import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import service.ServiceTest10;
import service.ServiceTest11;
import util.Utility;

public class Test11 {
	public static void main(String[] args) {
		String data = null;
		try {
			//data = Utility.readFromInputStream("prova11Test.txt");
			//data = Utility.readFromInputStream("prova11TestParte2.txt");
			data = Utility.readFromInputStream("prova11.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(data);
		

		ServiceTest11 serviceTest11 = new ServiceTest11();
		//extracted1(data, serviceTest11, "you");
		extracted2(data, serviceTest11,"svr");
	}

	private static void extracted1(String data, ServiceTest11 serviceTest11, String partenza) {
		String[] strParts = data.split("\n");
		Arrays.stream(strParts).forEach(s -> serviceTest11.populateList(s));
		serviceTest11.calcolaPathArrivo("out");
		List<List<String>> results=serviceTest11.calcolaPath(partenza);
		System.out.println("tutti i path sono da "+partenza+" a out sono: "+results.size());
	}

	private static void extracted2(String data, ServiceTest11 serviceTest11, String partenza) {
		String[] strParts = data.split("\n");
		Arrays.stream(strParts).forEach(s -> serviceTest11.populateList(s));
		//da srv a fft
		serviceTest11.calcolaPathArrivo("fft");
		List<List<String>> resultsfft=serviceTest11.calcolaPath(partenza);
		System.out.println("tutti i path sono da "+partenza+" a fft sono: "+resultsfft.size());
		//da srv a dac
		serviceTest11.calcolaPathArrivo("dac");
		List<List<String>> resultsDac=serviceTest11.calcolaPath(partenza);
		System.out.println("tutti i path sono da "+partenza+" a dac sono: "+resultsDac.size());
		BigInteger path_srv_dac_fft_out=BigInteger.ZERO;
		BigInteger path_srv_fft_dac_out=BigInteger.ZERO;
		if(!resultsfft.isEmpty()) {
			//da fft a dac
			partenza="fft";
			serviceTest11.calcolaPathArrivo("dac");
			List<List<String>> resultsfftToDac=serviceTest11.calcolaPath(partenza);
			System.out.println("tutti i path sono da "+partenza+" a dac sono: "+resultsfftToDac.size());
			if(!resultsfftToDac.isEmpty()) {
				//da dac a out
				partenza="dac";
				serviceTest11.calcolaPathArrivo("out");
				List<List<String>> resultsDacToOut=serviceTest11.calcolaPath(partenza);
				System.out.println("tutti i path sono da "+partenza+" a out sono: "+resultsDacToOut.size());
				path_srv_fft_dac_out=BigInteger.valueOf(resultsfft.size()).multiply(BigInteger.valueOf(resultsfftToDac.size())).multiply(BigInteger.valueOf(resultsDacToOut.size()));
			}
		}
		if(!resultsDac.isEmpty()) {
			//da dac a fft
			partenza="dac";
			serviceTest11.calcolaPathArrivo("fft");
			List<List<String>> resultsDacToFFt=serviceTest11.calcolaPath(partenza);
			System.out.println("tutti i path sono da "+partenza+" a fft sono: "+resultsDacToFFt.size());
			if(!resultsDacToFFt.isEmpty()) {
				//da fft a out
				partenza="fft";
				serviceTest11.calcolaPathArrivo("out");
				List<List<String>> resultsfftToOut=serviceTest11.calcolaPath(partenza);
				System.out.println("tutti i path sono da "+partenza+" a out sono: "+resultsfftToOut.size());
				path_srv_dac_fft_out=BigInteger.valueOf(resultsDac.size()).multiply(BigInteger.valueOf(resultsDacToFFt.size())).multiply(BigInteger.valueOf(resultsfftToOut.size()));
			}
		}
		System.out.println("la somma dei path è: "+path_srv_dac_fft_out.add(path_srv_fft_dac_out));
	}
	
	

}
