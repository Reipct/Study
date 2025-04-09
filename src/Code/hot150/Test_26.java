package Code.hot150;

/**
 * @Description 50. Pow(x, n)
 * @Author 12919
 * @Date 2025/3/14
 */
public class Test_26 {
    public static void main(String[] args) {
        double x = 2.00000;
        int n = 10;
        Test_26 test_26 = new Test_26();
        System.out.println(test_26.myPow(x, n));
    }

    public double myPow(double x, int n) {
        if (x == 0.0f) return 0.0d;
        long b = n;
        double res = 1.0;
        if (n < 0) {
            x = 1 / x;
            n = -n;
        }
        while (b > 0) {
            if ((b & 1) == 1) res *= x;
            x *= x;
            b = b >> 1;
        }
        return res;
    }
}
