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
public class test_4_1 {

    public static void main(String[] args) {
        System.out.println(letterCombinations("234"));
    }

    public static List<String> letterCombinations(String digits) {

        int len = digits.length();
        int[] num = new int[len];
        for (int i = 0; i < len; i++)
            num[i] = Character.getNumericValue(digits.charAt(i));
        List<String> result = new ArrayList<>();
        if(digits.equals(""))
            return result;
        Deque<Character> list = new LinkedList<>();
        for (String c : result)
            System.out.println(c);
        backstracking(num, len, 0, result, list);
        return result;

    }

    private static void backstracking(int[] num, int len, int startIndex, List<String> result, Deque<Character> list) {
        if (len == 0) {
            String str = "";
            for (char c : list)
                str = str + c;
            result.add(str);
            return;
        }
        String str = numstr(num[startIndex]);
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            list.addLast(c);
            backstracking(num, len - 1, startIndex + 1, result, list);
            list.removeLast();
        }
    }

    private static String numstr(int x) {
        switch (x) {
            case 2:
                return "abc";
            case 3:
                return "def";
            case 4:
                return "ghi";
            case 5:
                return "jkl";
            case 6:
                return "mno";
            case 7:
                return "pqrs";
            case 8:
                return "tuv";
            case 9:
                return "wxyz";
        }
        return "";
    }


}
