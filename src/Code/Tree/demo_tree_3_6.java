package Code.Tree;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Description 101. 对称二叉树
 * @Author 12919
 * @Date 2024/12/6
 */

public class demo_tree_3_6 {

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
    public boolean isSymmetric(TreeNode root) {
        if(root==null)
            return false;
        return dfs(root.left,root.right);

    }

    boolean dfs(TreeNode left,TreeNode right){
        if(left==null&&right==null)
            return true;
        if (left==null||right==null)
            return false;
        if(left.val!= right.val)
            return false;
        return dfs(left.left,right.right)&&dfs(left.right,right.left);
    }
}
