package Code.hot150;

import java.time.temporal.ValueRange;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Author 12919
 * @Date 2025/3/3
 */
public class Test_7 {
    public static void main(String[] args) {
        String ransomNote = "aa";
        String magazine = "aab";
        Test_7 test_7 = new Test_7();
        System.out.println(test_7.canConstruct_pro(ransomNote, magazine));
    }

    public boolean canConstruct_pro(String ransomNote, String magazine) {
        if (ransomNote.length() > magazine.length())
            return false;
        int[] letter = new int[26];
        for (int i = 0; i < magazine.length(); i++) {
            int c = magazine.charAt(i) - 'a';
            letter[c]++;
        }
        for (int i = 0; i < ransomNote.length(); i++) {
            int c = ransomNote.charAt(i) - 'a';
            if (letter[c] == 0)
                return false;
            else
                letter[c]--;
        }
        return true;
    }

    public boolean canConstruct(String ransomNote, String magazine) {
        if (ransomNote.length() > magazine.length())
            return false;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < magazine.length(); i++) {
            char c = magazine.charAt(i);
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        for (int i = 0; i < ransomNote.length(); i++) {
            char c = ransomNote.charAt(i);
            if (!map.containsKey(c))
                return false;
            else {
                if (map.get(c) == 1)
                    map.remove(c);
                else
                    map.put(c, map.get(c) - 1);
            }
        }
        return true;
    }

}
