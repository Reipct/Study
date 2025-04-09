package Code.Dp;

/**
 * @Description 509. 斐波那契数
 * @Author 12919
 * @Date 2024/12/14
 */
public class test_1 {
    public static void main(String[] args) {

    }

    public int fib(int n) {
        if (n == 0 || n == 1)
            return n;
        int[] dp = new int[n];
        dp[0] = dp[1] = 1;
        for (int i = 2; i < n; i++)
            dp[i] = dp[i - 1] + dp[i - 2];
        return dp[n - 1];
    }

}
