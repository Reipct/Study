package Code.hot150;

/**
 * @Description TODO
 * @Author 12919
 * @Date 2025/3/10
 */
public class Test_16 {


    public static void main(String[] args) {
        String str = "a";
        Test_16 test_16 = new Test_16();
        System.out.println(test_16.lengthOfLastWord(str));
    }

    public int lengthOfLastWord(String s) {
        int len_str = 0;
        int index = s.length() - 1;
        while (s.charAt(index) == ' ')
            index--;
        while (index>=0&&s.charAt(index) != ' ') {
            len_str++;
            index--;
        }
        return len_str;
    }
}
