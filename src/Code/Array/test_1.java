package Code.Array;

import java.util.Arrays;

/**
 * @Description 59.螺旋矩阵II
 * @Author 12919
 * @Date 2025/2/3
 */
public class test_1 {
    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(generateMatrix(5)));
    }

    public static int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        int start_x = 0;
        int start_y = 0;
        int offset = 1;
        int count = 1;
        int temp = n;
        int i = 0;
        int j = 0;
        while (temp > 1) {
            for (j = start_y; j < n - offset; j++)
                matrix[start_x][j] = count++;
            for (i = start_x; i < n - offset; i++)
                matrix[i][j] = count++;
            for (; j >= offset; j--)
                matrix[i][j] = count++;
            for (; i >= offset; i--)
                matrix[i][j] = count++;
            start_x++;
            start_y++;
            temp -= 2;
            offset++;
        }
        if (n % 2 == 1)
            matrix[start_x][start_y] = count;
        return matrix;
    }

}
