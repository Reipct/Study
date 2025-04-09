package Code.hot150;

/**
 * @Description TODO
 * @Author 12919
 * @Date 2025/3/14
 */
public class Test_23 {
    public static void main(String[] args) {
        int[] nums = {2, 2, 3, 2};
        Test_23 test_23 = new Test_23();
        System.out.println(test_23.singleNumber(nums));
    }

    public int singleNumber(int[] nums) {
        int ones = 0;
        int twos = 0;
        for (int i : nums) {
            ones = ones ^ i & ~twos;
            twos = twos ^ i & ~ones;
        }
        return ones;
    }
}
