package Code.hot150;

import java.util.Arrays;

/**
 * @Description 167. 两数之和 II - 输入有序数组
 * @Author 12919
 * @Date 2025/3/3
 */
//输入：numbers = [2,7,11,15], target = 9
//输出：[1,2]
//解释：2 与 7 之和等于目标数 9 。因此 index1 = 1, index2 = 2 。返回 [1, 2] 。
public class Test_6 {
    public static void main(String[] args) {
        int[] numbers = new int[]{2, 7, 11, 15};
        int target = 9;
        Test_6 test_6 = new Test_6();
        System.out.println(Arrays.toString(test_6.twoSum(numbers, target)));

    }

    public int[] twoSum(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;
        while (left < right) {
            if (numbers[left] + numbers[right] == target)
                return new int[]{left+1, right+1};
            else if (numbers[left] + numbers[right] < target)
                left++;
            else
                right--;
        }
        return new int[]{-1, -1};
    }
}
