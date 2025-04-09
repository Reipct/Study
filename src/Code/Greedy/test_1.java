package Code.Greedy;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.CompareGenerator;

import java.util.Arrays;
import java.util.Collections;

/**
 * @Description 455.分发饼干
 * @Author 12919
 * @Date 2024/12/13
 */
public class test_1 {
    public static void main(String[] args) {
        int[] g = {1, 2, 3};
        int[] s = {1, 1};
        System.out.println(findContentChildren(g, s));
    }


    public static int findContentChildren(int[] g, int[] s) {
        if (s.length == 0)
            return 0;
        Arrays.sort(g);
        Arrays.sort(s);
        int s_i = s.length - 1;
        for (int i = g.length - 1; i >= 0 && s_i >= 0; i--) {
            if (g[i] <= s[s_i])
                s_i--;
        }
        return s.length - s_i - 1;
    }
}
