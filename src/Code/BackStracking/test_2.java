package Code.BackStracking;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description 第77题. 组合
 * @Author 12919
 * @Date 2024/12/7
 */
public class test_2 {



    public List<List<Integer>> combine(int n, int k) {

        List<List<Integer>> result = new ArrayList<>();
        if (n < 0 || n < k)
            return result;
        Deque<Integer> deque = new LinkedList<>();
        backstracking(n, k, 1,result,deque);
        return result;
    }

    private void backstracking(int n, int k, int startIndex, List<List<Integer>> result, Deque<Integer> deque) {
        if(deque.size()==k){
            result.add(new ArrayList<>(deque));
            return;
        }
        for (int i=startIndex;i<=n-(k-deque.size())+1;i++){
            deque.addLast(i);
            backstracking(n,k,i+1,result,deque);
            deque.removeLast();
        }
    }
}
