package Code.BackStracking;

import java.util.*;

/**
 * @Description 78.子集
 * @Author 12919
 * @Date 2024/12/11
 */
public class test_9 {
    public static void main(String[] args) {

        int[] a = {1, 2, 3};
        System.out.println(subsets(a));

    }

    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Deque<Integer> numDeque = new LinkedList<>();
        dfs(nums, 0, res, numDeque);
        return res;
    }

    private static void dfs(int[] nums, int index, List<List<Integer>> res, Deque<Integer> deque) {
        res.add(new ArrayList<>(deque));
        for (int i = index; i < nums.length; i++) {
            deque.addLast(nums[i]);
            dfs(nums, i + 1, res, deque);
            deque.removeLast();
        }
    }
}
