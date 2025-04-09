package Code.hot100;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 560. 和为 K 的子数组
 * @Author 12919
 * @Date 2025/2/20
 */
//给你一个整数数组 nums 和一个整数 k ，请你统计并返回 该数组中和为 k 的子数组的个数 。
//子数组是数组中元素的连续非空序列。
//示例 1：
//输入：nums = [1,1,1], k = 2
//输出：2
//示例 2：
//输入：nums = [1,2,3], k = 3
//输出：2

public class test_11 {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3};
        System.out.println(subarraySum(nums, 3));
    }

    public static int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> mp = new HashMap<>();
        int pre = 0;
        int count = 0;
        mp.put(0, 1);
        for (int i = 0; i < nums.length; i++) {
            pre += nums[i];
            if (mp.containsKey(pre - k))
                count += mp.get(pre - k);
            mp.put(pre, mp.getOrDefault(pre, 0) + 1);
        }
        return count;
    }
}
