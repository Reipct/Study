package Code.hot100;

/**
 * @Description 74. 搜索二维矩阵
 * @Author 12919
 * @Date 2025/2/23
 */
//给你一个满足下述两条属性的 m x n 整数矩阵：
//每行中的整数从左到右按非严格递增顺序排列。
//每行的第一个整数大于前一行的最后一个整数。
//给你一个整数 target ，如果 target 在矩阵中，返回 true ；否则，返回 false 。
// 输入：matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
//输出：true
public class test_14 {
    public static void main(String[] args) {
//        int[][] matrix = new int[][]{{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};
        int[][] matrix = new int[][]{{1, 3}};
        System.out.println(searchMatrix(matrix, 3));
    }

    public static boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n=matrix[0].length;
        if (target < matrix[0][0] || target > matrix[m - 1][ n- 1]) return false;
        int row_target = searchInsert(matrix, target);
        return search_Col(matrix[row_target], target);
    }


    public static int searchInsert(int[][] matrix, int target) {
        int left = 0;
        int right = matrix.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (matrix[mid][0] == target) return mid;
            else if (matrix[mid][0] < target) left = mid + 1;
            else right = mid - 1;
        }
        return left - 1;
    }

    public static boolean search_Col(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target) return true;
            else if (nums[mid] < target) left = mid + 1;
            else right = mid - 1;

        }
        return false;
    }
}
