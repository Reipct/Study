package Code.hot100;

/**
 * @Description 11. 盛最多水的容器
 * @Author 12919
 * @Date 2025/2/24
 */
//给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
//找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
//返回容器可以储存的最大水量。
//输入：[1,8,6,2,5,4,8,3,7]
//输出：49
//解释：图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
public class test_16 {
    public static void main(String[] args) {
        int[] nums = new int[]{1,8,6,2,5,4,8,3,7};
        System.out.println(maxArea(nums));
    }

    public static int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int MaxArea = 0;
        while (left < right) {
            int area = Math.min(height[right], height[left]) * (right - left);
            MaxArea = Math.max(area, MaxArea);
            if (height[left] <= height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return MaxArea;
    }
}
