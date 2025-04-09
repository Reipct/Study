package Code.Tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Description 515.在每个树行中找最大值
 * @Author 12919
 * @Date 2024/12/6
 */
public class demo_tree_3_4_5 {

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

    public List<Integer> levelOrder(TreeNode root) {
        List<Integer> reslist = new ArrayList<>();
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
            int max = array.get(0);
            for (int i : array)
                if (max < i)
                    max = i;
            reslist.add(max);
        }
        return reslist;
    }
}
