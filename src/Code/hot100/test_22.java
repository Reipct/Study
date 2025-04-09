package Code.hot100;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 22. 括号生成
 * @Author 12919
 * @Date 2025/2/28
 */
public class test_22 {

    public static void main(String[] args) {
        System.out.println(generateParenthesis(2));

    }

    public static List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        if (n == 0)
            return result;
        backstracking("", n, n, result);
        return result;
    }

    public static void backstracking(String str, int left, int right, List<String> result) {
        if (left == 0 && right == 0) {
            result.add(str);
            return;
        }
        if (left > right)
            return;
        if (left > 0) {
            backstracking(str + "(", left - 1, right, result);
        }
        if (right > 0) {
            backstracking(str + ")", left, right - 1, result);
        }
    }
}