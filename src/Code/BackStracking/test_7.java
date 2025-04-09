package Code.BackStracking;

import com.sun.org.apache.xerces.internal.xs.StringList;

import java.util.*;

/**
 * @Description 131.分割回文串
 * @Author 12919
 * @Date 2024/12/10
 */
public class test_7 {

    public static void main(String[] args) {
        String s = "aab";
        System.out.println(partition(s));
    }


    public static List<List<String>> partition(String s) {
        int len = s.length();
        List<List<String>> res = new ArrayList<>();
        if (len == 0) {
            return res;
        }

        Deque<String> list = new ArrayDeque<>();
        char[] charArray = s.toCharArray();
        dfs(charArray, 0, len, list, res);
        return res;

    }


    private static void dfs(char[] charArray, int index, int len, Deque<String> list, List<List<String>> result) {
        if (index == len) {
            result.add(new ArrayList<>(list));
            return;
        }
        for (int i = index; i < len; i++) {
            if (!isCorrect(charArray, index, i))
                continue;
            list.addLast(new String(charArray, index, i - index + 1));
            dfs(charArray, i + 1, len, list, result);
            list.removeLast();
        }
    }

    private static boolean isCorrect(char[] s, int left, int right) {

        while (left < right) {
            if (s[left] != s[right])
                return false;
            left++;
            right--;
        }
        return true;
    }
}
