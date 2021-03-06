package BO;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
	public static void main(String[] args) {
		Map<String, List<String>> map = new HashMap<>();
		try {
			FileReader fr = new FileReader("MLN101.txt");
			LineNumberReader lr = new LineNumberReader(fr);
			String st;
			while ((st = lr.readLine()) != null) {
				if (!st.isEmpty()) {
					String[] stringSplit = st.split("[|]");
					if (stringSplit.length == 2) {
						String key = stringSplit[0].trim();
						String value = stringSplit[1].trim();
						List<String> values = map.get(key) == null ?
								new ArrayList<>() : map.get(key);
						values.add(value);
						map.put(key, values);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Normalization normalization = new Normalization();

		System.out.println(normalization.getCorrectQuestion(map, "Tiền công tư bản có hai hình thức đó là? \nA. "));
		System.out.println(map.get(normalization.getCorrectQuestion(map, "Thực tiển là? \nA. ")));
	}
}
