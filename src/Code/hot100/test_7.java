package Code.hot100;

/**
 * @Description 543.二叉树的直径
 * @Author 12919
 * @Date 2025/2/18
 */
public class test_7 {
    public static void main(String[] args) {

    }


    public static int diameterOfBinaryTree(TreeNode root) {
        depth(root);
        return k - 1;
    }
    static int k = 1;
    public static int depth(TreeNode root) {
        if (root == null)
            return 0;
        int left = depth(root.left);
        int right = depth(root.right);

        k = Math.max(left + right + 1, k);

        return Math.max(left, right) + 1;
    }



}
