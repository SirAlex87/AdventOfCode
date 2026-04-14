package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class Utility {
	
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
		try {
			classLoader = Utility.class.getClassLoader();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStream inputStream = classLoader.getResourceAsStream(file);
		try {
			data= readFromInputStream(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

}

