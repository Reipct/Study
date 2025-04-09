package Code.hot100;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @Description 506.和为K的子数组
 * @Author 12919
 * @Date 2025/2/5
 */
public class test_2 {
    public static void main(String[] args) {
        System.out.println(subarraySum(new int[]{1, 2, 3, 2}, 3));
    }

    public static int subarraySum(int[] nums, int k) {
        Backstracking(nums, k, 0, 0);
        return num_array;
    }

    private static int num_array = 0;

    public static void Backstracking(int[] nums, int k, int count, int index) {

        for (int i = index; i < nums.length; i++) {
            count += nums[i];
            if (count == k)
                num_array++;
            Backstracking(nums, k, count, i + 1);
            count -= nums[i];
        }
    }
}