package problem;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class DailyStocksTest {

    @Test
    public void shouldFindNoProfitWhenPriceIsConstant() {
        DailyStocks dailyStocks = new DailyStocks(new int[]{50, 50, 50, 50, 50});
        assertThat(dailyStocks.maxPossibleProfit(), equalTo(0));
    }

    @Test
    public void shouldFindMaxProfitWhenPriceOnlyIncrease() {
        DailyStocks dailyStocks = new DailyStocks(new int[]{10, 50, 75, 76, 80});
        assertThat(dailyStocks.maxPossibleProfit(), equalTo(70));
    }

    @Test
    public void shouldFindNoProfitWhenPriceOnlyDecrease() {
        DailyStocks dailyStocks = new DailyStocks(new int[]{80, 76, 75, 25, 24});
        assertThat(dailyStocks.maxPossibleProfit(), equalTo(0));
    }

    @Test
    public void shouldFindMaxProfitWhenIsDoingRollerCoaster() {
        DailyStocks dailyStocks = new DailyStocks(new int[]{100, 101, 95, 96, 98, 102, 99, 101});
        assertThat(dailyStocks.maxPossibleProfit(), equalTo(7));
    }

    @Test
    public void shouldFindMaxProfitWhenIsDoingRollerCoasterWithTwiceTheSameMinValue() {
        DailyStocks dailyStocks = new DailyStocks(new int[]{100, 101, 95, 96, 103, 95, 102, 99, 101});
        assertThat(dailyStocks.maxPossibleProfit(), equalTo(8));
    }

    @Test
    public void shouldFindMaxProfitWhenIsDoingRollerWithLowValuesAtTheEndOfDay() {
        DailyStocks dailyStocks = new DailyStocks(new int[]{100, 101, 95, 96, 98, 102, 99, 101, 93, 99});
        assertThat(dailyStocks.maxPossibleProfit(), equalTo(7));
    }
}
