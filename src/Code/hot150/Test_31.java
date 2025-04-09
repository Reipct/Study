package Code.hot150;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 205. 同构字符串
 * @Author 12919
 * @Date 2025/3/19
 */
public class Test_31 {

    public static void main(String[] args) {
        String s = "badc";
        String t = "baba";
        Test_31 test_31 = new Test_31();
        System.out.println(test_31.isIsomorphic(s, t));
    }

    public boolean isIsomorphic(String s, String t) {
        return isIsomorphic_fun(s, t) && isIsomorphic_fun(t, s);
    }
    private boolean isIsomorphic_fun(String s, String t) {
        Map<Character, Character> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char s_char = s.charAt(i);
            char t_char = t.charAt(i);
            if (map.containsKey(t_char)) {
                if (map.get(t_char) != s_char)
                    return false;
            } else {
                map.put(t_char, s_char);
            }
        }
        return true;
    }
}
