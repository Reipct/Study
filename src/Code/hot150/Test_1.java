package Code.hot150;

import java.util.Arrays;

/**
 * @Description 88. 合并两个有序数组
 * @Author 12919
 * @Date 2025/3/2
 */
public class Test_1 {


    public static void main(String[] args) {
        int[] nums1 = {4, 5, 6, 0, 0, 0};
        int m = 3;
        int[] nums2 = {1, 2, 3};
        int n = 3;
//        int[] nums1 = {0};
//        int m = 0;
//        int[] nums2 = {1};
//        int n = 1;
        Test_1 test_1 = new Test_1();
        test_1.merge(nums1, m, nums2, n);
        System.out.println(Arrays.toString(nums1));
    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int index_1 = m - 1;
        int index_2 = n - 1;
        int index_res = m + n - 1;
        while (index_1 >= 0 && index_2 >= 0) {
            if (nums1[index_1] <= nums2[index_2])
                nums1[index_res--] = nums2[index_2--];
            else
                nums1[index_res--] = nums1[index_1--];
        }
        if (index_1 == -1) {
            for (int i = index_2; i >= 0; i--) {
                nums1[index_res--] = nums2[i];
            }
        }
    }
}
