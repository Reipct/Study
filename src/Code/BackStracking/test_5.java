package Code.BackStracking;

import java.util.*;

/**
 * @Description 39. 组合总和
 * @Author 12919
 * @Date 2024/12/10
 */
public class test_5 {

    public static void main(String[] args) {
        int[] candidates = {2, 3, 5};
        int target = 8;
        System.out.println(combinationSum(candidates, target));
    }

    static Deque<Integer> deque = new LinkedList<>();
    static List<List<Integer>> result = new ArrayList<>();

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        if (candidates.length == 0)
            return result;
        Arrays.sort(candidates);
        backstracking(candidates, 0, 0, target);
        return result;
    }

    private static void backstracking(int[] candidates, int start, int sum, int target) {
        if (sum == target) {
            result.add(new ArrayList<>(deque));
            return;
        }
        if (sum > target)
            return;
        if (start < candidates.length && sum + candidates[start] > target)
            return;
        for (int i = start; i < candidates.length; i++) {
            deque.addLast(candidates[i]);
            backstracking(candidates, i, sum + candidates[i], target);
            deque.removeLast();
        }
    }
}

