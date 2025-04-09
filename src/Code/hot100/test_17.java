package Code.hot100;

import java.util.Arrays;

/**
 * @Description 189. 轮转数组
 * @Author 12919
 * @Date 2025/2/24
 */
//给定一个整数数组 nums，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
//输入: nums = [1,2,3,4,5,6,7], k = 3
//输出: [5,6,7,1,2,3,4]
//解释:
//向右轮转 1 步: [7,1,2,3,4,5,6]
//向右轮转 2 步: [6,7,1,2,3,4,5]
//向右轮转 3 步: [5,6,7,1,2,3,4]
public class test_17 {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 4, 5, 6, 7};
        int k = 3;
        rotate(nums, 3);
        System.out.println(Arrays.toString(nums));
    }

    public static void rotate(int[] nums, int k) {
        k %= nums.length;
        reverser(nums, 0, nums.length - 1);
        reverser(nums, 0, k - 1);
        reverser(nums, k, nums.length - 1);
    }

    public static void reverser(int[] nums, int start, int end) {
        if (end - start <= 0) return;
        int left = start;
        int right = end;
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }
}