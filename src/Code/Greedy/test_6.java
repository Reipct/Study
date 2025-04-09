package Code.Greedy;

/**
 * @Description 55. 跳跃游戏
 * @Author 12919
 * @Date 2025/1/30
 */
//输入：nums = [2,3,1,1,4]
//输出：true
//解释：可以先跳 1 步，从下标 0 到达下标 1, 然后再从下标 1 跳 3 步到达最后一个下标。
//[5,9,3,2,1,0,2,3,3,1,0,0]
//输入：nums = [3,2,1,0,4]
//输出：false
//解释：无论怎样，总会到达下标为 3 的位置。但该下标的最大跳跃长度是 0 ， 所以永远不可能到达最后一个下标。

public class test_6 {
    public static void main(String[] args) {
        int[] nums = {1, 0, 1, 0};
        System.out.println(canJump_pro(nums));
    }

    //每次取该范围内能跳到的最大长度
    public static boolean canJump(int[] nums) {
        if (nums.length == 1)
            return true;
        if (nums[0] == 0)
            return false;
        int left = 0;
        int right = 0;
        while (left <= right && right < nums.length) {
            for (int i = left + 1; i < left + 1 + nums[left] && i < nums.length; i++) {
                right = Math.max(right, i + nums[i]);
                if (right >= nums.length - 1)
                    return true;
            }
            left = nums[left] + left + 1;
        }
        return false;
    }


    public static boolean canJump_pro(int[] nums) {

        int maxlen = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i <= maxlen) {
                maxlen = Math.max(maxlen, nums[i] + i);
                if (maxlen >= nums.length - 1)
                    return true;
            }
        }
        return false;
    }
}
