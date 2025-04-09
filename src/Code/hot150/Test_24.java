package Code.hot150;

import java.util.Arrays;

/**
 * @Description TODO
 * @Author 12919
 * @Date 2025/3/14
 */
public class Test_24 {
    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int m = 3;
        int[] nums2 = {2, 5, 6};
        int n = 3;
        Test_24 test_24 = new Test_24();
        test_24.merge(nums1, m, nums2, n);
        System.out.println(Arrays.toString(nums1));
    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {

        int index1 = m - 1;
        int index2 = n - 1;
        int index_all = m + n - 1;
        while (index1 >= 0 && index2 >= 0) {
            if (nums1[index1] > nums2[index2])
                nums1[index_all--] = nums1[index1--];
            else
                nums1[index_all--] = nums2[index2--];
        }
        if (index1 == -1) {
            for (int i = index2; i >= 0; i--) {
                nums1[index_all--] = nums2[i];
            }
        }
    }
}