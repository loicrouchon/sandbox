package problem;

public class DailyStocks {

    private final int[] stocks;

    public DailyStocks(int[] stocks) {
        this.stocks = stocks;
    }

    /**
     * <p>I have an array stock_prices_yesterday where:</p>
     * <p/>
     * <p>The indices are the time, as a number of minutes past trade opening time, which was 9:30am local time.
     * The values are the price of Apple stock at that time, in dollars.</p>
     * <p/>
     * <p>For example, the stock cost $500 at 10:30am, so stock_prices_yesterday[60] = 500.</p>
     * <p/>
     * <p>Write an efficient algorithm for computing the best profit I could have made from 1 purchase and 1 sale of 1 Apple stock yesterday. For this problem, we won't allow "shorting"—you must buy before you sell.</p>
     *
     * @return the maximum profit that can be done this day
     */
    public int maxPossibleProfit() {
        int maxProfit = 0;
        int maxPrice = Integer.MIN_VALUE;
        for (int i = stocks.length - 1; i >= 0; i--) {
            int currentPrice = stocks[i];
            if (currentPrice > maxPrice) {
                maxPrice = currentPrice;
            }
            int potentialProfit = maxPrice - currentPrice;
            if (potentialProfit > maxProfit) {
                maxProfit = potentialProfit;
            }
        }
        return maxProfit;
    }
}
