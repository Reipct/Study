package Code.hot150;

/**
 * @Description 123. 买卖股票的最佳时机 III
 * @Author 12919
 * @Date 2025/3/12
 */
public class Test_19 {
    public static void main(String[] args) {
        int[] prices = {1, 2, 3, 4, 5};
        Test_19 test_19 = new Test_19();
        System.out.println(test_19.maxProfit(prices));
    }

    public int maxProfit(int[] prices) {
        int len = prices.length;
        int maxprofit = 0;
        for (int i = 0; i < len; i++) {
            int left = maxprofit_part(prices, 0, i);
            int right = maxprofit_part(prices, i + 1, len - 1);
            if (left + right > maxprofit)
                maxprofit = left + right;
        }
        return maxprofit;
    }

    public int maxprofit_part(int[] prices, int start, int end) {
        int maxprofit = 0;
        int minprice = Integer.MAX_VALUE;
        for (int i = start ; i <= end; i++) {
            if (prices[i] < minprice)
                minprice = prices[i];
            else if (prices[i] - minprice > maxprofit)
                maxprofit = prices[i] - minprice;
        }
        return maxprofit;
    }
}
