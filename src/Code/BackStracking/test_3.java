package Code.BackStracking;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description 216.组合总和III
 * @Author 12919
 * @Date 2024/12/7
 */
public class test_3 {

    public static void main(String[] args) {
        System.out.println(combinationSum3(3, 9));
    }

    public static List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();
        if (n < 0)
            return result;
        Deque<Integer> deque = new LinkedList<>();
        backstracking(k, n, 1, result, deque);
        return result;
    }

    //n=9,k=3
    private static void backstracking(int k, int n, int startIndex, List<List<Integer>> result,
                                      Deque<Integer> deque) {
        if (k == 0) {
            if (n == 0)
                result.add(new ArrayList<>(deque));
            return;
        }
        if (n < 0 || n < startIndex)
            return;
        for (int i = startIndex; i <= n && i < 10; i++) {
            deque.addLast(i);
            backstracking(k - 1, n - i, i + 1, result, deque);
            deque.removeLast();
        }
    }
}
