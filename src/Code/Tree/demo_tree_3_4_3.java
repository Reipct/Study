package Code.Tree;

import java.util.*;

/**
 * @Description 637.二叉树的层平均值
 * @Author 12919
 * @Date 2024/12/6
 */
public class demo_tree_3_4_3 {

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

    public List<Double> levelOrder(TreeNode root) {
        List<Double> reslist = new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        if (root == null)
            return reslist;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            List<Integer> list = new ArrayList<>();
            int len = queue.size();

            while (len > 0) {
                TreeNode temp = queue.poll();
                list.add(temp.val);
                if (temp.left != null)
                    queue.offer(temp.left);
                if (temp.right != null)
                    queue.offer(temp.right);
                len--;

            }
            result.add(list);

        }

        for (List<Integer> array : result) {
            double sum = 0;
            for (int i : array)
                sum += i;
            reslist.add(sum/array.size());
        }

        return reslist;
    }
}
