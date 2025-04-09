package Code.hot150;

/**
 * @Description 121. 买卖股票的最佳时机
 * @Author 12919
 * @Date 2025/3/6
 */
public class Test_10 {
    public static void main(String[] args) {
        int[] prices = {7, 1, 5, 3, 6, 4};
        Test_10 test_10 = new Test_10();
        System.out.println(test_10.maxProfit(prices));
    }


    public int maxProfit(int[] prices) {
        int maxprifits = 0;
        int minPrice = Integer.MAX_VALUE;
        for (int i = 0; i < prices.length; i++) {
            if (minPrice > prices[i])
                minPrice = prices[i];
            else if (prices[i] - minPrice > maxprifits)
                maxprifits = prices[i] - minPrice;
        }
        return maxprifits;
    }


    public int maxProfit_voilet(int[] prices) {
        int Price = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            for (int j = i + 1; j < prices.length; j++) {
                Price = Math.max(Price, prices[j] - prices[i]);
            }
        }
        return Price;
    }


}
