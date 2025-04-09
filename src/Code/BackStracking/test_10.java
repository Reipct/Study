package Code.BackStracking;

import java.util.*;

/**
 * @Description 子集 II
 * @Author 12919
 * @Date 2025/1/29
 */
//输入：nums = [1,2,2]
//输出：[[],[1],[1,2],[1,2,2],[2],[2,2]]
public class test_10 {
    public static void main(String[] args) {

    }

    static List<List<Integer>> res = new ArrayList<>();
    static LinkedList<Integer> deque = new LinkedList<>();

    public static List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        BackStracking(nums, 0);
        return res;

    }

    public static void BackStracking(int[] nums, int index) {
        res.add(new ArrayList<>(deque));
        for (int i = index; i < nums.length; i++) {
            if (i > index && nums[i] == nums[i - 1])
                continue;
            deque.add(nums[i]);
            BackStracking(nums, i + 1);
            deque.removeLast();
        }
    }
}
