package Code.hot150;


/**
 * @Description TODO
 * @Author 12919
 * @Date 2025/4/3
 */
public class Test_33 {

    public int rob(TreeNode root) {
        int[] result = rotFun(root);
        return Math.max(result[0], result[1]);
    }

    public int[] rotFun(TreeNode root) {
        if (root == null) return new int[2];
        int[] result = new int[2];
        int[] left = rotFun(root.left);
        int[] right = rotFun(root.right);
        result[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        result[1] = root.val + left[0] + right[0];
        return new int[]{result[0], result[1]};
    }
}
