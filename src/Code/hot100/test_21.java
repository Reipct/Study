package Code.hot100;

/**
 * @Description TODO
 * @Author 12919
 * @Date 2025/2/27
 */
public class test_21 {
    public static void main(String[] args) {
        int[] nums = new int[]{3, 4, -1, 1};
        System.out.println(firstMissingPositive(nums));
    }

    public static int firstMissingPositive(int[] nums) {
        int N = nums.length;

        for (int i = 0; i < N; i++) {
            if (nums[i] <= 0)
                nums[i] = N + 1;
        }
        for (int i = 0; i < N; i++) {
            int nums_i = Math.abs(nums[i]);
            if (nums_i <= N) {
                nums[nums_i - 1] = -Math.abs(nums[nums_i - 1]);
            }
        }
        int index;
        for (index = 0; index < N; index++) {
            if (nums[index] > 0)
                return index + 1;
        }
        return N + 1;
    }
}
