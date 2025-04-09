package Code.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author 12919
 * @Date 2024/12/6
 */

public class demo_tree_3_4_8 {


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


    public int maxDepth(TreeNode root) {
        if (root == null)
            return 0;
        int left = maxDepth(root.left)+1;
        int right = maxDepth(root.right)+1;
        return Math.max(left, right);
    }


}
