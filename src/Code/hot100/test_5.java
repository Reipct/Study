package Code.hot100;

import java.util.Arrays;

/**
 * @Description 73.矩阵清零
 * @Author 12919
 * @Date 2025/2/17
 */
public class test_5 {
    public static void main(String[] args) {
        int[][] matrix = new int[][]{{0, 1, 2, 0}, {3, 4, 5, 2}, {1, 3, 1, 5}};
        System.out.println(Arrays.deepToString(matrix));
        setZeroes(matrix);
        System.out.println(Arrays.deepToString(matrix));

    }

    public static void setZeroes(int[][] matrix) {
        if (matrix.length == 0) return;
        int length_rows = matrix.length;
        int length_cols = matrix[0].length;
        boolean flag_row0 = false, flag_col0 = false;
        for (int i = 0; i < length_cols; i++)
            if (matrix[0][i] == 0)
                flag_row0 = true;
        for (int i = 0; i < length_rows; i++)
            if (matrix[i][0] == 0)
                flag_col0 = true;

        for (int i = 1; i < length_rows; i++) {
            for (int j = 1; j < length_cols; j++)
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
        }
        for (int i = 1; i < length_rows; i++) {
            for (int j = 1; j < length_cols; j++)
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
        }
        if (flag_row0) {
            for (int i = 0; i < length_cols; i++)
                matrix[0][i] = 0;
        }
        if (flag_col0) {
            for (int i = 0; i < length_rows; i++)
                matrix[i][0] = 0;
        }
    }
}
