package Code.hot150;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description 77. 组合
 * @Author 12919
 * @Date 2025/3/3
 */
//给定两个整数 n 和 k，返回范围 [1, n] 中所有可能的 k 个数的组合。
//
//你可以按 任何顺序 返回答案。
public class Test_5 {
    public static void main(String[] args) {
        int n = 4, k = 2;
        Test_5 test_5 = new Test_5();
        List<List<Integer>> lists = test_5.combine(n, k);
        System.out.println(lists);

    }

    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        if (k <= 0 || n < k) {
            return res;
        }
        List<Integer> list = new ArrayList<>();
        backstracking(n, k, 1, list, res);
        return res;
    }

    public void backstracking(int n, int k, int index, List<Integer> list, List<List<Integer>> res) {
        if (list.size() == k) {
            res.add(new ArrayList<>(list));
            return;
        }
        for (int i = index; i <= n; i++) {
            list.add(i);
            backstracking(n, k, i + 1, list, res);
            list.remove(list.size() - 1);
        }

    }
}