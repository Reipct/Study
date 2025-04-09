package Code.Greedy;

/**
 * @Description 55. 跳跃游戏
 * 输入：nums = [2,3,1,1,4]
 * 输出：true
 * 解释：可以先跳 1 步，从下标 0 到达下标 1, 然后再从下标 1 跳 3 步到达最后一个下标。
 * @Author 12919
 * @Date 2024/12/15
 */
public class test_5 {
    public static void main(String[] args) {
        int[] nums = new int[]{3, 2, 1, 0, 4};
        System.out.println(canJump(nums));
    }

    public static boolean canJump(int[] nums) {
        int i = 0;
        while (i < nums.length) {
            int step = nums[i];
            int i_max = 0;
            for (int j = i + step; j > i; j--) {
                if (j >= nums.length)
                    return true;
                if (nums[i] < nums[j]) {
                    nums[i] = nums[j];
                    i_max = j;
                }
            }
            i = i_max;
        }
        return false;
    }
}
