import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import service.ServiceTest11;
import util.Utility;

public class Test11 {

	private static final Logger logger = LogManager.getLogger(Test11.class);

	public static void main(String[] args) {
		String data = null;
		try {
			// data = Utility.readFromInputStream("prova11Test.txt");
			// data = Utility.readFromInputStream("prova11TestParte2.txt");
			data = Utility.readFromInputStream("prova11.txt");
			logger.debug(data);
			extracted1(data, "you");
			extractet2SecondVersion(data, "svr");
		} catch (IOException e) {
			logger.error(e);
		}

	}

	private static void extracted1(String data, String partenza) {
		ServiceTest11 serviceTest11 = new ServiceTest11();
		String[] strParts = data.split("\n");
		Arrays.stream(strParts).forEach(serviceTest11::populateList);
		serviceTest11.calcolaPathArrivo("out");
		List<List<String>> results = serviceTest11.calcolaPath(partenza);
		logger.log(Level.INFO, "tutti i path sono da {} a fft sono:{} ", partenza, results.size());
	}

	private static void extractet2SecondVersion(String data, String partenza) {
		ServiceTest11 serviceTest11 = new ServiceTest11();
		String[] strParts = data.split("\n");
		Arrays.stream(strParts).forEach(s -> serviceTest11.populateList(s));
		BigInteger resultfftout = serviceTest11.countPaths("fft", "out", serviceTest11.getMappaConnessioni(),
				new HashMap<>());
		// BigInteger result = serviceTest11.countPaths("svr", "out",
		// serviceTest11.getMappaConnessioni(), new HashMap<>(), "fft", "dac",0);
		logger.log(Level.INFO, "Numero totale di path fft out: {}", resultfftout);
		BigInteger resultdacout = serviceTest11.countPaths("dac", "out", serviceTest11.getMappaConnessioni(),
				new HashMap<>());
		// BigInteger result = serviceTest11.countPaths("svr", "out",
		// serviceTest11.getMappaConnessioni(), new HashMap<>(), "fft", "dac",0);
		logger.log(Level.INFO, "Numero totale di path dac out: {}", resultdacout);
		BigInteger resultfftdac = serviceTest11.countPaths("fft", "dac", serviceTest11.getMappaConnessioni(),
				new HashMap<>());
		// BigInteger result = serviceTest11.countPaths("svr", "out",
		// serviceTest11.getMappaConnessioni(), new HashMap<>(), "fft", "dac",0);
		logger.log(Level.INFO, "Numero totale di path fft dac: {}", resultfftdac);
		BigInteger resultdacfft = serviceTest11.countPaths("dac", "fft", serviceTest11.getMappaConnessioni(),
				new HashMap<>());
		// BigInteger result = serviceTest11.countPaths("svr", "out",
		// serviceTest11.getMappaConnessioni(), new HashMap<>(), "fft", "dac",0);
		logger.log(Level.INFO, "Numero totale di path dac fft: {}", resultdacfft);
		BigInteger resultsvrfft = serviceTest11.countPaths("svr", "fft", serviceTest11.getMappaConnessioni(),
				new HashMap<>());
		// BigInteger result = serviceTest11.countPaths("svr", "out",
		// serviceTest11.getMappaConnessioni(), new HashMap<>(), "fft", "dac",0);
		logger.log(Level.INFO, "Numero totale di path svr fft: {}", resultsvrfft);
		BigInteger resultsvrdac = serviceTest11.countPaths("svr", "dac", serviceTest11.getMappaConnessioni(),
				new HashMap<>());
		// BigInteger result = serviceTest11.countPaths("svr", "out",
		// serviceTest11.getMappaConnessioni(), new HashMap<>(), "fft", "dac",0);
		logger.log(Level.INFO, "Numero totale di path svr dac: {}", resultsvrdac);
		BigInteger path_srv_dac_fft_out = BigInteger.ZERO;
		BigInteger path_srv_fft_dac_out = BigInteger.ZERO;
		if (resultsvrfft.compareTo(BigInteger.ZERO) > 0) {
			if (resultfftdac.compareTo(BigInteger.ZERO) > 0) {
				if (resultdacout.compareTo(BigInteger.ZERO) > 0) {
					path_srv_fft_dac_out = resultsvrfft.multiply(resultfftdac).multiply(resultdacout);
					logger.log(Level.INFO, "path_srv_fft_dac_out:{}", path_srv_fft_dac_out);
				}
			}
		}
		if (resultsvrdac.compareTo(BigInteger.ZERO) > 0 && resultdacfft.compareTo(BigInteger.ZERO) > 0
				&& resultfftout.compareTo(BigInteger.ZERO) > 0) {
			path_srv_fft_dac_out = resultsvrdac.multiply(resultdacfft).multiply(resultfftout);
			logger.log(Level.INFO, "path_srv_fft_dac_out:{}", path_srv_fft_dac_out);
		}

	}

}
