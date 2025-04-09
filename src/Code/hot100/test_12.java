package Code.hot100;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @Description 98. 验证二叉搜索树
 * @Author 12919
 * @Date 2025/2/21
 */
//
public class test_12 {
    public static void main(String[] args) {

    }
    static long pre=Long.MIN_VALUE;
    public static boolean isValidBST_pro(TreeNode root) {
        if(root==null)
            return true;
        if(!isValidBST_pro(root.left))
            return false;
        if(root.val<=pre)
            return false;
        pre=root.val;
        return isValidBST_pro(root.right);
    }




    public static boolean isValidBST(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        BFS(root, list);
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i-1) >= list.get(i ))
                return false;
        }
        return true;
    }

    public static void BFS(TreeNode root, List<Integer> list) {
        if (root == null) return;
        BFS(root.left, list);
        list.add(root.val);
        BFS(root.right, list);

    }

}