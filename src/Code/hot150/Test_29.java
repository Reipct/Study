package Code.hot150;

import java.util.Arrays;

/**
 * @Description 27. 移除元素
 * @Author 12919
 * @Date 2025/3/19
 */
public class Test_29 {
    public static void main(String[] args) {
        int[] nums = {3, 2, 2, 3};
        int val = 3;
        Test_29 test_29 = new Test_29();
        System.out.println(test_29.removeElement(nums, val));
        System.out.println(Arrays.toString(nums));
    }

    public int removeElement(int[] nums, int val) {
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[index++] = nums[i];
            }
        }
        return index;
    }
}
