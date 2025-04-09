package Code.Dp;

/**
 * @Description 70. 爬楼梯
 * @Author 12919
 * @Date 2024/12/14
 */
public class test_2 {
    public static void main(String[] args) {

    }

    public int fib(int n) {
        if (n == 1 || n == 2)
            return n;
        int[] dp = new int[n];
        dp[0] = 1;
        dp[1] = 2;
        for (int i = 2; i < n; i++)
            dp[i] = dp[i - 1] + dp[i - 2];
        return dp[n - 1];
    }

}
