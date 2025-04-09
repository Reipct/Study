package Code.BackStracking;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description 93.复原IP地址
 * @Author 12919
 * @Date 2024/12/11
 */
public class test_8 {

    public static void main(String[] args) {
        String s = "101023";
        System.out.println(restoreIpAddresses(s));

    }

    public static List<String> restoreIpAddresses(String s) {

        List<String> result = new ArrayList<>();
        if (s.length() < 4 || s.length() > 12)
            return result;
        Deque<String> list = new LinkedList<>();
        char[] chars = s.toCharArray();
        dfs(chars, 0, s.length(), 4, list, result);
        return result;

    }


    private static void dfs(char[] chars, int index, int len, int size, Deque<String> list, List<String> result) {
        if (len - index > size * 3 || len - index < size)
            return;
        if (index == len) {
            if (list.size() == 4)
                result.add(String.join(".", list));
            return;
        }
        for (int i = index; i < len; i++) {
            if (i - index >= 3)
                continue;
            if (!idCorrect(chars, index, i))
                continue;
            list.addLast(new String(chars, index, i - index + 1));
            dfs(chars, i + 1, len, size - 1, list, result);
            list.removeLast();
        }
    }

    private static boolean idCorrect(char[] s, int left, int right) {

        if (s[left] == '0')
            return right == left;
        if (right - left >= 3)
            return false;
        int x = Integer.parseInt(new String(s, left, right - left + 1));
        return x <= 255;
    }

}
