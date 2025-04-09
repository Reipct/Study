package Code.hot100;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 279.完全平方数
 * @Author 12919
 * @Date 2025/2/18
 */
//给你一个整数 n ，返回 和为 n 的完全平方数的最少数量 。
//完全平方数 是一个整数，其值等于另一个整数的平方；换句话说，其值等于一个整数自乘的积。例如，1、4、9 和 16 都是完全平方数，而 3 和 11 不是。
//输入：n = 12
//输出：3
//解释：12 = 4 + 4 + 4
public class test_6 {
    public static void main(String[] args) {
        System.out.println(numSquares_pro(13));
    }


    public static int numSquares_pro(int n) {
        int[] dp = new int[n + 1];
        for (int i = 0; i < n + 1; i++) {
            dp[i] = i;
            for (int j = 1; i - j * j >= 0; j++) {
                dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
            }
        }
        return dp[n];
    }


    public static int numSquares(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 0;
        for (int i = 1; i < n + 1; i++) {
            if (isPow(i)) {
                dp[i] = 1;
            } else {
                dp[i] = i;
                for (int j = 1; j <= i / 2; j++) {
                    dp[i] = Math.min(dp[j] + dp[i - j], dp[i]);
                }
            }
        }
        return dp[n];
    }

    public static boolean isPow(int x) {
        int sqrt = (int) Math.sqrt(x);
        if (x == Math.pow(sqrt, 2)) return true;
        else return false;
    }

}
