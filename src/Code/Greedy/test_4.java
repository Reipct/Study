package Code.Greedy;

/**
 * @Description 122.买卖股票的最佳时机 II
 * @Author 输入：prices = [7,1,5,3,6,4]
 * 输出：7
 * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5 - 1 = 4。
 * 随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6 - 3 = 3。
 * 最大总利润为 4 + 3 = 7 。
 * @Date 2024/12/15
 */
public class test_4 {

    public static void main(String[] args) {
        int[] price = new int[]{7, 1, 5, 3, 6, 4};
        System.out.println(maxProfit(price));
    }


    public static int maxProfit(int[] prices) {
        int len = prices.length;
        if (len < 2)
            return 0;
        int result = 0;
        for (int i = 1; i < len; i++) {
            int diff = prices[i] - prices[i - 1];
            if (diff > 0)
                result += diff;
        }
        return result;
    }
}
