package Code.hot150;

/**
 * @Description TODO
 * @Author 12919
 * @Date 2025/3/10
 */
public class Test_14 {

    public static void main(String[] args) {

        int[] nums = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        Test_14 test_14 = new Test_14();
        System.out.println(test_14.trap(nums));
    }

    public int trap(int[] height) {

        int len = height.length;
        int max_left = 0;
        int max_right = 0;
        int left = 1;
        int right = len - 2;
        int result = 0;
        for (int i = 1; i < len - 1; i++) {
            if (height[left - 1] <= height[right + 1]) {
                max_left = Math.max(max_left, height[left - 1]);
                if (height[left] < max_left)
                    result += (max_left - height[left]);
                left++;
            } else {
                max_right = Math.max(max_right, height[right + 1]);
                if (height[right] < max_right)
                    result += (max_right - height[right]);
                right--;
            }
        }
        return result;
    }


}
