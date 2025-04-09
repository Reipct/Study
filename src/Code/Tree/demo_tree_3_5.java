package Code.Tree;

/**
 * @Description 226.翻转二叉树
 * @Author 12919
 * @Date 2024/12/6
 */

public class demo_tree_3_5 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

//    public TreeNode invertTree(TreeNode root) {
//        if (root == null)
//            return null;
//        invertFun(root);
//        return root;
//
//    }
//
//    public static void invertFun(TreeNode root) {
//        if(root==null)
//            return;
//        if (root.left == null && root.right == null)
//            return;
//        TreeNode temp = root.left;
//        root.left = root.right;
//        root.right = temp;
//        invertFun(root.left);
//        invertFun(root.right);
//    }


    public TreeNode invertTree(TreeNode root) {
        if (root == null)
            return null;

        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        invertTree(root.left);
        invertTree(root.right);
        return root;

    }
}
