package Code.hot150;

/**
 * @Description 45. 跳跃游戏 II
 * @Author 12919
 * @Date 2025/4/7
 */
public class Test_36 {
    public static void main(String[] args) {
        int[] nums = {2, 3, 0, 1, 4};
        Test_36 test_36 = new Test_36();
        System.out.println(test_36.jump(nums));
    }

    public int jump(int[] nums) {
        int len = nums.length;
        if(len==1)
            return 0;
        int next = 0;
        int maxlen = 0;
        int count = 1;
        for (int i = 0; i < len; i++) {
            if (maxlen >= len - 1) break;
            if (i > next) {
                next = maxlen;
                count++;
            }
            maxlen = Math.max(maxlen, i + nums[i]);
        }
        return count;
    }
}
