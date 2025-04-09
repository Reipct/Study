package Code.hot150;

import java.util.*;

/**
 * @Description TODO
 * @Author 12919
 * @Date 2025/3/11
 */
public class Test_18 {
    public static void main(String[] args) {
        String s = "leetcode";
        List<String> wordDict = new ArrayList<>();
        Collections.addAll(wordDict, "leet", "code");
        Test_18 test_18 = new Test_18();
        System.out.println(test_18.wordBreak(s, wordDict));
    }

    //动态规划
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> set = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && set.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }


    //回溯算法
    // 超出时间限制，35 / 47 个通过的测试用例

//    public boolean wordBreak(String s, List<String> wordDict) {
//        return backstarcking(s, 0, wordDict);
//    }
//
//    public boolean backstarcking(String s, int index, List<String> wordDict) {
//        if (index == s.length())
//            return true;
//        for (int i = index; i < s.length(); i++) {
//            if (wordDict.contains(s.substring(index, i + 1))) {
//                if (backstarcking(s, i + 1, wordDict))
//                    return true;
//            }
//        }
//        return false;
//    }
}
