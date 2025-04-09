package Code.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author 12919
 * @Date 2024/12/6
 */

public class demo_tree_3_1_1 {


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


    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preorder(root,result);
        return result;

    }


    public void preorder(TreeNode root, List<Integer> result) {
        if (root == null)
            return;
        result.add(root.val );
        preorder(root.left,result);
        preorder(root.right,result);

    }


}
