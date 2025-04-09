package Code.hot150;

import java.util.Deque;

/**
 * @Description TODO
 * @Author 12919
 * @Date 2025/3/10
 */
public class Test_15 {
    public static void main(String[] args) {
        String[] strs = {"flower", "flow", "flight"};
        Test_15 test_15 = new Test_15();
        System.out.println(test_15.longestCommonPrefix(strs));
    }

    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        StringBuilder samestr = new StringBuilder();
        for (int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if (strs[j].length() <= i || strs[j].charAt(i) != c) {
                    return samestr.toString();
                }
            }
            samestr.append(c);
        }
        return samestr.toString();
    }
}
