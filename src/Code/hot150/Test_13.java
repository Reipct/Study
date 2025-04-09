package Code.hot150;

import java.util.List;

/**
 * @Description 12. 整数转罗马数字
 * @Author 12919
 * @Date 2025/3/6
 */
public class Test_13 {

    public static void main(String[] args) {
        int num = 3287;
        Test_13 test_13 = new Test_13();
        System.out.println(test_13.intToRoman(num));
    }

    String[] thousands = {"", "M", "MM", "MMM"};
    String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
    String[] tens = {"", "X", "XX", "XX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
    String[] ones = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

    public String intToRoman(int num) {
        StringBuffer res = new StringBuffer();
        res.append(thousands[num / 1000]);
        res.append(hundreds[num % 1000 / 100]);
        res.append(tens[num % 100 / 10]);
        res.append(ones[num % 10]);
        return res.toString();
    }
}
