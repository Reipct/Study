package Code.Dp;

/**
 * @Description 96.不同的二叉搜索树
 * @Author 12919
 * @Date 2025/1/25
 */
public class test_9 {


    public static void main(String[] args) {
        System.out.println(numTrees(3));
    }


    public static int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = dp[1] = 1;
        for (int i = 2; i <= n; i++)
            for (int j = 1; j <= i; j++)
                dp[i] += dp[j - 1] * dp[i - j];
        return dp[n];
    }

}