package Code.Dp;

/**
 * @Description 746. 使用最小花费爬楼梯
 * @Author 12919
 * @Date 2024/12/14
 */
public class test_4 {
    public static void main(String[] args) {

    }

    public int minCostClimbingStairs(int[] cost) {
        if (cost.length == 0)
            return 0;
        if (cost.length == 1)
            return cost[0];
        int[] dp = new int[cost.length + 1];
        dp[0] = dp[1] = 0;
        for (int i = 2; i < dp.length; i++) {
            dp[i] = Math.min(dp[i - 2] + cost[i - 2], dp[i - 1] + cost[i - 1]);
        }
        return dp[dp.length - 1];
    }

    public int minCostClimbingStairs_pro(int[] cost) {
        if (cost.length == 0)
            return 0;
        if (cost.length == 1)
            return cost[0];
        int p = 0;
        int q = 0;
        for (int i = 2; i < cost.length + 1; i++) {
            p = Math.min(p + cost[i - 2], q + cost[i - 1]);
            p = p + q;
            q = p - q;
            p = p - q;
        }
        return q;
    }
}
