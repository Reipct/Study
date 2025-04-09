package Code.hot150;

/**
 * @Description 122. 买卖股票的最佳时机 II
 * @Author 12919
 * @Date 2025/3/6
 */
public class Test_11 {

    public static void main(String[] args) {
        int[] prices = {7, 1, 5, 3, 6, 4};
        Test_11 test_11 = new Test_11();
        System.out.println(test_11.maxProfit(prices));
    }

    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1])
                maxProfit += (prices[i] - prices[i - 1]);

        }
        return maxProfit;
    }

}
