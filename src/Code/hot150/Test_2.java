package Code.hot150;

/**
 * @Description 125. 验证回文串
 * @Author 12919
 * @Date 2025/3/2
 */
public class Test_2 {


    public static void main(String[] args) {

        String str = "0P";
        Test_2 test_2 = new Test_2();
        System.out.println(test_2.isPalindrome(str));
    }

    public boolean isPalindrome(String s) {
        if (s == null || s.isEmpty()) {
            return true;
        }
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            while (left < right && !(Character.isLetterOrDigit(s.charAt(left)))) {
                left++;
            }
            while (left < right && !(Character.isLetterOrDigit(s.charAt(right)))) {
                right--;
            }
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

}
