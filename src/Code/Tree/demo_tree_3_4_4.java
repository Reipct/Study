package Code.Tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Description 429.N叉树的层序遍历
 * @Author 12919
 * @Date 2024/12/7
 */
public class demo_tree_3_4_4 {
    class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null)
            return result;

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            List<Integer> list = new ArrayList<>();
            int len = queue.size();

            while (len > 0) {
                Node Ntree = queue.poll();
                list.add(Ntree.val);
                for (Node node : Ntree.children)
                    queue.offer(node);
                len--;
            }
            result.add(list);
        }
        return result;
    }
}
