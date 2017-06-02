package BO;

import java.util.*;
import java.util.stream.Collectors;

class Normalization {

    // get the correct or most approximate question
    String getCorrectQuestion(Map<String, List<String>> map, String question) {
        // normalize question
        question = getTextNormalized(question);

        // split question via space
        List<String> questionSplit = Arrays
                .stream(question.split("\\s++"))
                .collect(Collectors.toList());

        // map to find correct question
        Map<Integer, List<String>> keyMap = new TreeMap<>();
        for (String key : map.keySet()) {

            // split key via space
            List<String> keySplit = Arrays
                    .stream(getTextNormalized(key).split("\\s++"))
                    .collect(Collectors.toList());

            // If both the key and the question begin with the same sequence
            // it will be count
            if (keySplit.get(0).equals(questionSplit.get(0))) {
                int count = 0;
                int length = keySplit.size() > questionSplit.size() ? questionSplit.size() : keySplit.size();

                // counting the number of positions from the same string
                // break if they are not the same.
                for (int i = 0; i < length; i++) {
                    if (questionSplit.get(i).equals(keySplit.get(i))) count++;
                    else break;
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
            List<List<String>> values = (keyMap.values().stream().collect(Collectors.toList()));
            questionSplit = values.get(values.size() - 1);
            if (questionSplit.size() > 1) {
                questionSplit.sort(Comparator.comparing(String::length));
                return questionSplit.get(0);
            } else return questionSplit.get(0);
        }
    }

    @SuppressWarnings("unused")
    // get normal map with the normal all text of it
    Map<String, List<String>> getNormalizedMap(Map<String, List<String>> map) {
        Map<String, List<String>> normalMap = new HashMap<>();
        for (String key : map.keySet()) {
            List<String> values = map.get(key);
            List<String> normalValues = values.parallelStream()
                    .map(v -> getTextNormalized(v))
                    .collect(Collectors.toList());
            normalMap.put(getTextNormalized(key), normalValues);
        }
        return normalMap;
    }

    // get normalized text
    String getTextNormalized(String string) {
        String[] stringSplit = string.toLowerCase().split("\\s++");
        String valueAtPos_0 = stringSplit[0];
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
