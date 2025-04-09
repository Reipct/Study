package Code.Dp;

/**
 * @Description 53. 最大子序和
 * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
 * 输出：6
 * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
 * @Author 12919
 * @Date 2024/12/15
 */
public class test_8 {
    public static void main(String[] args) {
//        int[] num = new int[]{0, -2, -3};
        int[] num = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(maxSubArray(num));
    }


    public static int maxSubArray(int[] nums) {
        int len = nums.length;
        int[][] dp = new int[len][len];
        int max = nums[0];
        for (int i = 0; i < len; i++) {
            dp[i][i] = nums[i];
            max = Math.max(dp[i][i], max);
        }
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                dp[i][j] = dp[i][j - 1] + dp[j][j];
                max = Math.max(dp[i][j], max);
            }
        }
        return max;
    }

}
