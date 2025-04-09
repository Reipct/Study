package Code.Tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Description 111.二叉树的最小深度
 * @Author 12919
 * @Date 2024/12/6
 */

public class demo_tree_3_4_9 {


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


    public int minDepth(TreeNode root) {
        if (root == null)
            return 0;
        if (root.left == null && root.right == null)
            return 1;

        int left = minDepth(root.left);
        int right = minDepth(root.right);
        if (root.left == null || root.right == null)
            return left + right + 1;

        return Math.min(left,right)+1;

    }
}
