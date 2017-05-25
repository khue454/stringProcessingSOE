package BO;

import java.util.*;
import java.util.stream.Collectors;

class Helper {
    String getCorrectQuestion(Map<String, String> map, String question) {
        List<String> questionSplit = getStringSplit(question);
        Map<Integer, List<String>> keyMap = new TreeMap<>();
        for (String key : map.keySet()) {
            List<String> keySplit = getStringSplit(key);
            if (keySplit.get(0).equals(questionSplit.get(0))) {
                int count = 0;
                int length = keySplit.size() > questionSplit.size() ? questionSplit.size() : keySplit.size();
                for (int i = 0; i < length; i++) {
                    if (questionSplit.get(i).equals(keySplit.get(i))) count++;
                    else break;
                }
                if (keyMap.get(count) == null) {
                    List<String> list = new ArrayList<>();
                    list.add(key);
                    keyMap.put(count, list);
                } else {
                    List<String> list = keyMap.get(count);
                    list.add(key);
                    keyMap.put(count, list);
                }
            }
        }
        if (keyMap.keySet().isEmpty()) return "Error";
        else {
            List<List<String>> values = (keyMap.values().stream().collect(Collectors.toList()));
            questionSplit = values.get(values.size() - 1);
            if (questionSplit.size() > 1) {
                questionSplit.sort((String s1, String s2) -> s1.trim().length() - s2.trim().length());
                return questionSplit.get(0);
            } else return questionSplit.get(0);
        }
    }

    private List<String> getStringSplit(String string) {
        string = string.toLowerCase();
        String[] stringSplit = string.split("[^a-z0-9]");
        List<String> strings = Arrays.asList(stringSplit).stream()
                .filter(s -> !s.isEmpty()).collect(Collectors.toList());
        return strings;
    }
}
