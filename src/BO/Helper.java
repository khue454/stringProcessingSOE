package BO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

class Helper {
    String getCorrectQuestion(Map<String, String> map, String question) {
        List<String> questionSplit = getStringSplit(question);
        Map<Integer, String> keyMap = new TreeMap<>();
        for (String key : map.keySet()) {
            List<String> keySplit = getStringSplit(key);
            if (keySplit.get(0).equals(questionSplit.get(0))) {
                int count = 0;
                int length = keySplit.size() > questionSplit.size() ? questionSplit.size() : keySplit.size();
                for (int i = 0; i < length; i++) {
                    if (questionSplit.get(i).equals(keySplit.get(i))) count++;
                    else break;
                }
                keyMap.put(count, key);
            }
        }
        questionSplit = (keyMap.values().stream().collect(Collectors.toList()));
        List<Integer> valueSplit = keyMap.keySet().stream().collect(Collectors.toList());
        return valueSplit.get(valueSplit.size() - 1) == 0 ?
                "ERROR" : questionSplit.get(questionSplit.size() - 1);
    }

    private List<String> getStringSplit(String string) {
        string = string.toLowerCase();
        String[] stringSplit = string.split("[^a-z0-9]");
        List<String> strings = new ArrayList<>();
        for (String s : stringSplit) {
            if (!s.isEmpty())
                strings.add(s);
        }
        return strings;
    }
}
