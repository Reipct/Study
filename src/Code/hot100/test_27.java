package Code.hot100;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author 12919
 * @Date 2025/3/1
 */
public class test_27 {

    public static void main(String[] args) {

    }

    static String[] letter_map = {" ", "*", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    static List<String> res = new ArrayList<>();

    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.length() == 0) {
            return new ArrayList<>();
        }
        backstracking(digits, new StringBuilder(), 0);
        return res;
    }

    public static void backstracking(String digits, StringBuilder letter, int index) {
        if (index == digits.length()) {
            res.add(letter.toString());
            return;
        }
        char c = digits.charAt(index);
        int pos = c - '0';
        String map_string = letter_map[pos];

        for (int i = 0; i < map_string.length(); i++) {
            letter.append(map_string.charAt(i));
            backstracking(digits, letter, index + 1);
            letter.deleteCharAt(letter.length() - 1);
        }

    }
}
