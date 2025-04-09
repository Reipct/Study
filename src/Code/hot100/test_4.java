package Code.hot100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @Description 54.螺旋数组
 * @Author 12919
 * @Date 2025/2/16
 */
//给你一个 m 行 n 列的矩阵 matrix ，请按照 顺时针螺旋顺序 ，返回矩阵中的所有元素。
public class test_4 {

    public static void main(String[] args) {
        int[][] nums = new int[][]{{6, 9, 7}};
        //        int[][] nums = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        System.out.println(spiralOrder(nums));
    }

    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> list = new ArrayList<>();
        if (matrix.length == 0) return null;
        int rows = matrix.length, cols = matrix[0].length;
        int left = 0, right = cols - 1, top = 0, bottom = rows - 1;
        while (left <= right && top <= bottom) {
            for (int column = left; column <= right; column++) {
                list.add(matrix[top][column]);
            }
            for (int row = top + 1; row <= bottom; row++) {
                list.add(matrix[row][right]);
            }
            if (left < right && top < bottom) {
                for (int column = right - 1; column > left; column--) {
                    list.add(matrix[bottom][column]);
                }
                for (int row = bottom; row > top; row--) {
                    list.add(matrix[row][left]);
                }
            }
            left++;
            right--;
            top++;
            bottom--;
        }
        return list;
    }
}