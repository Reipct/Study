package Code.hot150;

/**
 * @Description 238. 除自身以外数组的乘积
 * @Author 12919
 * @Date 2025/4/3
 */
public class Test_34 {
    public int[] productExceptSelf(int[] nums) {
        int len = nums.length;
        int[] pre = new int[len];
        int tmp = 1;
        pre[0] = 1;
        for (int i = 1; i < len; i++) {
            pre[i] = pre[i - 1] * nums[i - 1];
        }
        for (int i = len - 2; i >= 0; i--) {
            tmp *= nums[i + 1];
            pre[i] *= tmp;
        }
        return pre;
    }
}