package BO;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class processString {
	public static HashMap<String, String> key = new HashMap<>();
	
	public String getAnswer(String imageText) {
		imageText = imageText.trim();
		char[] b = imageText.toCharArray();
		if (b[0] == '—') {
			imageText = imageText.substring(1);
		}
		int countDuplicateWord = 0;
		String[] imageSplit = imageText.split("\n");
		for (Map.Entry<String, String> entry : key.entrySet()) {
			if (entry.getKey().trim().toLowerCase().equals(imageSplit[0].trim().toLowerCase())) {
				for (int i = 1; i < imageSplit.length; i++) {
					if (imageSplit[i].trim().toLowerCase().contains(entry.getValue().trim().toLowerCase())) {
						return imageSplit[i].substring(0, 1);
					} else {
						StringTokenizer st = new StringTokenizer(entry.getValue().trim().toLowerCase());
						while (st.hasMoreTokens()) {
							if (imageSplit[i].trim().toLowerCase().contains(st.nextToken())) {
								countDuplicateWord++;
							}
						}
						if (countDuplicateWord > 5) {
							return imageSplit[i].substring(0, 1);
						}
					}
				}
			}
		}
		return "Z";
	}

	public static void main(String[] args) {
		processString p = new processString();
		String a = "—\nWhich of the following does a policy change control board do?\n\nA. Assesses policies and standards and makes recommendations for change\n\nB. Determines the policy and standards library numbering scheme\n\nC. Implements technical controls as business conditions change\n\nD. Reviews requested changes to the policy framework\n\nE. A and D\n\n";
		key.put("which of the following does a policy change control board do?", "A and D");
		System.out.println(p.getAnswer(a));
	}
}
