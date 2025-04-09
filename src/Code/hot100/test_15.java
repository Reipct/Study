package Code.hot100;

import java.util.*;

/**
 * @Description 763. 划分字母区间
 * @Author 12919
 * @Date 2025/2/23
 */
//给你一个字符串 s 。我们要把这个字符串划分为尽可能多的片段，同一字母最多出现在一个片段中。例如，字符串 "ababcc" 能够被分为 ["abab", "cc"]，
// 但类似 ["aba", "bcc"] 或 ["ab", "ab", "cc"] 的划分是非法的。
//注意，划分结果需要满足：将所有划分结果按顺序连接，得到的字符串仍然是 s 。
//返回一个表示每个字符串片段的长度的列表。
//示例 1：
//输入：s = "ababcbacadefegdehijhklij"
//输出：[9,7,8]
//解释：
//划分结果为 "ababcbaca"、"defegde"、"hijhklij" 。
//每个字母最多出现在一个片段中。
//像 "ababcbacadefegde", "hijhklij" 这样的划分是错误的，因为划分的片段数较少。

//示例 2：
//输入：s = "eccbbbbdec"
//输出：[10]
public class test_15 {

    public static void main(String[] args) {
        String s = s = "ababcbacadefegdehijhklij";
        System.out.println(partitionLabels_pro(s));
    }

    public static List<Integer> partitionLabels_pro(String s) {
        List<Integer> result = new LinkedList<>();
        int[] lastIndex = new int[26];
        for (int i = 0; i < s.length(); i++)
            lastIndex[s.charAt(i) - 'a'] = i;
        int left = 0;
        int right = 0;
        for (int i = 0; i < s.length(); i++) {
            right = Math.max(right, lastIndex[s.charAt(i) - 'a']);
            if (i == right) {
                result.add(right - left + 1);
                left = right + 1;
            }
        }
        return result;
    }

    public static List<Integer> partitionLabels(String s) {
        List<Integer> result = new LinkedList<>();
        Map<Character, Integer> lastIndex = new HashMap<>();
        for (int i = 0; i < s.length(); i++)
            lastIndex.put(s.charAt(i), i);
        int left = 0;
        int right = 0;
        for (int i = 0; i < s.length(); i++) {
            right = Math.max(right, lastIndex.get(s.charAt(i)));
            if (i == right) {
                result.add(right - left + 1);
                left = right + 1;
            }
        }
        return result;
    }


}
