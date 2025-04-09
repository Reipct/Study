package Code.hot100;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Description 238. 除自身以外数组的乘积
 * @Author 12919
 * @Date 2025/2/25
 */
//给你一个整数数组 nums，返回 数组 answer ，其中 answer[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积 。
//题目数据 保证 数组 nums之中任意元素的全部前缀元素和后缀的乘积都在  32 位 整数范围内。
//请 不要使用除法，且在 O(n) 时间复杂度内完成此题。
//示例 1:
//输入: nums = [1,2,3,4]
//输出: [24,12,8,6]
//示例 2:
//输入: nums = [-1,1,0,-3,3]
//输出: [0,0,9,0,0]
public class test_18 {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 4};
        System.out.println(Arrays.toString(productExceptSelf(nums)));
    }

    public static int[] productExceptSelf(int[] nums) {
        //nums : l->r multiply
        //result : r->l multiply
        int len = nums.length;
        int[] result = new int[len];
        if (len == 0) return result;

        result[0] = 1;
        int temp = 1;
        for (int i = 1; i < len; i++) {
            result[i] = result[i - 1] * nums[i - 1];
        }
        for (int i = len - 2; i >= 0; i--) {
            temp *= nums[i + 1];
            result[i] *= temp;
        }
        return result;
    }
}
