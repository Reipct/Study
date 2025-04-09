package Code.hot100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.OptionalInt;

/**
 * @Description 42. 接雨水
 * @Author 12919
 * @Date 2025/2/27
 */
//给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
public class test_19 {
    public static void main(String[] args) {
        int[] nums = new int[]{4, 2, 0, 3, 2, 5};
        System.out.println(trap_pro_pro(nums));
    }

    //时间On，空间O1
    public static int trap_pro_pro(int[] height) {
        int len = height.length;
        int result = 0;
        int max_left = 0;
        int max_right = 0;
        int left = 1;
        int right = height.length - 2;
        for (int i = 1; i < len - 1; i++) {
            if (height[left - 1] <= height[right + 1]) {
                max_left = Math.max(max_left, height[left - 1]);
                if (height[left] < max_left)
                    result += (max_left - height[left]);
                left++;
            } else {
                max_right = Math.max(max_right, height[right + 1]);
                if (height[right] < max_right)
                    result += (max_right - height[right]);
                right--;
            }
        }
        return result;
    }

    //时间On，空间O1
    public static int trap_pro(int[] height) {
        int len = height.length;
        int result = 0;
        int max_left = height[0];
        int[] max_right = new int[len];
        for (int i = len - 2; i >= 0; i--)
            max_right[i] = Math.max(max_right[i + 1], height[i + 1]);
        for (int i = 1; i < len - 1; i++) {
            max_left = Math.max(max_left, height[i - 1]);
            int min = Math.min(max_left, max_right[i]);
            if (min > height[i])
                result += min - height[i];
        }
        return result;
    }

    public static int trap(int[] height) {
        int result = 0;
        int max = height[0]; // 假设第一个元素是最大值
        for (int i = 1; i < height.length; i++)
            max = Math.max(max, height[i]);

        int left = 0;
        int rigtht = height.length - 1;
        for (int i = 0; i < max && left < rigtht; i++) {
            while (height[left] < i + 1)
                left++;
            while (height[rigtht] < i + 1)
                rigtht--;
            for (int j = left + 1; j < rigtht; j++) {
                if (height[j] < i + 1)
                    result++;
            }
        }
        return result;
    }
}
