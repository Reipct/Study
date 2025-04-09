package Code.hot150;

/**
 * @Description 530. 二叉搜索树的最小绝对差
 * @Author 12919
 * @Date 2025/3/19
 */
public class Test_30 {
    public static void main(String[] args) {

    }
    int pre;
    int res;

    public int getMinimumDifference(TreeNode root) {
        res = Integer.MAX_VALUE;
        pre = -1;
        DFS(root);
        return res;

    }
    public void DFS(TreeNode root) {
        if (root == null) return;
        DFS(root.left);
        if (pre == -1) pre = root.val;
        else {
            res = Math.min(root.val - pre, res);
            pre = root.val;
        }
        DFS(root.right);
    }
}
