package Code.hot100;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description 78. 子集
 * @Author 12919
 * @Date 2025/2/28
 */
public class test_23 {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3};
        System.out.println(subsets(nums));
    }

    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Deque<Integer> deque = new LinkedList<>();
        backstracking(nums, 0, nums.length, deque, result);
        return result;
    }

    public static void backstracking(int[] nums, int index, int len, Deque<Integer> deque, List<List<Integer>> result) {
            result.add(new ArrayList<>(deque));

        for (int i = index; i < len; i++) {
            deque.addLast(nums[i]);
            backstracking(nums, i + 1, len, deque, result);
            deque.removeLast();
        }
    }

}
