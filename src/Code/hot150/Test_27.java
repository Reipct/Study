package Code.hot150;

import Code.hot150.TreeNode;

/**
 * @Description 二叉树的最近公共祖先
 * @Author 12919
 * @Date 2025/3/19
 */
public class Test_27 {
    public static void main(String[] args) {

    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left == null) return right;
        if (right == null) return left;
        return root;
    }

}