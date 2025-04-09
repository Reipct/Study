package Code.hot100;

/**
 * @Description 35. 搜索插入位置
 * @Author 12919
 * @Date 2025/2/22
 */
public class test_13 {
    //输入: nums = [1,3,5,6], target = 5
//输出: 2
    public static void main(String[] args) {
        int[] num = {1, 3, 5, 6};
        System.out.println(searchInsert(num, 8));
    }

    public static int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target)
                return mid;
            else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }
}
