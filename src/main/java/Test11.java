import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
		//extracted2(data, serviceTest11,"svr");
		extractet2SecondVersion(data, serviceTest11,"svr");
	}

	private static void extractet2SecondVersion(String data, ServiceTest11 serviceTest11, String partenza) {
		String[] strParts = data.split("\n");
		Arrays.stream(strParts).forEach(s -> serviceTest11.populateList(s));
		BigInteger resultfftout = serviceTest11.countPaths("fft", "out", serviceTest11.getMappaConnessioni(), new HashMap<>());
		//BigInteger result = serviceTest11.countPaths("svr", "out", serviceTest11.getMappaConnessioni(), new HashMap<>(), "fft", "dac",0);
        System.out.println("Numero totale di path fft out: " + resultfftout);
        BigInteger resultdacout = serviceTest11.countPaths("dac", "out", serviceTest11.getMappaConnessioni(), new HashMap<>());
		//BigInteger result = serviceTest11.countPaths("svr", "out", serviceTest11.getMappaConnessioni(), new HashMap<>(), "fft", "dac",0);
        System.out.println("Numero totale di path dac out: " + resultdacout);
        BigInteger resultfftdac = serviceTest11.countPaths("fft", "dac", serviceTest11.getMappaConnessioni(), new HashMap<>());
		//BigInteger result = serviceTest11.countPaths("svr", "out", serviceTest11.getMappaConnessioni(), new HashMap<>(), "fft", "dac",0);
        System.out.println("Numero totale di path fft dac: " + resultfftdac);
        BigInteger resultdacfft = serviceTest11.countPaths("dac", "fft", serviceTest11.getMappaConnessioni(), new HashMap<>());
		//BigInteger result = serviceTest11.countPaths("svr", "out", serviceTest11.getMappaConnessioni(), new HashMap<>(), "fft", "dac",0);
        System.out.println("Numero totale di path dac fft: " + resultdacfft);
        BigInteger resultsvrfft = serviceTest11.countPaths("svr", "fft", serviceTest11.getMappaConnessioni(), new HashMap<>());
		//BigInteger result = serviceTest11.countPaths("svr", "out", serviceTest11.getMappaConnessioni(), new HashMap<>(), "fft", "dac",0);
        System.out.println("Numero totale di path svr fft: " + resultsvrfft);
        BigInteger resultsvrdac = serviceTest11.countPaths("svr", "dac", serviceTest11.getMappaConnessioni(), new HashMap<>());
		//BigInteger result = serviceTest11.countPaths("svr", "out", serviceTest11.getMappaConnessioni(), new HashMap<>(), "fft", "dac",0);
        System.out.println("Numero totale di path svr dac: " + resultsvrdac);
        BigInteger path_srv_dac_fft_out=BigInteger.ZERO;
        BigInteger path_srv_fft_dac_out=BigInteger.ZERO;
        if(resultsvrfft.compareTo(BigInteger.ZERO)>0) {
        	if(resultfftdac.compareTo(BigInteger.ZERO)>0) {
        		if(resultdacout.compareTo(BigInteger.ZERO)>0) {
        			path_srv_fft_dac_out=resultsvrfft.multiply(resultfftdac).multiply(resultdacout);
        			System.out.println("path_srv_fft_dac_out:"+path_srv_fft_dac_out);
        		}
        	}
        }
        if(resultsvrdac.compareTo(BigInteger.ZERO)>0) {
        	if(resultdacfft.compareTo(BigInteger.ZERO)>0) {
        		if(resultfftout.compareTo(BigInteger.ZERO)>0) {
        			path_srv_fft_dac_out=resultsvrdac.multiply(resultdacfft).multiply(resultfftout);
        			System.out.println("path_srv_fft_dac_out:"+path_srv_fft_dac_out);
        		}
        	}
        }

		
	}

	private static void extracted1(String data, ServiceTest11 serviceTest11, String partenza) {
		String[] strParts = data.split("\n");
		Arrays.stream(strParts).forEach(s -> serviceTest11.populateList(s));
		serviceTest11.calcolaPathArrivo("out");
		List<List<String>> results=serviceTest11.calcolaPath(partenza);
		System.out.println("tutti i path sono da "+partenza+" a out sono: "+results.size());
	}
	
	private static void extracted2NV(String data, ServiceTest11 serviceTest11, String partenza) {
		String[] strParts = data.split("\n");
		Arrays.stream(strParts).forEach(s -> serviceTest11.populateList(s));
		partenza="fft";
		serviceTest11.calcolaPathArrivo("out");
		List<List<String>> resultsDacToOut=serviceTest11.calcolaPath(partenza);
		System.out.println("tutti i path sono da "+partenza+" a out sono: "+resultsDacToOut.size());
		}

	private static void extracted2(String data, ServiceTest11 serviceTest11, String partenza) {
		String[] strParts = data.split("\n");
		Arrays.stream(strParts).forEach(s -> serviceTest11.populateList(s));
		//da srv a fft
		serviceTest11.calcolaPathArrivo("fft");
		List<List<String>> resultsfft=serviceTest11.calcolaPath(partenza);
		System.out.println("tutti i path sono da "+partenza+" a fft sono: "+resultsfft.size());
		int containDac=0;
		for(int i=0;i<resultsfft.size();i++) {
			if(resultsfft.get(i).contains("dac")) {
				containDac++;
			}
		}
		System.out.println("fft contains dac:"+containDac);
		if(containDac>0) {
			//da dac a out
//			partenza="dac";
//			serviceTest11.calcolaPathArrivo("out");
//			List<List<String>> resultsDacToOut=serviceTest11.calcolaPath(partenza);
//			System.out.println("tutti i path sono da "+partenza+" a out sono: "+resultsDacToOut.size());
		}
		System.out.println("containDac:"+containDac);
		//da srv a dac
		partenza="svr";
		serviceTest11.calcolaPathArrivo("dac");
		List<List<String>> resultsDac=serviceTest11.calcolaPath(partenza);
		System.out.println("tutti i path sono da "+partenza+" a dac sono: "+resultsDac.size());
		int containfft=0;
		for(int i=0;i<resultsDac.size();i++) {
			if(resultsfft.get(i).contains("fft")) {
				containfft++;
			}
		}
		System.out.println("containfft:"+containfft);
//		BigInteger path_srv_dac_fft_out=BigInteger.ZERO;
//		BigInteger path_srv_fft_dac_out=BigInteger.ZERO;
//		if(!resultsfft.isEmpty()) {
//			//da fft a dac
//			partenza="fft";
//			serviceTest11.calcolaPathArrivo("dac");
//			List<List<String>> resultsfftToDac=serviceTest11.calcolaPath(partenza);
//			System.out.println("tutti i path sono da "+partenza+" a dac sono: "+resultsfftToDac.size());
//			if(!resultsfftToDac.isEmpty()) {
//				//da dac a out
//				partenza="dac";
//				serviceTest11.calcolaPathArrivo("out");
//				List<List<String>> resultsDacToOut=serviceTest11.calcolaPath(partenza);
//				System.out.println("tutti i path sono da "+partenza+" a out sono: "+resultsDacToOut.size());
//				path_srv_fft_dac_out=BigInteger.valueOf(resultsfft.size()).multiply(BigInteger.valueOf(resultsfftToDac.size())).multiply(BigInteger.valueOf(resultsDacToOut.size()));
//			}
//		}
//		if(!resultsDac.isEmpty()) {
//			//da dac a fft
//			partenza="dac";
//			serviceTest11.calcolaPathArrivo("fft");
//			List<List<String>> resultsDacToFFt=serviceTest11.calcolaPath(partenza);
//			System.out.println("tutti i path sono da "+partenza+" a fft sono: "+resultsDacToFFt.size());
//			if(!resultsDacToFFt.isEmpty()) {
//				//da fft a out
//				partenza="fft";
//				serviceTest11.calcolaPathArrivo("out");
//				List<List<String>> resultsfftToOut=serviceTest11.calcolaPath(partenza);
//				System.out.println("tutti i path sono da "+partenza+" a out sono: "+resultsfftToOut.size());
//				path_srv_dac_fft_out=BigInteger.valueOf(resultsDac.size()).multiply(BigInteger.valueOf(resultsDacToFFt.size())).multiply(BigInteger.valueOf(resultsfftToOut.size()));
//			}
//		}
//		System.out.println("la somma dei path è: "+path_srv_dac_fft_out.add(path_srv_fft_dac_out));
	}
	
	

}
