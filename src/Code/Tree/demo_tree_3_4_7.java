package Code.Tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Description 117.填充每个节点的下一个右侧节点指针II
 * @Author 12919
 * @Date 2024/12/7
 */
public class demo_tree_3_4_7 {
    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }

    public Node connect(Node root) {
        if (root == null)
            return null;
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int len = queue.size();
            for (int i = 0; i < len; i++) {
                Node temp = queue.poll();
                if (i < len - 1)
                    temp.next = queue.peek();
                else
                    temp.next = null;
                if (temp.left != null)
                    queue.offer(temp.left);
                if (temp.right != null)
                    queue.offer(temp.right);
            }
        }
        return root;
    }


}
