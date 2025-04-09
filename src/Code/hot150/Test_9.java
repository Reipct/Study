package Code.hot150;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 55. 跳跃游戏
 * @Author 12919
 * @Date 2025/3/4
 */
public class Test_9 {
    public static void main(String[] args) {
        int[] nums = {3, 2, 1, 0, 4};
        Test_9 test_9 = new Test_9();
        System.out.println(test_9.canJump(nums));
    }

    public boolean canJump(int[] nums) {
        int maxlen = 0;
        for (int i = 0; i < nums.length; i++) {
            if (maxlen < i)
                return false;
            maxlen = Math.max(maxlen, nums[i] + i);
        }
        return maxlen >= nums.length - 1;
    }

}
