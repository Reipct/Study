package Code.hot150;

import java.util.Scanner;

/**
 * @Description 221. 最大正方形
 * @Author 12919
 * @Date 2025/4/10
 */
public class Test_38 {
    public static void main(String[] args) {
        Test_38 test_38 = new Test_38();
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();
        int n = sc.nextInt();
        sc.nextLine();
        char[][] matrix = new char[m][n];
        for (int i = 0; i < m; i++) {
            String[] strings = sc.nextLine().split(",");
            for (int j = 0; j < n; j++) {
                matrix[i][j] = strings[j].charAt(0);
            }
        }

        System.out.println(test_38.maximalSquare(matrix));
    }

    public int maximalSquare(char[][] matrix) {
        int max = 0;
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
            return max;
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    if (i == 0 || j == 0)
                        dp[i][j] = 1;
                    else
                        dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
                    max = Math.max(max, dp[i][j]);
                }
            }
        }
        return max * max;
    }
}
