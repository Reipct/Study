package Code.hot150;

/**
 * @Description TODO
 * @Author 12919
 * @Date 2025/3/13
 */
public class Test_21 {
    public static void main(String[] args) {
        Test_21 test_21 = new Test_21();
    }

    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null)
            return false;
        if (root.left == null && root.right == null)
            return root.val == targetSum;
        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }


}
