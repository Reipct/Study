package Code.hot150;

/**
 * @Description 153. 寻找旋转排序数组中的最小值
 * @Author 12919
 * @Date 2025/3/13
 */
public class Test_20 {
    public static void main(String[] args) {
        int[] nums = {3, 4, 5, 1, 2};
        Test_20 test_20 = new Test_20();
        System.out.println(test_20.findMin(nums));
    }

    public int findMin_pro(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return nums[left];
    }

    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            if (nums[left] < nums[right])
                return nums[left];
            else {
                int mid = (left + right) / 2;
                if (nums[mid] > nums[right])
                    left = mid;
                else
                    right = mid;
                left++;
            }
        }
        return left;
    }
}
