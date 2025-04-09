package Code.BackStracking;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description 17.电话号码的字母组合
 * 输入：digits = "23"
 * 输出：["ad","ae","af","bd","be","bf","cd","ce","cf"
 * @Author 12919
 * @Date 2024/12/8
 */
public class test_4_2 {

    public static void main(String[] args) {
        System.out.println(letterCombinations("234"));
    }

    static String[] letter_map = {" ", "*", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    static List<String> result = new ArrayList<>();

    public static List<String> letterCombinations(String digits) {
        if (digits == null || digits.length() == 0) {
            return new ArrayList<>();
        }
        iterStr(digits, new StringBuilder(), 0);
        return result;

    }

    static void iterStr(String str, StringBuilder letter, int index) {
        if (index == str.length()) {
            result.add(letter.toString());
            return;
        }

        char c = str.charAt(index);
        int pos = c - '0';
        String map_string = letter_map[pos];

        for (int i = 0; i < map_string.length(); i++) {
            //调用下一层递归，用文字很难描述，请配合动态图理解
            letter.append(map_string.charAt(i));
            //如果是String类型做拼接效率会比较低
            //iterStr(str, letter+map_string.charAt(i), index+1);
            iterStr(str, letter, index + 1);
            letter.deleteCharAt(letter.length() - 1);
        }
    }

}
