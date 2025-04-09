package Code.Greedy;

import org.omg.IOP.TAG_RMI_CUSTOM_MAX_STREAM_FORMAT;

/**
 * @Description 53. 最大子序和
 * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
 * 输出：6
 * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
 * @Author 12919
 * @Date 2024/12/15
 */
public class test_3 {
    public static void main(String[] args) {
        int[] num = new int[]{0, -2, -3};
//        int[] num = new int[]{ -2,-1};
        System.out.println(maxSubArray_pro(num));
    }


    public static int maxSubArray(int[] nums) {

        int pre_res = nums[0];
        int cur_res = nums[0];
        for (int i = 1; i < nums.length; i++) {
            cur_res = cur_res + nums[i];
            if (cur_res < nums[i]) {
                cur_res = Math.max(nums[i], cur_res - nums[i]);
                pre_res = Math.max(pre_res, cur_res);
            }
            if (pre_res <= cur_res)
                pre_res = cur_res;
        }
        return pre_res;
    }

    public static int maxSubArray_pro(int[] nums) {
        int res =Integer.MIN_VALUE;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            count += nums[i];
            if (res < count)
                res = count;
            if (count <= 0) count = 0;
        }
        return res;
    }


}
