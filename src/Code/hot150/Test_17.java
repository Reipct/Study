package Code.hot150;

import java.util.Arrays;

/**
 * @Description TODO
 * @Author 12919
 * @Date 2025/3/11
 */
public class Test_17 {
    public static void main(String[] args) {
//        int[] nums = {1, 1, 1, 2, 2, 3};
        int[] nums = {0, 0, 1, 1, 1, 1, 2, 3, 3};
        Test_17 test_17 = new Test_17();
        System.out.println(test_17.removeDuplicates(nums));

    }

    public int removeDuplicates(int[] nums) {
        int len = nums.length;
        int curr = 0;
        int pre = 0;
        while (pre < len) {
            nums[curr++] = nums[pre++];
            if (pre < len && nums[pre] == nums[pre - 1]) {
                nums[curr++] = nums[pre++];
                while (pre < len && nums[pre] == nums[pre - 1])
                    pre++;
            }
        }
//        for (int i = 0; i < curr; i++) {
//            System.out.print(nums[i] + " ");
//        }
//        System.out.println();
        return curr;
    }
}