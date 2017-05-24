package BO;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class processString {
	File fileKey;
	HashMap<String, String> key = new HashMap<>();

	public String getAnswer(String imageText) {
		String[] imageSplit = imageText.split("\n");
		for (Map.Entry<String, String> entry : key.entrySet()) {
			if (entry.getKey().trim().toLowerCase().equals(imageSplit[0].trim().toLowerCase())) {
				for (int i = 1; i < imageSplit.length; i++) {
					if (imageSplit[i].trim().toLowerCase().contains(entry.getValue().trim().toLowerCase())) {
						return imageSplit[i].substring(0, 1);
					}
				}
			}
		}
		return "Z";
	}
}
