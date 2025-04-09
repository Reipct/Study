package Code.Dp;

import java.util.Scanner;

/**
 * @Description TODO
 * @Author 12919
 * @Date 2025/1/31
 */
public class test_10 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int Bag_weight = sc.nextInt();
        int[] weigth = new int[n];
        int[] value = new int[n];
        for (int i = 0; i < n; i++)
            weigth[i] = sc.nextInt();
        for (int i = 0; i < n; i++)
            value[i] = sc.nextInt();
        System.out.println(Bag01(Bag_weight, n, weigth, value));
    }

    public static int Bag01(int Bag_weight, int n, int[] weight, int[] value) {
        int[][] dp = new int[n][Bag_weight + 1];
        for (int i = 0; i < n; i++)
            dp[i][0] = 0;
        for (int i = 1; i < weight[0]; i++)
            dp[0][i] = 0;
        for (int i = weight[0]; i <= Bag_weight; i++)
            dp[0][i] = weight[0];
        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= Bag_weight; j++) {
                if (j < weight[i]) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i]] + value[i]);
                }
            }
        }
        return dp[n - 1][Bag_weight];
    }
}
