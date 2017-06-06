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
			FileReader fr = new FileReader("DATABASE_full.txt");
			LineNumberReader lr = new LineNumberReader(fr);
			String st;
			while ((st = lr.readLine()) != null) {
				if (!st.isEmpty()) {
					String[] stringSplit = st.split("[|]");
					if (stringSplit.length == 2) {
						String key = stringSplit[0].trim();
						String value = stringSplit[1].trim();
//                        String key = map.get();
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
		String key = "!~##!*$ The rule that requires financial statements to reflect the assumption that?(*%(#@*$ the business will continue operating";
		Normalization normalization = new Normalization();
		System.out.println(normalization.getQuestion(
				"A database transaction. by de?nition. must be ACID (atomic. consistent. isolated and durable). What does �Consistent' mean?\n"
						+ "A. �Consistent' means that: Transactions provide an 'all-or-nothing' proposition. stating that each work-unit performed in a database must either complete in its entirety or have no effectwhatsoever"
						+ "\nB. �Consistent' means that: Transactions must notviolate any integrity constraints during its execution"));
		String key1 = normalization.getCorrectQuestion(map,
				"A database transaction. by de?nition. must be ACID (atomic. consistent. isolated and durable). What does �Consistent' mean?\n"
						+ "A. �Consistent' means that: Transactions provide an 'all-or-nothing' proposition. stating that each work-unit performed in a database must either complete in its entirety or have no effectwhatsoever"
						+ "\nB. �Consistent' means that: Transactions must notviolate any integrity constraints during its execution");
		System.out.println(key1);
		System.out.println(map.get(key1));
	}
}
