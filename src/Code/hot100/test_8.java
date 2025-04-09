package Code.hot100;

import java.util.*;

/**
 * @Description 46.全排列
 * @Author 12919
 * @Date 2025/2/18
 */
//给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
//    输入：nums = [1,2,3]
//            输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
public class test_8 {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3};
        System.out.println(permute(nums));
    }

    public static List<List<Integer>> permute(int[] nums) {
        int len = nums.length;
        List<List<Integer>> result = new ArrayList<>();
        if (len == 0)
            return result;

        List<Integer> list = new ArrayList<>();

        boolean[] used = new boolean[len];
        Backstracking(nums, len, 0, used, list, result);
        return result;
    }

    public static void Backstracking(int[] nums, int len, int depth, boolean[] used, List<Integer> list, List<List<Integer>> result) {
        if (depth == len) {
            result.add(new ArrayList<>(list));
            return;
        }
        for (int i = 0; i < len; i++) {
            if (!used[i]) {
                list.add(depth, nums[i]);
                used[i] = true;
                Backstracking(nums, len, depth + 1, used, list, result);
                list.remove(depth);
                used[i] = false;
            }
        }
    }
}
