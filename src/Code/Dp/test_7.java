package Code.Dp;

/**
 * @Description 343. 整数拆分
 * @Author 12919
 * @Date 2024/12/14
 */
public class test_7 {
    public static void main(String[] args) {
        System.out.println(integerBreak(10));
    }

    public static int integerBreak(int n) {
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 1;
        for (int i = 3; i <= n; i++) {
            dp[i]=i-1;
            for (int j = i - 2; j >= i / 2; j--)
                dp[i] = Math.max(dp[i], Math.max(j, dp[j]) * Math.max(i - j, dp[i - j]));
        }
        return dp[n];
    }
}
