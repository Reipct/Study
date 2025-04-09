package Code.hot150;

/**
 * @Description 191. 位1的个数
 * @Author 12919
 * @Date 2025/3/13
 */
public class Test_22 {
    public static void main(String[] args) {
        int n = 2147483645;
        Test_22 test_22 = new Test_22();
        System.out.println(test_22.hammingWeight(n));

    }

    public int hammingWeight(int n) {
        int setbit = 0;
        while (n > 0) {
            setbit += n % 2;
            n /= 2;
        }
        return setbit;
    }
}
