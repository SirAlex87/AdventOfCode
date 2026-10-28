package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPSolver;

public class Utility {
	private static Logger logger = LogManager.getLogger(Utility.class);
 private Utility() {
  /* This utility class should not be instantiated */
 }

	
	private static String readFromInputStream(InputStream inputStream)
			  throws IOException {
			    StringBuilder resultStringBuilder = new StringBuilder();
			    try (BufferedReader br
			      = new BufferedReader(new InputStreamReader(inputStream))) {
			        String line;
			        while ((line = br.readLine()) != null) {
			            resultStringBuilder.append(line).append("\n");
			        }
			    }
			  return resultStringBuilder.toString();
			}
	public static String readFromInputStream(String file) throws IOException {
		ClassLoader classLoader=null;
		String data= null;
		InputStream inputStream = null;
		
		try {
			classLoader = Utility.class.getClassLoader();
			inputStream=classLoader.getResourceAsStream(file);
		} catch (Exception e) {
			logger.error(e);
		}
		
		try {
			data= readFromInputStream(inputStream);
		} catch (IOException e) {
			logger.error(e);
		}
		return data;
	}
	

public static List<String> trasponi(List<String> input) {
        List<String> risultato = new ArrayList<>();

        if (input == null || input.isEmpty()) {
            return risultato;
        }

        int maxLen = input.stream()
                          .mapToInt(String::length)
                          .max()
                          .orElse(0);

        for (int i = 0; i < maxLen; i++) {
            StringBuilder sb = new StringBuilder();
            for (String s : input) {
                if (s != null && i < s.length()) {
                    sb.append(s.charAt(i));
                }
            }
            risultato.add(sb.toString());
        }

        return risultato;
    }


	public static MPSolver loadResolver() {
		// Carica le librerie native di OR-Tools (obbligatorio una sola volta)
		Loader.loadNativeLibraries();

		// Crea un solver di programmazione lineare intera.
		// "CBC" è un solver open-source per Integer Linear Programming.

		return MPSolver.createSolver("CBC"); // risolutore intero

	}

}

