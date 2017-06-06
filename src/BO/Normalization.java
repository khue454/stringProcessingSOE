package BO;

import java.util.*;
import java.util.stream.Collectors;

class Normalization {

    // get the correct or most approximate question
    String getCorrectQuestion(Map<String, List<String>> map, String image) {

        image = getQuestion(image);
        // normalize question
        String question = getTextNormalized(image);

        // split question via space
        List<String> questionSplit = Arrays
                .stream(question.split("\\s++"))
                .collect(Collectors.toList());

        // map to find correct question
        Map<Integer, List<String>> keyMap = new TreeMap<>();
        for (String key : map.keySet()) {

            // split key via space
            String questionInMap = getTextNormalized(key);
            List<String> keySplit = Arrays
                    .stream(questionInMap.split("\\s++"))
                    .collect(Collectors.toList());

            // If both the key and the question begin with the same sequence
            // it will be count...
            if (questionInMap.trim().equalsIgnoreCase(question.trim())) {
                return key;
            } else if (keySplit.get(0).equals(questionSplit.get(0))) {
                int count = 0;
                int length = keySplit.size() > questionSplit.size() ? questionSplit.size() : keySplit.size();

                // counting the number of positions from the same string
                // break if they are not the same.
                for (int i = 0; i < length; i++) {
                    if (questionSplit.get(i).equals(keySplit.get(i))) count++;
                }

                // if value in keyMap is nullable, create new instance
                // else get by key.
                List<String> value = keyMap.get((Integer) count) == null ?
                        new ArrayList<>() : keyMap.get((Integer) count);
                value.add(key);
                keyMap.put(count, value);
            }
        }
        if (keyMap.keySet().isEmpty()) return "Error";
        else {
//            List<List<String>> values = (keyMap.values().stream().collect(Collectors.toList()));
//            questionSplit = values.get(values.size() - 1);
            List<String> values = keyMap.values().stream().collect(Collectors.toList()).get(keyMap.values().size()-1);
            if (values.size() > 1) {
                values.sort(Comparator.comparing(String::length));
                question = values.get(0);
            } else question = values.get(0);
            int length = keyMap.keySet().parallelStream().collect(Collectors.toList()).get(keyMap.size() - 1);
            questionSplit = Arrays
                    .stream(question.split("\\s++"))
                    .collect(Collectors.toList());
            if (questionSplit.size() < 20) {
                System.out.println("< 20");
                System.out.println(length);
                System.out.println(questionSplit.size());
                return ((double) length * 10 / questionSplit.size()) >= 8.5 ? question : "ERROR";
            } else {
                System.out.println(">= 20");
                return ((double) length * 10 / questionSplit.size()) >= 7.5 ? question : "ERROR";
            }
        }
    }

    private String getQuestion(String image) {
        String question = "";
        image = image.substring(0, image.indexOf("\nA.") - 1);
        image = image.trim();
        image = image.startsWith("(") ? image.substring(image.indexOf(")") + 1) : image;

        String[] imageSplit = image.split("[\r\n]++");
        for (String item : imageSplit) {
            question += item.isEmpty() || item == null ? "" : item + " ";
        }
        return question;
    }

    // get normalized text
    String getTextNormalized(String string) {
        String[] stringSplit = string.toLowerCase().split("[.]");
        String valueAtPos_0 = stringSplit[0];
        boolean checkFirst = false;
        try {
            Integer.parseInt(valueAtPos_0);
            checkFirst = true;
        } catch (Exception e) {
        }
        if (checkFirst && stringSplit.length > 1) {
            string = "";
            int length = stringSplit.length;
            for (int i = 1; i < length; i++) {
                string += stringSplit[i] + " ";
            }
        } else {
            stringSplit = string.toLowerCase().split("\\s++");
            valueAtPos_0 = stringSplit[0];
            boolean isStartKey = false;

            // split string to check valid
            // remove first positive of split string if startwish 'qn' or endwish '.'
            if (valueAtPos_0.trim().toLowerCase().startsWith("qn")) isStartKey = true;
            else if (valueAtPos_0.trim().endsWith(".")) isStartKey = true;
            if (isStartKey) {
                string = "";
                for (int i = 1; i < stringSplit.length; i++) {
                    string += stringSplit[i].trim();
                }
            }
        }
        // normalize text, keep digits and letters only
        stringSplit = string.toLowerCase().split("[^a-z0-9]");
        StringBuilder textNormalized = new StringBuilder();
        for (String s : stringSplit) {
            if (!s.isEmpty())
                textNormalized.append(s).append(" ");
        }
        return textNormalized.toString().trim();
    }
}
