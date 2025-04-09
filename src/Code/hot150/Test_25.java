package Code.hot150;

/**
 * @Description TODO
 * @Author 12919
 * @Date 2025/3/14
 */
public class Test_25 {
    public static void main(String[] args) {
        int[] num = {3, 4, 3, 2, 1};
        Test_25 test_25 = new Test_25();
        System.out.println(test_25.findPeakElement(num));
    }

    public int findPeakElement(int[] nums) {
        int len = nums.length;
        int left = 0;
        int right = len - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[mid + 1]) right = mid;
            else left = mid + 1;
        }
        return left;
    }


//    public int findPeakElement(int[] nums) {
//        if (nums.length == 1)
//            return 0;
//        if (nums[0] > nums[1])
//            return 0;
//        if (nums[nums.length - 1] > nums[nums.length - 2])
//            return nums.length - 1;
//
//        return find(nums, 1, nums.length - 2);
//    }
//
//    public int find(int[] nums, int left, int right) {
//        if (left > right) {
//            return -1;
//        }
//        int mid = left + (right - left) / 2;
//        if (nums[mid] > nums[mid + 1] && nums[mid] > nums[mid - 1])
//            return mid;
//        int l = find(nums, mid + 1, right);
//        if (l != -1)
//            return l;
//        int r = find(nums, left, mid - 1);
//        if (r != -1)
//            return r;
//        return -1;
//    }
}
