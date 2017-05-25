package BO;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class processString {
	public static HashMap<String, String> key = new HashMap<>();
	
	public static List<String> processImageText(String imageText) {
        imageText = imageText.trim();
        int countAnswers = 0;
        if (imageText.contains("A.")) {
            countAnswers++;
        }
        if (imageText.contains("B.")) {
            countAnswers++;
        }
        if (imageText.contains("C.")) {
            countAnswers++;
        }
        if (imageText.contains("D.")) {
            countAnswers++;
        }
        if (imageText.contains("E.")) {
            countAnswers++;
        }
        if (imageText.contains("F.")) {
            countAnswers++;
        }
        List<String> imageSplit = new ArrayList<>();
        int rootIndex = 0;
        imageSplit.add(imageText.substring(0, imageText.indexOf("A.") - 1));
        rootIndex = imageText.indexOf("A.");
        imageSplit.add(imageText.substring(rootIndex, imageText.indexOf("B.") - 1));
        rootIndex = imageText.indexOf("B.");
        if (countAnswers > 2) {
            imageSplit.add(imageText.substring(rootIndex, imageText.indexOf("C.") - 1));
            rootIndex = imageText.indexOf("C.");
        }
        if (countAnswers > 3) {
            imageSplit.add(imageText.substring(rootIndex, imageText.indexOf("D.") - 1));
            rootIndex = imageText.indexOf("D.");
        }
        if (countAnswers > 4) {
            imageSplit.add(imageText.substring(rootIndex, imageText.indexOf("E.") - 1));
            rootIndex = imageText.indexOf("E.");
        }
        if (countAnswers > 5) {
            imageSplit.add(imageText.substring(rootIndex, imageText.indexOf("F.") - 1));
            rootIndex = imageText.indexOf("F.");
        }
        imageSplit.add(imageText.substring(rootIndex));
        imageSplit.set(0, imageSplit.get(0).replace(".", ""));
        for (int i = 0; i < imageSplit.size(); i++) {
            imageSplit.set(i, imageSplit.get(i).replace("\n", ""));
            imageSplit.set(i, imageSplit.get(i).replace("?", ""));
            imageSplit.set(i, imageSplit.get(i).replace("—", ""));
        }
        return imageSplit;
    }

    public static String getAnswer(List<String> imageSplit) {
        List<Integer> countDuplicateWord = new ArrayList<>();
        int countSuccess = 0;
        int countCompare = 0;
        for (Map.Entry<String, String> entry : key.entrySet()) {
            if (entry.getKey().trim().toLowerCase().equals(imageSplit.get(0).trim().toLowerCase())) {
                for (int i = 1; i < imageSplit.size(); i++) {
                    if (imageSplit.get(i).trim().toLowerCase().contains(entry.getValue().trim().toLowerCase())) {
                        countSuccess = i;
                    }
                }
                if (countSuccess > 0) {
                    return imageSplit.get(countSuccess).trim().substring(0, 1);
                } else {
                    StringTokenizer stFile = new StringTokenizer(entry.getValue().trim().toLowerCase());
                    for (int i = 1; i < imageSplit.size(); i++) {
                        StringTokenizer stImage = new StringTokenizer(imageSplit.get(i));
                        while (stImage.hasMoreTokens()) {
                            while (stFile.hasMoreTokens()) {
                                if (stFile.equals(stImage)) {
                                    countCompare++;
                                }
                            }
                        }
                        countDuplicateWord.add(countCompare);
                    }
                    int max = countDuplicateWord.get(0);
                    for (int i = 0; i < countDuplicateWord.size(); i++) {
                        if (countDuplicateWord.get(i) < countDuplicateWord.get(i + 1)) {
                            max = countDuplicateWord.get(i + 1);
                        }
                    }
                    return imageSplit.get(max + 1).substring(0, 1);
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
