package problem;
public class CoinCounter {
	/**
	 * Given a set of coin denominators, find the minimum number of coins to
	 * give a certain amount of change. denominators in sorted order
	 */
	public int minimumNbOfCoinsToCoverAmount(int[] denominators, int amount) {
		if (amount == 0) {
			return 0;
		}
		if (denominators.length == 0 || amount % denominators[0] > 0) {
			return -1;
		}
		int nb = 0;
		for (int i = denominators.length - 1; i >= 0 && amount > 0; i--) {
			if (denominators[i] <= amount) {
				int q = amount / denominators[i];
				int remaining = amount % denominators[i];
				if (remaining == 0) {
					nb += q;
					amount = remaining;
				} else {
					for (int j = 0; j < i; j++) {
						if (remaining % denominators[j] == 0) {
							nb += q;
							amount = remaining;
							break;
						}
					}
				}
			}
		}
		return nb;
	}

}
