package Code.hot150;

import java.util.Arrays;

/**
 * @Description TODO
 * @Author 12919
 * @Date 2025/3/19
 */
public class Test_28 {
    public static void main(String[] args) {
        int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};
        Test_28 test_28 = new Test_28();
        System.out.println(test_28.lengthOfLIS(nums));
    }

    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        int res = 0;
        for (int num : nums) {
            int i = 0;
            int j = res;
            while (i < j) {
                int mid = i + (j - i) / 2;
                if (dp[mid] < num) i = mid + 1;
                else j = mid;
            }
            dp[i] = num;
            if (res == j)
                res++;
        }
        return res;
    }


    public int lengthOfLIS_primary(int[] nums) {
        if (nums.length == 0)
            return 0;
        int[] dp = new int[nums.length];
        int res = 0;
        Arrays.fill(dp, 1);
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            res = Math.max(dp[i], res);
        }
        return res;
    }
}
