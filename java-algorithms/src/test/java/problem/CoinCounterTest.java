package problem;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CoinCounterTest {

	@Test
	public void minimumNbOfCoinsToCoverAmount() {
		CoinCounter coinCounter = new CoinCounter();
		assertThat(coinCounter.minimumNbOfCoinsToCoverAmount(new int[] {}, 0), is(0));
		assertThat(coinCounter.minimumNbOfCoinsToCoverAmount(new int[] {}, 1), is(-1));
		assertThat(coinCounter.minimumNbOfCoinsToCoverAmount(new int[] { 2 }, 1), is(-1));
		assertThat(coinCounter.minimumNbOfCoinsToCoverAmount(new int[] { 2 }, 2), is(1));
		assertThat(coinCounter.minimumNbOfCoinsToCoverAmount(new int[] { 2 }, 4), is(2));
		assertThat(coinCounter.minimumNbOfCoinsToCoverAmount(new int[] { 1, 2 }, 4), is(2));
		assertThat(coinCounter.minimumNbOfCoinsToCoverAmount(new int[] { 1, 2 }, 3), is(2));
		assertThat(coinCounter.minimumNbOfCoinsToCoverAmount(new int[] { 1, 3 }, 2), is(2));
		assertThat(coinCounter.minimumNbOfCoinsToCoverAmount(new int[] { 1, 3, 5 }, 9), is(3));
		assertThat(coinCounter.minimumNbOfCoinsToCoverAmount(new int[] { 2, 5, 6 }, 10), is(2));
	}
}
