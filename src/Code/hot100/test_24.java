package Code.hot100;

import java.util.Arrays;

/**
 * @Description 34. 在排序数组中查找元素的第一个和最后一个位置
 * @Author 12919
 * @Date 2025/2/28
 */
public class test_24 {

    public static void main(String[] args) {
        int[] nums = new int[]{5, 8, 8, 8, 8, 10};
        System.out.println(Arrays.toString(searchRange(nums, 8)));

    }

    public static int[] searchRange(int[] nums, int target) {
        int start = searchInsert(nums, target);
        if (start == nums.length || nums[start] != target)
            return new int[]{-1, -1};
        int end = searchInsert(nums, target + 1)-1;
        return new int[]{start, end};
    }

    public static int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

}
