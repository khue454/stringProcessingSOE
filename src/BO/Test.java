package BO;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        try {
            FileReader fr = new FileReader("ACC-Keyfull.txt");
            LineNumberReader lr = new LineNumberReader(fr);
            String st;
            while ((st = lr.readLine()) != null) {
                if (!st.isEmpty()) {
                    String[] stringSplit = st.split("[|]");
                    if (stringSplit.length == 2)
                        map.put(stringSplit[0].trim(), stringSplit[1].trim());
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        String key = "!~##!*$   Provide descriptions for this transaction kajsdj askdjk ajsdkj aks ?";
        Helper helper = new Helper();
        System.out.println(helper.getCorrectQuestion(map, key));
    }
}
