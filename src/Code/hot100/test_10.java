package Code.hot100;

/**
 * @Description 108. 将有序数组转换为二叉搜索树
 * @Author 12919
 * @Date 2025/2/19
 */
public class test_10 {

    public static TreeNode sortedArrayToBST(int[] nums) {
        return fun(nums, 0, nums.length - 1);
    }

    public static TreeNode fun(int[] nums, int left, int right) {
        if (left > right)
            return null;
        int mid = (left + right) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = fun(nums, left, mid - 1);
        root.right = fun(nums, mid + 1, right);
        return root;

    }
}
