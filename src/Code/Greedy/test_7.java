package Code.Greedy;

/**
 * @Description 45.跳跃游戏 II
 * @Author 12919
 * @Date 2025/1/31
 */
public class test_7 {

    public static void main(String[] args) {
        int[] nums = {2, 3, 1, 1, 4};
        System.out.println(jump_pro(nums));
    }

    public static int jump(int[] nums) {
        int pos = nums.length - 1;
        int step = 0;
        while (pos > 0) {
            for (int i = 0; i < pos; i++) {
                if (i + nums[i] >= pos) {
                    pos = i;
                    step++;
                    break;
                }
            }
        }
        return step;
    }
    public static int jump_pro(int[] nums) {
        int len = nums.length;
        int end = 0;
        int maxpos = 0;
        int step = 0;
        for (int i = 0; i < len-1; i++) {
            maxpos = Math.max(maxpos, i + nums[i]);
            if (i == end) {
                end = maxpos;
                step++;
            }
        }
        return step;
    }
}